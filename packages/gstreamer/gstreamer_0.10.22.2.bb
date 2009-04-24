DESCRIPTION = "GStreamer is a multimedia framework for encoding and decoding video and sound. \
It supports a wide range of formats including mp3, ogg, avi, mpeg and quicktime."
SECTION = "multimedia"
PRIORITY = "optional"
LICENSE = "LGPL"
HOMEPAGE = "http://www.gstreamer.net/"
MAINTAINER = "Andreas Frisch <andreas.frisch@multimedia-labs.de>"
DEPENDS = "libmatroska libxml2 glib-2.0 gettext-native popt"

PR = "r0"

inherit autotools pkgconfig

SRC_URI = "http://gstreamer.freedesktop.org/src/gstreamer/pre/gstreamer-${PV}.tar.bz2 \
file://gst-configure_skip_shave.patch;patch=1;pnum=0"

EXTRA_OECONF = "--disable-docs-build --disable-dependency-tracking --with-check=no"

do_configure_prepend() {
	for i in libtool ltoptions ltsugar ltversion lt~obsolete shave; do
		rm ${S}/common/m4/$i.m4 || /bin/true;
	done
}

do_stage() {
	oe_runmake install prefix=${STAGING_DIR} \
	       bindir=${STAGING_BINDIR} \
	       includedir=${STAGING_INCDIR} \
	       libdir=${STAGING_LIBDIR} \
	       datadir=${STAGING_DATADIR} \
	       mandir=${STAGING_DIR}/share/man
}

FILES_${PN} += " ${libdir}/gstreamer-0.10/*.so"
FILES_${PN}-dev += " ${libdir}/gstreamer-0.10/*.la ${libdir}/gstreamer-0.10/*.a"
