DESCRIPTION = "Mobile Messaging"
SECTION = "opie/applications"
PRIORITY = "optional"
MAINTAINER = "Team Opie <opie@handhelds.org>"
LICENSE = "GPL"

APPNAME = "mobilemsg"
APPTYPE = "binary"

TAG = "${@'v' + bb.data.getVar('PV',d,1).replace('.', '_')}"
SRC_URI = "${HANDHELDS_CVS};tag=${TAG};module=opie/noncore/comm/mobilemsg \
           ${HANDHELDS_CVS};tag=${TAG};module=opie/pics \
           ${HANDHELDS_CVS};tag=${TAG};module=opie/apps"

S = "${WORKDIR}/${APPNAME}"

inherit opie

# FILES plugins/application/libmobilemsg.so* bin/mobilemsg apps/Applications/mobilemsg.desktop pics/mobilemsg

do_install() {
}

do_install_prepend() {
	install -d ${D}${palmtopdir}/pics/mobilemsg/
	install -m 0644 ${WORKDIR}/pics/mobilemsg/*.png ${D}${palmtopdir}/pics/mobilemsg/
}
