DESCRIPTION = "Polished window decoration style for Opie"
SECTION = "opie/decorations"
PRIORITY = "optional"
MAINTAINER = "Team Opie <opie@handhelds.org>"
LICENSE = "GPL"
PV = "1.1.8+cvs-${CVSDATE}"
APPNAME = "polished"

SRC_URI = "${HANDHELDS_CVS};module=opie/noncore/decorations/polished "

S = "${WORKDIR}/polished"

inherit opie
