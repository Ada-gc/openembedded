DESCRIPTION = "Utopia fonts - QPF Edition"
SECTION = "opie/fonts"
PRIORITY = "optional"
MAINTAINER = "Marcin Juszkiewicz <openembedded@hrw.one.pl>"
LICENSE = "GPL QPL"
HOMEPAGE = "http://www.pobox.sk/~mico/zaurus.html"
PACKAGE_ARCH = "all"

SRC_URI = "http://www.hrw.one.pl/_pliki/oe/files/qpf-utopia.tar.bz2"
S = "${WORKDIR}/utopia"

do_install () { 
        install -d ${D}${palmqtdir}/lib/fonts/ 
        for i in *.qpf; do 
                install -m 644 $i ${D}${palmqtdir}/lib/fonts/${i} 
        done 
} 

pkg_postinst () {
#!/bin/sh
if [ -n "$D" ]; then exit 1; fi
set -e
. /etc/profile
${sbindir}/update-qtfontdir
}

PACKAGES = "qpf-utopia-small qpf-utopia-large"

FILES_qpf-utopia-small = "${palmqtdir}/lib/fonts/utopia_100* ${palmqtdir}/lib/fonts/utopia_120*"

FILES_qpf-utopia-large = "${palmqtdir}/lib/fonts/utopia_140* \
${palmqtdir}/lib/fonts/utopia_180* ${palmqtdir}/lib/fonts/utopia_240*"
