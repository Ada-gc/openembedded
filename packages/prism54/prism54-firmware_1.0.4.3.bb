DESCRIPTION = "Firmware for the Prism54 driver"
SECTION = "base"
PRIORITY = "optional"
MAINTAINER = "Bruno Randolf <bruno.randolf@4g-systems.biz>"
LICENSE = "GPL"

SRC_URI = "http://prism54.org/~mcgrof/firmware/${PV}.arm"

S = "${WORKDIR}/prism54.org"

do_install() {
	install -d ${D}/lib/firmware/
	install -m 0644 ${PV}.arm ${D}/lib/firmware/isl3890
}

FILES_${PN} = "/lib/firmware/"
