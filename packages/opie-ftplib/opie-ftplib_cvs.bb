DESCRIPTION = "Opie FTP Library"
SECTION = "opie/libs"
MAINTAINER = "Team Opie <opie@handhelds.org>"
PRIORITY = "optional"
LICENSE = "GPL"
PROVIDES = "libftplib1"
I18N_FILES = "libftplib.ts"

APPNAME = "opie-ftplib"

PV = "1.1.9+cvs-${CVSDATE}"

SRC_URI = "${HANDHELDS_CVS};module=opie/noncore/net/ftplib"
S = "${WORKDIR}/ftplib"

inherit palmtop


EXTRA_QMAKEVARS_POST = "DESTDIR=${S}"

do_stage () {
	install -m 0664 ${S}/ftplib.h ${STAGING_INCDIR}/
	oe_libinstall -so libftplib ${STAGING_LIBDIR}
}

do_install() {
	oe_libinstall -so libftplib ${D}/${palmtopdir}/lib
}
