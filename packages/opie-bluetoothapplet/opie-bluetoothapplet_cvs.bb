require ${PN}.inc

PV = "${OPIE_CVS_PV}"
PR = "r2"

SRC_URI = "${HANDHELDS_CVS};module=opie/noncore/net/opietooth/applet \
           ${HANDHELDS_CVS};module=opie/pics/bluetoothapplet"

#           file://sysconfig-bluetooth.patch;patch=1"
