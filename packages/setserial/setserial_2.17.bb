SECTION = "console/utils"
SRC_URI = "ftp://tsx-11.mit.edu/pub/linux/sources/sbin/setserial-2.17.tar.gz"
LICENSE = "GPL"
inherit autotools

do_install() {
	install -d ${D}/bin
	install -d ${D}/usr/man/man8
	install -d ${D}${mandir}
	autotools_do_install
	mv ${D}/usr/man/* ${D}${mandir}/
}
