SECTION = "opie/base"
include qte_${PV}-snapshot.bb

FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/qte-2.3.8-snapshot"

DESCRIPTION = "Qt/Embedded for Qtopia version ${PV}"

SRC_URI_append = " file://qtopia.patch;patch=1 \
		file://qconfig-qpe.h \
		file://sharp_char.h \
		file://switches.h "

#		file://devfs.patch;patch=1 \
