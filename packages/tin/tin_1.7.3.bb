DESCRIPTION = "Tin is a powerful text mode news reader."
SECTION = "console/network"
DEPENDS = "ncurses"
LICENSE = "GPL"
PR = "r3"

SRC_URI = "ftp://ftp.tin.org/pub/news/clients/tin/unstable/tin-${PV}.tar.gz \
	   file://makecfg-buildcc.patch;patch=1 \
	   file://m4.patch;patch=1 \
	   file://configure.patch;patch=1"

inherit autotools 

EXTRA_OECONF = "--with-screen=ncurses"
CFLAGS += "-DM_UNIX"

do_compile() {
        ${BUILD_CC} -DLINK_SIZE=2 -I${S}/include -c pcre/dftables.c
        ${BUILD_CC} dftables.o -o pcre/dftables
        oe_runmake build
}
