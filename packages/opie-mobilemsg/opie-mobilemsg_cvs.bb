DESCRIPTION = "Mobile Messaging"
SECTION = "opie/applications"
PRIORITY = "optional"
MAINTAINER = "Team Opie <opie@handhelds.org>"
LICENSE = "GPL"
PV = "1.1.9+cvs-${CVSDATE}"
APPNAME = "mobilemsg"
APPTYPE = "binary"

SRC_URI = "${HANDHELDS_CVS};module=opie/noncore/comm/mobilemsg \
           ${HANDHELDS_CVS};module=opie/pics \
           ${HANDHELDS_CVS};module=opie/apps"

S = "${WORKDIR}/${APPNAME}"

inherit opie

# FILES plugins/application/libmobilemsg.so* bin/mobilemsg apps/Applications/mobilemsg.desktop pics/mobilemsg
# Note: No pics are available in pics/mobilemsg -- no content in directory
do_install() {
}

