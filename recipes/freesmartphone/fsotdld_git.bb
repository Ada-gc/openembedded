require cornucopia.inc
inherit fso-plugin
PR = "${INC_PR}.2"
PV = "0.0.0+gitr${SRCPV}"
PE = "1"

DEPENDS += "libfsotransport libfsoresource"
