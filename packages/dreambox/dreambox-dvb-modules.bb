DESCRIPTION = "Hardware drivers for DM702x"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "Felix Domke <tmbinc@elitedvb.net>"

KV_dm7020 = "2.6.9"
PV_dm7020 = "${KV}-20060111"

KV_dm7025 = "2.6.12"
PV_dm7025 = "${KV}-20060311"

RDEPENDS = "kernel (${KV})"
PR = "r0"

SRC_URI = "http://sources.dreamboxupdate.com/snapshots/dreambox-dvb-modules-${MACHINE}-${PV}.tar.bz2 file://dream "
SRC_URI_append_dm7025 = "http://sources.dreamboxupdate.com/download/7020/fpupgrade-${MACHINE}-v2"

S = "${WORKDIR}"

inherit update-rc.d

INITSCRIPT_NAME = "dream"
INITSCRIPT_PARAMS = "start 39 S ."

do_install_dm7020() {
	install -d ${D}/etc/init.d
	install -d ${D}/lib/modules/${KV}/extra
	install -m 0755 ${WORKDIR}/dream ${D}/etc/init.d/
	for f in head; do
		install -m 0644 $f.ko ${D}/lib/modules/${KV}/extra/$f.ko;
	done
}

do_install_dm7025() {
	install -d ${D}/etc/init.d
	install -d ${D}/lib/modules/${KV}/extra
	install -m 0755 ${WORKDIR}/dream ${D}/etc/init.d/
	for f in alps_bsbe1.ko avs.ko dreambox_rc2.ko fp.ko lcd.ko \
		mb86a15.ko nec_ir.ko rfmod.ko stb-proc.ko cu1216mk3.ko \
		tu1216.ko xilleon.ko LICENSE; do
		install -m 0644 ${WORKDIR}/$f ${D}/lib/modules/${KV}/extra/$f;
	done
	install -d ${D}${sbindir}
	install -m 0755 ${WORKDIR}/fpupgrade-${MACHINE}-v2 ${D}${sbindir}/fpupgrade
}

PACKAGE_ARCH := "${MACHINE_ARCH}"
FILES_${PN} = "/"
