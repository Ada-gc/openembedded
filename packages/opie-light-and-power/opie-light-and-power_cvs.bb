DESCRIPTION = "Light and Power settings dialog"
SECTION = "opie/settings"
PRIORITY = "optional"
MAINTAINER = "Team Opie <opie@handhelds.org>"
LICENSE = "GPL"
PV = "1.1.9+cvs-${CVSDATE}"
APPNAME = "light-and-power"

SRC_URI = "${HANDHELDS_CVS};module=opie/core/settings/light-and-power \
           ${HANDHELDS_CVS};module=opie/pics \
           ${HANDHELDS_CVS};module=opie/apps"

S = "${WORKDIR}/${APPNAME}"

inherit opie

# FILES plugins/application/liblight-and-power.so* bin/light-and-power apps/Settings/light.desktop pics/lightandpower
do_install() {
        install -d ${D}${palmtopdir}/pics/lightandpower/
        install -m 0644 ${WORKDIR}/pics/lightandpower/*.png ${D}${palmtopdir}/pics/lightandpower/
}

