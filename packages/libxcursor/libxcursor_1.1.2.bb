SECTION = "x11/libs"
PRIORITY = "optional"
MAINTAINER = "Phil Blundell <pb@handhelds.org>"
DESCRIPTION = "X Cursor library"
DEPENDS = "libxfixes"
PR = "r1"

SRC_URI = "${XLIBS_MIRROR}/libXcursor-${PV}.tar.bz2 \
	   file://autofoo.patch;patch=1"
S = "${WORKDIR}/libXcursor-${PV}"

inherit autotools pkgconfig 

do_stage() {
	oe_runmake install prefix=${STAGING_DIR} \
	       bindir=${STAGING_BINDIR} \
	       includedir=${STAGING_INCDIR} \
	       libdir=${STAGING_LIBDIR} \
	       datadir=${STAGING_DATADIR} \
	       mandir=${STAGING_DIR}/man
}
