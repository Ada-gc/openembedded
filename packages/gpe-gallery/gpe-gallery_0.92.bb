LICENSE = "GPL"
PR = "r3"

inherit gpe

DEPENDS = "libgpewidget"
SECTION = "gpe"
MAINTAINER = "Florian Boor <florian.boor@kernelconcepts.de>"

FILES_${PN} += "${datadir}/gpe/pixmaps"
FILES_${PN} += "${datadir}/application-registry"

DESCRIPTION = "GPE image viewer application"
export CVSBUILD = no

SRC_URI = "${GPE_MIRROR}/gpe-gallery-${PV}.tar.gz \
	file://desktop-categories.patch;patch=1 \
	file://mcheck.patch;patch=1"
