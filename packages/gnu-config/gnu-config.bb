SECTION = "base"
PV = "0.1"
LICENSE = "GPL"
MAINTAINER = "Chris Larson <kergoth@handhelds.org>"
SRC_URI = "ftp://ftp.club.cc.cmu.edu/pub/gnu/config/config.guess \
	   ftp://ftp.club.cc.cmu.edu/pub/gnu/config/config.sub \
	   file://uclibc.patch;patch=1;pnum=0 \
	   file://gnu-configize.in"
S = "${WORKDIR}" 

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = ""
PACKAGES = "${PN}"
FILES_${PN} = "${bindir} \
	       ${datadir}/gnu-config"

do_install () {
	install -d ${D}/${datadir}/gnu-config \
		   ${D}/${bindir}
	cat ${WORKDIR}/gnu-configize.in | \
		sed -e 's,@gnu-configdir@,${datadir}/gnu-config,' \
		    -e 's,@autom4te_perllibdir@,${STAGING_DATADIR}/autoconf,' > ${STAGING_BINDIR}/gnu-configize
	chmod 755 ${D}/${bindir}/gnu-configize
	install -m 0644 config.guess config.sub ${D}/${datadir}/gnu-config/
}
