require bluez-utils.inc

PR = "r3"

DEPENDS += "dbus"
SRC_URI += "file://dbus-2.24.patch;patch=1 \
	    file://hciattach-ti-bts.patch;patch=1"
EXTRA_OECONF += "--with-dbus"
