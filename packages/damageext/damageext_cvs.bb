PV = "0.0cvs${CVSDATE}"
SECTION = "libs"
MAINTAINER = "Greg Gilbert <greg@treke.net>"
DEPENDS = "xextensions fixesext"
DESCRIPTION = "X Damage extension headers and specification"
PR = "r1"

SRC_URI = "cvs://anoncvs:anoncvs@pdx.freedesktop.org/cvs/xlibs;module=DamageExt \
	   file://autofoo.patch;patch=1"
S = "${WORKDIR}/DamageExt"

inherit autotools pkgconfig

do_stage() {
	oe_runmake install prefix=${STAGING_DIR} \
	       bindir=${STAGING_BINDIR} \
	       includedir=${STAGING_INCDIR} \
	       libdir=${STAGING_LIBDIR} \
	       datadir=${STAGING_DATADIR}
}
