DESCRIPTION = "A Client for Wi-Fi Protected Access (WPA)."
SECTION = "network"
LICENSE = "GPL"
MAINTAINER = "Holger Schurig"
HOMEPAGE = "http://hostap.epitest.fi/wpa_supplicant/"
DEPENDS = "openssl"
DEPENDS_mtx-1_append = "madwifi-modules"
DEPENDS_mtx-2_append = "madwifi-modules"
PR = "r3"

SRC_URI = "http://hostap.epitest.fi/releases/wpa_supplicant-${PV}.tar.gz \
	file://madwifi-bsd-fix.diff;patch=1;pnum=0 \
	file://defconfig \
        file://driver-hermes.patch;patch=1 \
	file://driver-zd1211.patch;patch=1 \
	file://wpa_supplicant-ralink_rt73.patch;patch=1 \
	file://wpa_supplicant-ralink_rt73-fix.patch;patch=1 \
	file://wpa_supplicant.conf"

DEPENDS_dm8000_append = "madwifi-ng"
TARGET_CFLAGS_dm8000_append = " -I${STAGING_INCDIR}/madwifi-ng"

S = "${WORKDIR}/wpa_supplicant-${PV}"

do_configure () {
	install -m 0755 ${WORKDIR}/defconfig  .config
}

do_compile () {
	make
}

do_install () {
	install -d ${D}${sbindir}
	install -m 755 wpa_supplicant ${D}${sbindir}
	install -m 755 wpa_passphrase ${D}${sbindir}
	install -m 755 wpa_cli        ${D}${sbindir}

	install -d ${D}${sysconfdir}
	install -m 644 ${WORKDIR}/wpa_supplicant.conf ${D}${sysconfdir}

	install -d ${D}${docdir}/wpa_supplicant
	install -m 644 README ${D}${docdir}/wpa_supplicant
}
