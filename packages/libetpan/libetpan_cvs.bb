DESCRIPTION = "libetpan is a library for communicating with mail and news servers using \
SMTP, POP, IMAP and NNTP"
SECTION = "libs"
DEPENDS = "openssl"
PV = "0.33"
PR = "r1"
S = "${WORKDIR}/libetpan"
TAG="rel-0-33-b"
LICENSE = "BSD"
SRC_URI = "cvs://anonymous@cvs.sourceforge.net/cvsroot/libetpan;tag=${TAG};module=libetpan"

inherit autotools pkgconfig gettext

EXTRA_OECONF = "--with-openssl=${STAGING_LIBDIR}/.. --disable-db"

do_stage () {
	oe_runmake install includedir=${STAGING_INCDIR} libdir=${STAGING_LIBDIR} bindir=${STAGING_BINDIR}
}

FILES_${PN} = "${libdir}/lib*.so.*"
FILES_${PN}-dev = "${bindir} ${includedir} ${libdir}/lib*.so ${libdir}/*.la ${libdir}/*.a ${libdir}/pkgconfig"
