DESCRIPTION = "Enables apps on external media"
SECTION = "opie/base"
PRIORITY = "optional"
MAINTAINER = "Team Opie <opie@handhelds.org>"
LICENSE = "GPL"

APPNAME = "opie-update-symlinks"
APPTYPE = "binary"


SRC_URI = "${HANDHELDS_CVS};tag=${TAG};module=opie/core/symlinker "

S = "${WORKDIR}/symlinker"

inherit opie

do_install() {
	install -d ${D}${palmtopdir}/bin/
	install -m 0755 ${S}/opie-update-symlinks ${D}${palmtopdir}/bin/
}

