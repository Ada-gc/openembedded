DESCRIPTION = "Library of functions for 2D graphics"
SECTION = "x11/gnome"
LICENSE = "LGPL"
PR = "r2"

# can't use gnome.oeclass due to _ in filename
SRC_URI = "http://ftp.gnome.org/pub/GNOME/sources/libart_lgpl/2.3/libart_lgpl-${PV}.tar.bz2 \
       file://${HOST_SYS}/art_config.h \
       file://Makefile.am.patch;patch=1"

inherit autotools pkgconfig

DEPENDS = ""

FILES_${PN} = "${libdir}/*.so.*"
FILES_${PN}-dev += "${bindir}/libart2-config"

S = "${WORKDIR}/libart_lgpl-${PV}"

do_configure_prepend() {
	cp ${WORKDIR}/${HOST_SYS}/art_config.h ${S}
}

EXTRA_OECONF = "--disable-gtk-doc"

do_stage() {
	autotools_stage_includes
	oe_libinstall -a -so libart_lgpl_2 ${STAGING_LIBDIR}
}
