inherit package
DEPENDS_prepend="${@["ipkg-utils-native ", ""][(bb.data.getVar('PACKAGES', d, 1) == '')]}"
BOOTSTRAP_EXTRA_RDEPENDS += "ipkg-collateral ipkg ipkg-link"
BOOTSTRAP_EXTRA_DEPENDS  += "ipkg-collateral ipkg ipkg-link"

python package_ipk_fn () {
	from bb import data
	bb.data.setVar('PKGFN', bb.data.getVar('PKG',d), d)
}

python package_ipk_install () {
	import os, sys
	pkg = bb.data.getVar('PKG', d, 1)
	pkgfn = bb.data.getVar('PKGFN', d, 1)
	rootfs = bb.data.getVar('IMAGE_ROOTFS', d, 1)
	ipkdir = bb.data.getVar('DEPLOY_DIR_IPK', d, 1)
	stagingdir = bb.data.getVar('STAGING_DIR', d, 1)
	tmpdir = bb.data.getVar('TMPDIR', d, 1)

	if None in (pkg,pkgfn,rootfs):
		raise bb.build.FuncFailed("missing variables (one or more of PKG, PKGFN, IMAGEROOTFS)")
	try:
		bb.mkdirhier(rootfs)
		os.chdir(rootfs)
	except OSError:
		(type, value, traceback) = sys.exc_info()
		print value
		raise bb.build.FuncFailed

	# Generate ipk.conf if it or the stamp doesnt exist
	conffile = os.path.join(stagingdir,"ipkg.conf")
	if not  os.access(conffile, os.R_OK):
		ipkg_archs = bb.data.getVar('IPKG_ARCHS',d)
		if ipkg_archs is None:
			bb.error("IPKG_ARCHS missing")
			raise FuncFailed
		ipkg_archs = ipkg_archs.split()
		arch_priority = 1

		f = open(conffile,"w")
		for arch in ipkg_archs:
			f.write("arch %s %s\n" % ( arch, arch_priority ))
			arch_priority += 1
		f.write("src local file:%s" % ipkdir)
		f.close()


	if (not os.access(os.path.join(ipkdir,"Packages"), os.R_OK) or
		not os.access(os.path.join(os.path.join(tmpdir, "stamps"),"do_packages"),os.R_OK)):
		ret = os.system('ipkg-make-index -p %s %s ' % (os.path.join(ipkdir, "Packages"), ipkdir))
		if (ret != 0 ):
			raise bb.build.FuncFailed
		f=open(os.path.join(os.path.join(tmpdir, "stamps"),"do_packages"),"w")
		f.close()

	ret = os.system('ipkg-cl  -o %s -f %s update' % (rootfs, conffile))
	ret = os.system('ipkg-cl  -o %s -f %s install %s' % (rootfs, conffile, pkgfn))
	if (ret != 0 ):
		raise bb.build.FuncFailed
}

