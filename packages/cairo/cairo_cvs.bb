PV = "0.0cvs${CVSDATE}"
SECTION = "libs"
PRIORITY = "optional"
MAINTAINER = "Phil Blundell <pb@debian.org>"
# DEPENDS = "x11 libpixman libpng fontconfig libxrender xcb glitz"
DEPENDS = "x11 libpixman libpng fontconfig libxrender"
DESCRIPTION = "Cairo graphics library"
LICENSE = "MPL LGPL"
PR = "r1"

SRC_URI = "cvs://anoncvs:anoncvs@cvs.cairographics.org/cvs/cairo;module=cairo
\
	   file://status.patch;patch=1"
S = "${WORKDIR}/cairo"

inherit autotools pkgconfig 

do_stage () {
	oe_runmake install DESTDIR="" bindir=${STAGING_BINDIR} includedir=${STAGING_INCDIR} libdir=${STAGING_LIBDIR} prefix=${STAGING_DIR}
}
