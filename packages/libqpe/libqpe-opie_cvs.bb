require ${PN}.inc

PV = "${OPIE_CVS_PV}"
PR = "r6"

SRC_URI = "${HANDHELDS_CVS};module=opie/library \
           file://fix-titleheight.patch;patch=1"