python package_ipk_do_package_ipk () {
	import copy # to back up env data
	import sys

	bb.build.exec_func('read_subpackage_metadata', d)

	workdir = bb.data.getVar('WORKDIR', d, 1)
	if not workdir:
		bb.error("WORKDIR not defined, unable to package")
		return

	import os # path manipulations
	outdir = bb.data.getVar('DEPLOY_DIR_IPK', d, 1)
	if not outdir:
		bb.error("DEPLOY_DIR_IPK not defined, unable to package")
		return
	bb.mkdirhier(outdir)

	dvar = bb.data.getVar('D', d, 1)
	if not dvar:
		bb.error("D not defined, unable to package")
		return
	bb.mkdirhier(dvar)

	packages = bb.data.getVar('PACKAGES', d, 1)
	if not packages:
		bb.debug(1, "PACKAGES not defined, nothing to package")
		return

	tmpdir = bb.data.getVar('TMPDIR', d, 1)
	# Invalidate the packages file
	if os.access(os.path.join(os.path.join(tmpdir, "stamps"),"do_packages"),os.R_OK):
		os.unlink(os.path.join(os.path.join(tmpdir, "stamps"),"do_packages"))

	if packages == []:
		bb.debug(1, "No packages; nothing to do")
		return

	for pkg in packages.split():
		from copy import deepcopy
		localdata = deepcopy(d)
		root = "%s/install/%s" % (workdir, pkg)

		bb.data.setVar('ROOT', '', localdata)
		bb.data.setVar('ROOT_%s' % pkg, root, localdata)
		pkgname = bb.data.getVar('PKG_%s' % pkg, localdata, 1)
		if not pkgname:
			pkgname = pkg
		bb.data.setVar('PKG', pkgname, localdata)

		overrides = bb.data.getVar('OVERRIDES', localdata)
		if not overrides:
			raise bb.build.FuncFailed('OVERRIDES not defined')
		overrides = bb.data.expand(overrides, localdata)
		bb.data.setVar('OVERRIDES', overrides + ':' + pkg, localdata)

		bb.data.update_data(localdata)
		basedir = os.path.join(os.path.dirname(root))
		pkgoutdir = outdir
		bb.mkdirhier(pkgoutdir)
		os.chdir(root)
		from glob import glob
		g = glob('*')
		try:
			del g[g.index('CONTROL')]
			del g[g.index('./CONTROL')]
		except ValueError:
			pass
		if not g and not bb.data.getVar('ALLOW_EMPTY', localdata):
			from bb import note
			note("Not creating empty archive for %s-%s-%s" % (pkg, bb.data.getVar('PV', localdata, 1), bb.data.getVar('PR', localdata, 1)))
			continue
		controldir = os.path.join(root, 'CONTROL')
		bb.mkdirhier(controldir)
		try:
			ctrlfile = file(os.path.join(controldir, 'control'), 'w')
		except OSError:
			raise bb.build.FuncFailed("unable to open control file for writing.")

		fields = []
		fields.append(["Version: %s-%s\n", ['PV', 'PR']])
		fields.append(["Description: %s\n", ['DESCRIPTION']])
		fields.append(["Section: %s\n", ['SECTION']])
		fields.append(["Priority: %s\n", ['PRIORITY']])
		fields.append(["Maintainer: %s\n", ['MAINTAINER']])
		fields.append(["Architecture: %s\n", ['PACKAGE_ARCH']])
		fields.append(["OE: %s\n", ['P']])
		fields.append(["Source: %s\n", ['SRC_URI']])

		def pullData(l, d):
			l2 = []
			for i in l:
				l2.append(bb.data.getVar(i, d, 1))
			return l2

		ctrlfile.write("Package: %s\n" % pkgname)
		# check for required fields
		try:
			for (c, fs) in fields:
				for f in fs:
					if bb.data.getVar(f, localdata) is None:
						raise KeyError(f)
				ctrlfile.write(c % tuple(pullData(fs, localdata)))
		except KeyError:
			(type, value, traceback) = sys.exc_info()
			ctrlfile.close()
			raise bb.build.FuncFailed("Missing field for ipk generation: %s" % value)
		# more fields
		rdepends = explode_deps(bb.data.getVar("RDEPENDS", localdata, 1) or "")
		rsuggests = (bb.data.getVar("RSUGGESTS", localdata, 1) or "").split()
		rrecommends = (bb.data.getVar("RRECOMMENDS", localdata, 1) or "").split()
		rprovides = (bb.data.getVar("RPROVIDES", localdata, 1) or "").split()
		rreplaces = (bb.data.getVar("RREPLACES", localdata, 1) or "").split()
		rconflicts = (bb.data.getVar("RCONFLICTS", localdata, 1) or "").split()
		if rdepends:
			ctrlfile.write("Depends: " + ", ".join(rdepends) + "\n")
		if rsuggests:
			ctrlfile.write("Suggests: " + ", ".join(rsuggests) + "\n")
		if rrecommends:
			ctrlfile.write("Recommends: " + ", ".join(rrecommends) + "\n")
		if rprovides:
			ctrlfile.write("Provides: " + ", ".join(rprovides) + "\n")
		if rreplaces:
			ctrlfile.write("Replaces: " + ", ".join(rreplaces) + "\n")
		if rconflicts:
			ctrlfile.write("Conflicts: " + ", ".join(rconflicts) + "\n")
		ctrlfile.close()

		for script in ["preinst", "postinst", "prerm", "postrm"]:
			scriptvar = bb.data.getVar('pkg_%s' % script, localdata, 1)
			if not scriptvar:
				continue
			try:
				scriptfile = file(os.path.join(controldir, script), 'w')
			except OSError:
				raise bb.build.FuncFailed("unable to open %s script file for writing." % script)
			scriptfile.write(scriptvar)
			scriptfile.close()
			os.chmod(os.path.join(controldir, script), 0755)

		conffiles_str = bb.data.getVar("CONFFILES", localdata, 1)
		if conffiles_str:
			try:
				conffiles = file(os.path.join(controldir, 'conffiles'), 'w')
			except OSError:
				raise bb.build.FuncFailed("unable to open conffiles for writing.")
			for f in conffiles_str.split():
				conffiles.write('%s\n' % f)
			conffiles.close()

		os.chdir(basedir)
		ret = os.system("PATH=\"%s\" ipkg-build -o 0 -g 0 %s %s" % (bb.data.getVar("PATH", localdata, 1), pkg, pkgoutdir))
		if ret != 0:
			raise bb.build.FuncFailed("ipkg-build execution failed")

		for script in ["preinst", "postinst", "prerm", "postrm", "control" ]:
			scriptfile = os.path.join(controldir, script)
			try:
				os.remove(scriptfile)
			except OSError:
				pass
		try:
			os.rmdir(controldir)
		except OSError:
			pass
		del localdata
}

EXPORT_FUNCTIONS do_package_ipk

addtask package_ipk after do_package before do_build
