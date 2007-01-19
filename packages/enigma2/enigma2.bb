DESCRIPTION = "Enigma2 is an experimental, but useful framebuffer-based frontend for DVB functions"
MAINTAINER = "Felix Domke <tmbinc@elitedvb.net>"
DEPENDS = "jpeg libungif libmad libpng libsigc++-1.2 gettext-native \
	dreambox-dvbincludes freetype libdvbsi++ python swig-native  \
	libfribidi gstreamer"
RDEPENDS = "python-codecs python-core python-lang python-re python-threading \
	python-xml python-fcntl gst-plugin-decodebin python-stringold \
	gst-plugin-id3demux gst-plugin-mad gst-plugin-ogg gst-plugin-playbin \
	gst-plugin-typefindfunctions gst-plugin-vorbis gst-plugin-audioconvert \
	gst-plugin-wavparse python-netclient gst-plugin-mpegstream"

RDEPENDS_append_dm7020 = " gst-plugin-ossaudio"
RDEPENDS_append_dm7025 = " gst-plugin-alsa alsa-conf"

# 'forward depends' - no two providers can have the same PACKAGES_DYNAMIC, however both
# enigma2 and enigma2-plugins produce enigma2-plugin-*.
DEPENDS += "enigma2-plugins"

PACKAGES_DYNAMIC = "enigma2-plugin-*"

PN = "enigma2"
PR = "r0"
SRCDATE = "20070120"

REL_MAJOR="2"
REL_MINOR="2"

PV = "${REL_MAJOR}.${REL_MINOR}cvs${SRCDATE}"
TAG = "enigma2_rel${REL_MAJOR}${REL_MINOR}"

SRC_URI = "cvs://anonymous@dreamboxupdate.com/cvs;module=enigma2;method=pserver;tag=${TAG};date=${SRCDATE} \
           file://enigma2.sh"

S = "${WORKDIR}/enigma2"

FILES_${PN} += "${datadir}/fonts"

inherit autotools pkgconfig

bindir = "/usr/bin"
sbindir = "/usr/sbin"

EXTRA_OECONF = "--enable-maintainer-mode --with-target=native --with-libsdl=no"

do_install_append() {
	install -m 0755 ${WORKDIR}/enigma2.sh ${D}/usr/bin/
}

python populate_packages_prepend () {
	enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)

	do_split_packages(d, enigma2_plugindir, '(.*?/.*?)/.*', 'enigma2-plugin-%s', 'Enigma2 Plugin: %s', recursive=True, match_path=True, prepend=True)
}
