require navit.inc

PV = "0.1.0+svnr${SRCPV}"
PR = "r2"
PE = "1"

DEFAULT_PREFERENCE = "-1"

S = "${WORKDIR}/navit"

SRC_URI = "svn://anonymous@navit.svn.sourceforge.net/svnroot/navit/trunk;module=navit;proto=https"

EXTRA_AUTORECONF = " -I m4"
