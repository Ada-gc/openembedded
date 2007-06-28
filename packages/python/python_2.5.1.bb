DESCRIPTION = "Python Programming Language"
HOMEPAGE = "http://www.python.org"
LICENSE = "PSF"
SECTION = "devel/python"
PRIORITY = "optional"
MAINTAINER = "Michael 'Mickey' Lauer <mickey@Vanille.de>"
DEPENDS = "python-native readline zlib gdbm openssl"
PR = "ml0"

PYTHON_MAJMIN = "2.5"

SRC_URI = "http://www.python.org/ftp/python/${PV}/Python-${PV}.tar.bz2 \
           file://bindir-libdir.patch;patch=1 \
           file://crosscompile.patch;patch=1 \
	   file://build-native-md5-sha.patch;patch=1 \
	   file://fix-tkinter-detection.patch;patch=1 \
	   file://some_configure_fixes.patch;patch=1;pnum=0"
S = "${WORKDIR}/Python-${PV}"

inherit autotools

EXTRA_OECONF = "--with-threads --with-pymalloc --with-cyclic-gc \
                --without-cxx --with-signal-module --with-wctype-functions \
                --enable-shared"

#
# copy config.h and an appropriate Makefile for distutils.sysconfig
# which laters uses the information out of these to compile extensions
#
do_compile_prepend() {
	install -d ${STAGING_INCDIR}/python${PYTHON_MAJMIN}/
	install -d ${STAGING_LIBDIR}/python${PYTHON_MAJMIN}/config/
	install -m 0644 pyconfig.h ${STAGING_INCDIR}/python${PYTHON_MAJMIN}/
	install -m 0644 Makefile Makefile.orig
	install -m 0644 Makefile Makefile.backup
	sed -e 's,${includedir},${STAGING_INCDIR},' < Makefile.backup > Makefile
	install -m 0644 Makefile Makefile.backup
	sed -e 's,${libdir},${STAGING_LIBDIR},' < Makefile.backup > Makefile
	install -m 0644 Makefile ${STAGING_LIBDIR}/python${PYTHON_MAJMIN}/config/
}

do_compile() {
	oe_runmake HOSTPGEN=${STAGING_BINDIR}/pgen \
		   HOSTPYTHON=${STAGING_BINDIR}/python \
		   STAGING_LIBDIR=${STAGING_LIBDIR} \
		   STAGING_INCDIR=${STAGING_INCDIR} \
		   PYTHON_LIBDIR=${S} \
		   BUILD_SYS=${BUILD_SYS} \
		   HOST_SYS=${HOST_SYS}
}

do_stage() {
	install -m 0644 Include/*.h ${STAGING_INCDIR}/python${PYTHON_MAJMIN}/
	oe_libinstall -a -so libpython2.5 ${STAGING_LIBDIR}
}

do_install() {
	install -m 0644 Makefile.orig Makefile
        oe_runmake HOSTPGEN=${STAGING_BINDIR}/pgen \
                   HOSTPYTHON=${STAGING_BINDIR}/python \
                   STAGING_LIBDIR=${STAGING_LIBDIR} \
		   STAGING_INCDIR=${STAGING_INCDIR} \
		   PYTHON_LIBDIR=${S} \
		   BUILD_SYS=${BUILD_SYS} HOST_SYS=${HOST_SYS} \
		   DESTDIR=${D} install
}

include python-${PV}-manifest.inc

PACKAGES =+ "libpython2"
FILES_libpython2 = "${libdir}/libpython*"

