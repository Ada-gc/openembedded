DESCRIPTION = "Opie's mail and news client featuring POP3, IMAP and NNTP - with and without SSL."
SECTION = "opie/pim"
PRIORITY = "optional"
MAINTAINER = "Team Opie <opie@handhelds.org>"
LICENSE = "GPL"
DEPENDS = "libmailwrapper libetpan"
RDEPENDS = "opie-pics"
PV = "1.1.9+cvs-${CVSDATE}"
APPNAME = "opiemail"

SRC_URI = "${HANDHELDS_CVS};module=opie/noncore/net/mail \
           ${HANDHELDS_CVS};module=opie/apps \
	   ${HANDHELDS_CVS};module=opie/pics"

S = "${WORKDIR}/mail"

inherit opie

# FILES plugins/application/libopiemail.so* bin/opiemail apps/1Pim/mail.desktop pics/mail/*.png

do_install() {
	install -d ${D}/${palmtopdir}/pics/mail/
	install -m 0644 ${WORKDIR}/pics/mail/*.png ${D}/${palmtopdir}/pics/mail/
}

