DESCRIPTION = "A Biff-like mailchecker"
SECTION = "opie/applets"
PRIORITY = "optional"
MAINTAINER = "Team Opie <opie@handhelds.org>"
LICENSE = "GPL"
DEPENDS = "libmailwrapper"
PV = "1.1.8+cvs-${CVSDATE}"
APPNAME = "mailapplet"

SRC_URI = "${HANDHELDS_CVS};module=opie/noncore/net/mail/taskbarapplet \
           ${HANDHELDS_CVS};module=opie/pics \
           ${HANDHELDS_CVS};module=opie/apps"

S = "${WORKDIR}/taskbarapplet"

inherit opie

