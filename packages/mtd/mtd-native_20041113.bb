LICENSE = "GPLv2"
SECTION = "base"
include mtd_${PV}.bb
inherit native
DEPENDS = "patcher-native zlib-native"
FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/mtd"

do_stage () {
	for binary in ftl_format flash_erase flash_eraseall nanddump doc_loadbios \
		mkfs.jffs ftl_check mkfs.jffs2 flash_lock flash_unlock flash_info mtd_debug \
		flashcp nandwrite jffs2dump; do
		install -m 0755 util/$binary ${STAGING_BINDIR}/
	done
}
