DESCRIPTION = "The GIMP is the GNU Image Manipulation Program."
HOMEPAGE = "http://www.gimp.org"
SECTION = "x11/graphics"
LICENSE = "GPL"
PR = "r1"

DEPENDS = "sed-native libart-lgpl gtk+ jpeg libpng libexif tiff"

SRC_URI = "ftp://ftp.gimp.org/pub/gimp/v2.3/gimp-${PV}.tar.bz2 \
                 file://configure-libwmf.patch;patch=1"

inherit autotools pkgconfig

FILES_gimp-dbg =+ "${libdir}/gimp/2.0/modules/.debug \
                   ${libdir}/gimp/2.0/plug-ins/.debug"

#Don't laugh, this just builds a threaded gimp
EXTRA_OECONF = " --disable-gtktest \
                --disable-print \
                --disable-python \
                --enable-mp \
                --without-libwmf"

do_configure_append() {
        find ${S} -name Makefile | xargs sed -i s:'-I$(includedir)':'-I.':g
}

