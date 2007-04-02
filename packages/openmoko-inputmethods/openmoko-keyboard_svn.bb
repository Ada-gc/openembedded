DESCRIPTION = "Matchbox virtual keyboard for X11 - OpenMoko fork"
LICENSE = "GPL"
DEPENDS = "libfakekey expat libxft"
SECTION = "openmoko/inputmethods"
PV = "0.0+svn${SRCDATE}"
PR = "r1"

inherit openmoko autotools pkgconfig gettext

SRC_URI = "${OPENMOKO_MIRROR}/src/target/${OPENMOKO_RELEASE}/applications;module=${PN};proto=http"

S = "${WORKDIR}/${PN}"

EXTRA_OECONF = "--disable-cairo"

FILES_${PN} = "${bindir}/* \
	       ${datadir}/applications \
	       ${datadir}/pixmaps \
	       ${datadir}/openmoko-keyboard"

