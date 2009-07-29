DESCRIPTION = "High Level DBus Introspection Daemon"
AUTHOR = "M. Lauer et. al."
SECTION = "console/network"
DEPENDS = "vala-native dbus dbus-glib"
LICENSE = "GPL"
PV = "0.9.0+gitr${SRCPV}"
PR = "r0"
PE = "1"

SRC_URI = "${FREESMARTPHONE_GIT}/dbus-hlid.git;protocol=git;branch=master"
S = "${WORKDIR}/git"

inherit autotools

FILES_${PN} += "${datadir} ${sysconfdir}"
