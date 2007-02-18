DESCRIPTION = "Web server"
SECTION = "net"
DEPENDS = "sqlite3 libpcre libxml2"
LICENSE = "BSD"
PR = "r5"

SRC_URI = "http://www.lighttpd.net/download/lighttpd-1.4.13.tar.gz \
	   file://configure.in.patch;patch=1 \
	   file://src-server.c.patch;patch=1 \
	   file://index.html \
	   file://lighttpd.conf \
	   file://lighttpd"

EXTRA_OECONF="--without-bzip2 \
		--without-ldap \
		--without-lua \
		--without-memcache \
		--with-pcre \
		--with-webdav-props \
		--with-webdav-locks \
		--without-openssl \
		--disable-nls \
		--disable-static"

inherit autotools pkgconfig update-rc.d

INITSCRIPT_NAME = "lighttpd"
INITSCRIPT_PARAMS = "defaults 70"

do_install_append() {
	install -d ${D}${sysconfdir}/init.d ${D}/www/logs ${D}/www/pages/dav ${D}/www/var
	install -m 0755 ${WORKDIR}/lighttpd ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/lighttpd.conf ${D}${sysconfdir}
	install -m 0644 ${WORKDIR}/index.html ${D}/www/pages/
}

do_stage() {
	autotools_stage_all
}

FILES_${PN} += "${libdir}/mod_*.so ${sysconfdir} /www"
