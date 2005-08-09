include edb_${PV}.bb
inherit native
DEPENDS = "zlib-native"

do_stage () {
	install -m 0755 tools/.libs/edb_ed ${STAGING_BINDIR}
	oe_libinstall -C src libedb ${STAGING_LIBDIR}/
	install -m 0644 ${S}/src/Edb.h ${STAGING_INCDIR}/
}

do_install() {
	:
}

