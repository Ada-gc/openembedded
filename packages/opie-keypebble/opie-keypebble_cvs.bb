DESCRIPTION = "A VNC Viewer"
SECTION = "opie/applications"
PRIORITY = "optional"
MAINTAINER = "Team Opie <opie@handhelds.org>"
LICENSE = "GPL"
PV = "1.1.8+cvs-${CVSDATE}"
APPNAME = "keypebble"

SRC_URI = "${HANDHELDS_CVS};module=opie/noncore/comm/${APPNAME} \
           ${HANDHELDS_CVS};module=opie/pics                    \
           ${HANDHELDS_CVS};module=opie/apps"
S = "${WORKDIR}/${APPNAME}"

inherit opie

do_install() {
	install -d ${D}${palmtopdir}/pics/vnc
	install -m 0644 ${WORKDIR}/pics/vnc/*.* ${D}${palmtopdir}/pics/vnc/
}

