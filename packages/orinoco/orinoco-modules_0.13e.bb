DESCRIPTION = "A driver for wireless LAN cards based on Hermes(Orinoco) cards. \
Also contains support for cards using downloadable firmware, i.e. the Symbol/Socket family."
SECTION = "kernel/modules"
PRIORITY = "optional"
MAINTAINER = "Michael 'Mickey' Lauer <mickey@Vanille.de>"
LICENSE = "GPL"
PR = "r2"

SRC_URI = "http://ozlabs.org/people/dgibson/dldwd/orinoco-${PV}.tar.gz; \
           file://crosscompile.patch;patch=1 \
           file://monitor-${PV}.patch;patch=1 \
           file://spectrum*"
S = "${WORKDIR}/orinoco-${PV}"

inherit module

do_compile_prepend() {
	cp -f ${WORKDIR}/spectrum* ${S}/
}

do_install() {
	install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
        install -d ${D}/etc/pcmcia
	install -m 0644 *.o ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless/
        install -m 0644 spectrum.conf ${D}/etc/pcmcia/
        install -m 0644 hermes.conf ${D}/etc/pcmcia/

	# if PCI is disabled, don't ship the PCI modules
	if grep -q '#undef \+CONFIG_PCI' ${STAGING_KERNEL_DIR}/include/linux/autoconf.h; then
		for f in plx pci tmd; do
			rm -f ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless/orinoco_$f.o
		done
	fi
}
