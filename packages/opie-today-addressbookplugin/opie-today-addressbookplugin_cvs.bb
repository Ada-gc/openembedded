DESCRIPTION = "Addressbook plugin for Today"
SECTION = "opie/today"
PRIORITY = "optional"
LICENSE = "GPL"
RDEPENDS = "opie-today"
PV = "1.1.9+cvs-${CVSDATE}"
APPNAME = "todayaddressbookplugin"

SRC_URI = "${HANDHELDS_CVS};module=opie/core/pim/today/plugins/addressbook "

S = "${WORKDIR}/addressbook"

inherit opie

