DESCRIPTION = "About Opie"
SECTION = "opie/applets"
PRIORITY = "optional"
MAINTAINER = "Rajko Albrecht <alwin@handhelds.org>"
LICENSE = "GPL"
PV = "1.1.9+cvs-${CVSDATE}"
APPNAME = "aboutapplet"

SRC_URI = "${HANDHELDS_CVS};module=opie/core/applets/aboutapplet"
S = "${WORKDIR}/${APPNAME}"

inherit opie
