DESCRIPTION = "Qt/Embedded terminus font"
HOMEPAGE = "http://www.is-vn.bg/hamster/jimmy-en.html"
SECTION = "opie/fonts"
PRIORITY = "optional"
MAINTAINER = "M&N Solutions GmbH <info@mn-solutions.de>"
LICENSE = "GPL"
RDEPENDS = "qte-fonts-common"
PACKAGE_ARCH = "all"
PV = "1.0"
PR = "r1"

SRC_URI = "http://www.mn-solutions.de/downloads/mnci/terminus-fonts.tar.bz2"
S = ${WORKDIR}/terminus-fonts


do_install() {
	mkdir -p ${D}${palmqtdir}/lib/fonts
	cp *.qpf ${D}${palmqtdir}/lib/fonts
}

PACKAGES = "qpf-terminus"
FILES = ""
FILES_qpf-terminus = "${palmqtdir}/lib/fonts"

pkg_postinst() {
#!/bin/sh
if [ -n "$D" ]; then exit 1; fi
set -e
. /etc/profile
${sbindir}/update-qtfontdir
}
