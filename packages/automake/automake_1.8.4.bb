DESCRIPTION = "A tool for automatically generating Makefiles."
LICENSE = "GPL"
HOMEPAGE = "http://www.gnu.org/software/automake/"
SECTION = "devel"
PR = "r2"

SRC_URI = "${GNU_MIRROR}/automake/automake-${PV}.tar.bz2"
FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/automake-${PV}"

inherit autotools

FILES_${PN} += "${datadir}/automake* ${datadir}/aclocal*"
RDEPENDS_${PN} += "perl"

do_install () {
	oe_runmake 'DESTDIR=${D}' install
	install -d ${D}${datadir}
	if [ ! -e ${D}${datadir}/aclocal ]; then
		ln -sf aclocal-1.8 ${D}${datadir}/aclocal
	fi
	if [ ! -e ${D}${datadir}/automake ]; then
		ln -sf automake-1.8 ${D}${datadir}/automake
	fi
}
