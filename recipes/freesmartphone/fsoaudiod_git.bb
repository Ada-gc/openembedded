require cornucopia.inc
inherit fso-plugin

DEPENDS += "alsa-lib"

# We need to uncomment the line below after the migration of all scenario files from
# fsodeviced to fsoaudiod is done. Otherwise we will get build errors as both fsodeviced
# and fsoaudiod are dependencies of the FSO framework.
# PROVIDES_${PN} = "openmoko-alsa-scenarios virtual/alsa-scenarios"

SRCREV = "${FSO_CORNUCOPIA_SRCREV}"
PV = "0.1.0+gitr${SRCPV}"
PE = "2"
PR = "${INC_PR}.1"

inherit update-rc.d

INITSCRIPT_NAME = "fsoaudiod"
INITSCRIPT_PARAMS = "defaults 30"

SRC_URI += "file://fsoaudiod"

CONFFILES_${PN} = " \
  ${sysconfdir}/freesmartphone/conf/palm_pre/fsoaudiod.conf \
"

do_install_append() {
  install -d ${D}${sysconfdir}/init.d/
  install -m 0755 ${WORKDIR}/fsoaudiod ${D}${sysconfdir}/init.d/
}
