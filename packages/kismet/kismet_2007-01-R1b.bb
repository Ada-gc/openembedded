SECTION = "console/network"
DESCRIPTION = "Kismet is an 802.11 layer2 wireless network detector, sniffer, and intrusion detection system"
HOMEPAGE = "http://www.kismetwireless.net/"
LICENSE = "GPLv2"
DEPENDS = "expat gmp"

SRC_URI = "http://www.kismetwireless.net/code/kismet-2007-01-R1b.tar.gz \
           file://no-chmod.patch;patch=1"


EXTRA_OECONF = "--with-pcap=linux --disable-setuid --with-linuxheaders=${STAGING_KERNEL_DIR}/include --disable-gpsmap"

inherit autotools

do_configure() {
        oe_runconf
}

do_install_append() {
	if test -e ${WORKDIR}/kismet.conf; then
		install -m 644 ${WORKDIR}/kismet.conf ${D}${sysconfdir}/
	fi
}

PACKAGES =+ "kismet-sounds"
FILES_kismet-sounds = "/usr/share/kismet/wav"

CONFFILES_${PN}_nylon = "${sysconfdir}/kismet.conf"
