include evas-fb_${PV}.bb
inherit native
DEPENDS = "freetype-native"

export EDB_CONFIG = "${STAGING_BINDIR}/edb-config-native"
export EET_CONFIG = "${STAGING_BINDIR}/eet-config-native"

do_stage () {
        for i in ${headers}
        do
                install -m 0644 ${S}/src/lib/$i ${STAGING_INCDIR}/
        done
        oe_libinstall -C src/lib libevas ${STAGING_LIBDIR}/
}

#FIXME: Conflicts with zlib-devel on the build machine. Remove it and it builds.
