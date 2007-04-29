DESCRIPTION = "MPEG Video Decoder Library"
SECTION = "libs"
PRIORITY = "optional"
DEPENDS = "virtual/libsdl"
LICENSE = "GPL"

PR = "r1"

SRC_URI = "http://libmpeg2.sourceforge.net/files/mpeg2dec-${PV}.tar.gz \
			file://Makefile.patch;patch=1 "
S = "${WORKDIR}/mpeg2dec-0.4.0"

inherit autotools lib_package

EXTRA_OECONF="--enable-shared"

LEAD_SONAME = "libmpeg2.so.0"

do_stage() {
	oe_libinstall -a -so -C libmpeg2 libmpeg2 ${STAGING_LIBDIR}
	oe_libinstall -a -so -C libmpeg2/convert libmpeg2convert ${STAGING_LIBDIR}

	install -d ${STAGING_INCDIR}/mpeg2dec/
	install -m 0644 include/mpeg2.h ${STAGING_INCDIR}/mpeg2dec/
	install -m 0644 include/mpeg2convert.h ${STAGING_INCDIR}/mpeg2dec/
}
