DESCRIPTION = "A driver for wireless LAN cards based on Hermes(Orinoco) cards. \
Also contains support for cards using downloadable firmware, i.e. the Symbol/Socket family."
SECTION = "kernel/modules"
PRIORITY = "optional"
PROVIDES = "spectrum-modules"
MAINTAINER = "Michael 'Mickey' Lauer <mickey@Vanille.de>"
LICENSE = "GPL"
PR = "r3"

# seems to cause problems on arm
DEFAULT_PREFERENCE_arm = "-1"

export EXTRACFLAGS = "-mstructure-size-boundary=32"

SRC_URI = "http://ozlabs.org/people/dgibson/dldwd/orinoco-${PV}.tar.gz \
           file://list-move.patch;patch=1 \
           file://spectrum-firmware.patch;patch=1 \
           file://spectrum.conf \
           file://spectrum_fw.h \
	   file://orinoco_cs.conf"
S = "${WORKDIR}/orinoco-${PV}"

inherit module

do_compile_prepend() {
	install ${WORKDIR}/spectrum_fw.h ${S}/
}

do_install() {
	install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
        install -d ${D}/etc/pcmcia
	install -m 0644 *.o ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless/
        install -m 0644 ${WORKDIR}/spectrum.conf ${D}/etc/pcmcia/
        install -m 0644 hermes.conf ${D}/etc/pcmcia/
	install -d ${D}/etc/modutils
	install -m 0644 ${WORKDIR}/orinoco_cs.conf ${D}/etc/modutils/
}

