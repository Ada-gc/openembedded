DESCRIPTION = "Python GTK+ Bindings"
SECTION = "devel/python"
PRIORITY = "optional"
MAINTAINER = "Michael 'Mickey' Lauer <mickey@Vanille.de>"
DEPENDS = "gtk+ libglade"
SRCNAME = "pygtk"
PR = "r1"
LICENSE = "LGPL"
SRC_URI = "ftp://ftp.gnome.org/pub/gnome/sources/pygtk/2.2/${SRCNAME}-${PV}.tar.bz2"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit autotools pkgconfig

FILES_${PN} = "${libdir}/python2.3/"

do_stage() {
	autotools_stage_includes
}

