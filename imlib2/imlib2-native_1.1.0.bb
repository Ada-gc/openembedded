SECTION = "libs"
include imlib2_${PV}.bb
inherit native
DEPENDS = "freetype-native"
EXTRA_OECONF = "--without-x"
PR = "r1"

FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/imlib2-${PV}"

do_configure () {
        rm -f ${S}/libltdl/acinclude.m4
        autotools_do_configure
}

do_stage () {
        oe_libinstall -a -so -C src libImlib2 ${STAGING_LIBDIR}/
        install -m 0644 ${S}/src/Imlib2.h ${STAGING_INCDIR}/
	install -d ${STAGING_LIBDIR}/imlib2/loaders/image/
	install -m 0755 loaders/.libs/*.so ${STAGING_LIBDIR}/imlib2/loaders/image/
}
