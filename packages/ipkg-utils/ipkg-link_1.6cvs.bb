SECTION = "base"
DESCRIPTION = "Itsy Package Manager utilities link script"
LICENSE = "GPL"
CONFLICTS = "ipkg-utils"
PV_append = "${CVSDATE}"

SRC_URI = "${HANDHELDS_CVS};module=ipkg-utils \
	   file://fix-ipkg-link.patch;patch=1 "
	   
SRC_URI_append_beagle = "file://fix-ipkg-link-beagle.patch;patch=1"	   
S = "${WORKDIR}/ipkg-utils"

do_compile() {
	:
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ipkg-link ${D}${bindir}
}
