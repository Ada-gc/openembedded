inherit gpe
LICENSE = "GPL"
DESCRIPTION = "A package manager GUI for GPE"
DEPENDS = "libipkg libgpewidget libdisplaymigration libgpepimc"
RDEPENDS = "gpe-icons"
SECTION = "gpe"
PRIORITY = "optional"

SRC_URI += "file://fix_makefiles.patch;patch=1 \
	    file://fix_interface_c.patch;patch=1"

do_compile() {
	oe_runmake STAGING_INCDIR="${STAGING_INCDIR}"
}
