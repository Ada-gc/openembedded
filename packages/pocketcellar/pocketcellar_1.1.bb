DESCRIPTION = "Program for wine enthusiasts"
SECTION = "opie/applications"
PRIORITY = "optional"
MAINTAINER = "Michael 'Mickey' Lauer <mickey@Vanille.de>"
LICENSE = "GPL"
PR = "r2"

SRC_URI = "http://www.staikos.net/~staikos/pocketcellar/pocketcellar-${PV}.tar.gz \
           file://gcc3.patch;patch=1"

inherit palmtop

EXTRA_QMAKEVARS_POST = " DESTDIR=pkg-cellar/home/QtPalmtop/bin/"

do_install() {
        install -d ${D}${palmtopdir}/bin \
        	   ${D}${palmtopdir}/apps/Applications \
        	   ${D}${palmtopdir}/pics \
			   ${D}${palmtopdir}/data/PocketCellar
			   
		install -m 644 pkg-pcellar/home/QtPalmtop/data/PocketCellar/* ${D}${palmtopdir}/data/PocketCellar/
# BAD.  packages -never- install files into home directories.  ever. --CL
#	cp -a pkg-pcellar/home/root/Settings/* ${D}/home/root/Settings/
        install -m 755 pkg-cellar/home/QtPalmtop/bin/pocketcellar ${D}${palmtopdir}/bin/
        install -m 644 pocketcellar.desktop ${D}${palmtopdir}/apps/Applications/
        install -m 644 pocketcellar.png ${D}${palmtopdir}/pics/
}

FILES_${PN} = "/"
