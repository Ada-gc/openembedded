LICENSE = GPL
# libxine OE build file
# Modified by Advanced Micro Devices, Inc.

#FIXME: libxine doesn't build when X11 has been built before:
#| In file included from video_out_dxr3.c:55:
#| /local/pkg/oe/collie/tmp/staging/arm-linux/include/X11/extensions/Xinerama.h:16: error: parse error before "Bool"
#| /local/pkg/oe/collie/tmp/staging/arm-linux/include/X11/extensions/Xinerama.h:17: error: parse error before '*' token
#| /local/pkg/oe/collie/tmp/staging/arm-linux/include/X11/extensions/Xinerama.h:20: warning: type defaults to `int' in declaration of 
#`XineramaQueryExtension'
#| /local/pkg/oe/collie/tmp/staging/arm-linux/include/X11/extensions/Xinerama.h:20: warning: data definition has no type or storage class
#| /local/pkg/oe/collie/tmp/staging/arm-linux/include/X11/extensions/Xinerama.h:22: error: parse error before "XineramaQueryVersion"
#| /local/pkg/oe/collie/tmp/staging/arm-linux/include/X11/extensions/Xinerama.h:23: error: parse error before '*' token
#| /local/pkg/oe/collie/tmp/staging/arm-linux/include/X11/extensions/Xinerama.h:26: warning: type defaults to `int' in declaration of 
#`XineramaQueryVersion'
#| /local/pkg/oe/collie/tmp/staging/arm-linux/include/X11/extensions/Xinerama.h:26: warning: data definition has no type or storage class
#| /local/pkg/oe/collie/tmp/staging/arm-linux/include/X11/extensions/Xinerama.h:28: error: parse error before "XineramaIsActive"
#| /local/pkg/oe/collie/tmp/staging/arm-linux/include/X11/extensions/Xinerama.h:28: error: parse error before '*' token
#| /local/pkg/oe/collie/tmp/staging/arm-linux/include/X11/extensions/Xinerama.h:28: warning: type defaults to `int' in declaration of 
#`XineramaIsActive'
#| /local/pkg/oe/collie/tmp/staging/arm-linux/include/X11/extensions/Xinerama.h:28: warning: data definition has no type or storage class
#| /local/pkg/oe/collie/tmp/staging/arm-linux/include/X11/extensions/Xinerama.h:41: error: parse error before '*' token
#| In file included from /local/pkg/oe/collie/tmp/staging/arm-linux/include/inttypes.h:28,
#|                  from ../../src/xine-engine/xine_internal.h:31,
#|                  from video_out_dxr3.c:65:
#| /local/pkg/oe/collie/tmp/staging/arm-linux/include/stdint.h:49: error: syntax error before "typedef"

DESCRIPTION = "libxine"
SECTION = "libs"
PRIORITY = "optional"
MAINTAINER = "Pawel Osiczko <p.osiczko@tetrapyloctomy.org>"
DEPENDS = "zlib libogg libvorbis libmad"
PROVIDES = "virtual/libxine"

inherit autotools pkgconfig gettext

S = "${WORKDIR}/xine-lib-${PV}"

SRC_URI = "http://heanet.dl.sourceforge.net/sourceforge/xine/xine-lib-${PV}.tar.gz \
	file://cpu-${PV}.patch;patch=1 \
	file://configure-${PV}.patch;patch=1"

SOV = "1.0.7"

# Omit the annoying xine-config in ${bindir}
FILES_${PN}="${libdir}/*.so*"

# And include it in the dev package
FILES_${PN}-dev += " ${bindir}"

EXTRA_OECONF="-with-zlib-path=${STAGING_DIR}/${HOST_SYS} \
	--with-vorbis-prefix=${STAGING_DIR}/${HOST_SYS} \
	--disable-oggtest \
	--with-ogg-prefix=${STAGING_DIR}/${HOST_SYS} \
	--disable-altivec --disable-vis --disable-mlib \
	--enable-shared --disable-static \
	--disable-fb --disable-alsa --disable-vcd \
	--disable-asf --disable-faad --disable-iconv \
	--disable-aalib --with-xv-path=${STAGING_LIBDIR} \
	--without-v4l --without-arts --without-sdl"
			      
do_compile() {
	oe_runmake LIBTOOL=${S}/${TARGET_SYS}-libtool
}

do_install() {
	oe_runmake DESTDIR=${D} LIBTOOL=${S}/${TARGET_SYS}-libtool install
}

HEADERS="src/xine-engine/xineintl.h src/xine-utils/xineutils.h            \
	src/xine-engine/xine_internal.h  src/xine-engine/xine_plugin.h    \
	src/xine-utils/xine_buffer.h     src/xine-engine/video_out.h      \
	src/xine-engine/buffer.h         src/xine-engine/vo_scale.h       \
	src/xine-engine/configfile.h     src/xine-utils/attributes.h      \
	src/xine-engine/info_helper.h    src/xine-engine/scratch.h        \
	src/xine-engine/audio_decoder.h  src/input/input_plugin.h         \
	src/xine-engine/spu_decoder.h    src/xine-engine/audio_out.h      \
	src/xine-engine/io_helper.h      src/xine-engine/video_decoder.h  \
	src/xine-engine/broadcaster.h    src/xine-engine/metronom.h       \
	src/xine-utils/xmllexer.h        src/xine-engine/osd.h            \
	src/xine-engine/video_overlay.h  src/xine-utils/xmlparser.h       \
	src/xine-utils/compat.h          src/xine-engine/plugin_catalog.h \
	src/xine-engine/post.h           src/demuxers/demux.h             \
	src/xine-engine/resample.h	 lib/os_types.h                   \
	src/xine-engine/refcounter.h"

do_stage() {
	install -d ${STAGING_INCDIR}/xine

	install -m 0644 ${S}/include/xine.h ${STAGING_INCDIR}

	for file in ${HEADERS}; do
		cp ${S}/$file ${STAGING_INCDIR}/xine/`basename $file`
	done

	oe_libinstall -so -C src/xine-engine libxine ${STAGING_LIBDIR}
}

python populate_packages_prepend () {
	plugindir = bb.data.expand('${libdir}/xine/plugins/1.0.0', d)
	do_split_packages(d, plugindir, '^xineplug_(.*)\.so$', 'libxine-plugin-%s', 'Xine plugin for %s', extra_depends='' )

	postdir = bb.data.expand('${libdir}/xine/plugins/1.0.0/post', d)
	do_split_packages(d, postdir, '^xineplug_(.*)\.so$', 'libxine-plugin-%s', 'Xine plugin for %s', extra_depends='' )

	fontdir = bb.data.expand('${datadir}/xine/libxine1/fonts', d)
	do_split_packages(d, fontdir, '^(.*).xinefont.gz$', 'libxine-font-%s', 'Xine font %s', extra_depends='' )
}
