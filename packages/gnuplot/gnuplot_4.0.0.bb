DESCRIPTION = "Gnuplot is a portable command-line driven interactive datafile \
(text or binary) and function plotting utility."
SECTION = "x11/utils"
LICENSE = "BSD-4"
PRIORITY = "optional"
MAINTAINER = "Chris Larson <kergoth@handhelds.org>"
DEPENDS = "x11 libpng gd readline"
PR = "r1"

SRC_URI = "ftp://ftp.gnuplot.info/pub/gnuplot/gnuplot-${PV}.tar.gz \
	   file://subdirs.patch;patch=1 \
	   file://gnuplot.desktop \
	   file://gnuplot.png"

inherit autotools 
acpaths = ""
EXTRA_OECONF = "--with-readline=${STAGING_LIBDIR}/.. \
		--without-plot \
		--with-png=${STAGING_LIBDIR}/.. \
		--with-gd=${STAGING_LIBDIR}/.. \
		--without-lisp-files \
		--without-tutorial"

do_install_append() {
	install -d ${D}${datadir}/applications/
	install -m 0644 ${WORKDIR}/gnuplot.desktop ${D}${datadir}/applications/
	install -d ${D}${datadir}/pixmaps/
	install -m 0644 ${WORKDIR}/gnuplot.png ${D}${datadir}/pixmaps/
}
