DESCRIPTION = "A driver for wireless LAN cards based on Intersil's Prism2/2.5/3 chipset"
SECTION = "kernel/modules"
PRIORITY = "optional"
MAINTAINER = "Michael 'Mickey' Lauer <mickey@Vanille.de>"
LICENSE = "GPL"
PR = "r2"

SRC_URI = "http://hostap.epitest.fi/releases/hostap-driver-${PV}.tar.gz \
           file://hostap_cs.conf"
S = "${WORKDIR}/hostap-driver-${PV}"

inherit module

MAKE_TARGETS = "hostap pccard"

do_install() {
	install -d ${D}/lib/modules/${KERNEL_VERSION}/net \
		   ${D}/lib/modules/${KERNEL_VERSION}/pcmcia \
        	   ${D}/${sysconfdir}/pcmcia
	install -m 0644 driver/modules/hostap_crypt_wep.o ${D}/lib/modules/${KERNEL_VERSION}/net/
	install -m 0644 driver/modules/hostap.o ${D}/lib/modules/${KERNEL_VERSION}/net/
	install -m 0644 driver/modules/hostap_cs.o ${D}/lib/modules/${KERNEL_VERSION}/pcmcia/
	install -m 0644 driver/etc/hostap_cs.conf ${D}/${sysconfdir}/pcmcia/hostap_cs.conf                
	cat ${WORKDIR}/hostap_cs.conf >>${D}/${sysconfdir}/pcmcia/hostap_cs.conf
}
