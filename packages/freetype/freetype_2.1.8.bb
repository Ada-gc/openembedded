SECTION = "libs"
LICENSE = "freetype"
DESCRIPTION = "Freetype font rendering library"

SRC_URI = "ftp://ftp.freetype.org/freetype/freetype2/freetype-${PV}.tar.bz2 \
	   file://configure.patch;patch=1"

FILES_${PN} = "${libdir}/lib*.so.*"
FILES_${PN}-dev += " ${bindir}"

inherit autotools pkgconfig binconfig

LIBTOOL = "${S}/builds/unix/${HOST_SYS}-libtool"
EXTRA_OEMAKE = "'LIBTOOL=${LIBTOOL}'"

do_configure () {
	cd builds/unix
	gnu-configize
	aclocal -I .
	autoconf
	cd ${S}
	oe_runconf
}

do_stage () {
	oe_libinstall -so -a -C objs libfreetype ${STAGING_LIBDIR}

	cp -a ${S}/include/*.h ${STAGING_INCDIR}
	install -d ${STAGING_INCDIR}/freetype2
	cp -a ${S}/include/freetype ${STAGING_INCDIR}/freetype2/
}
