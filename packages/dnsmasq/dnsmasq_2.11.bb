SECTION = "console/network"
LICENSE = "GPL"
DESCRIPTION = "Dnsmasq is a lightweight, easy to configure \
DNS forwarder and DHCP server."
MAINTAINER = "Chris Larson <kergoth@handhelds.org>"

SRC_URI = "http://www.thekelleys.org.uk/dnsmasq/dnsmasq-${PV}.tar.gz"
S = "${WORKDIR}/dnsmasq-${PV}"

do_install () {
	oe_runmake "PREFIX=${D}${prefix}" \
		   "BINDIR=${D}${bindir}" \
		   "MANDIR=${D}${mandir}" \
		   install
}
