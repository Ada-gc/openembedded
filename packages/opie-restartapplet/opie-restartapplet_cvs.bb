DESCRIPTION = "Restart Applet"
SECTION = "opie/applets"
PRIORITY = "optional"
MAINTAINER = "Team Opie <opie@handhelds.org>"
LICENSE = "GPL"
PV = "1.1.9+cvs-${CVSDATE}"
APPNAME = "restartapplet"

SRC_URI = "${HANDHELDS_CVS};module=opie/core/applets/restartapplet \
           ${HANDHELDS_CVS};module=opie/apps"

S = "${WORKDIR}/${APPNAME}"

inherit opie

# FILES plugins/applets/librestartapplet.so*
do_install() {
}

