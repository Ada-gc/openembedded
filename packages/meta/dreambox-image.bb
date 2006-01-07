export IMAGE_BASENAME = "dreambox-image"

OPENDREAMBOX_COMMON = "base-files busybox \
	ipkg initscripts sysvinit netbase dropbear tinylogin \
	base-passwd ncurses joe mc vsftpd timezones-alternative \
	netkit-base fakelocale less dreambox-bootlogo  \
	dreambox-dccamd dreambox-keymaps tuxbox-image-info dvbsnoop \
	dreambox-compat tuxbox-common"

OPENDREAMBOX_COMMON_D = "util-linux e2fsprogs \
	ppp module-init-tools modutils-initscripts"

OPENDREAMBOX_COMMON_R = "util-linux-sfdisk util-linux-fdisk e2fsprogs-mke2fs e2fsprogs-e2fsck \
	ppp	module-init-tools-depmod \
	modutils-initscripts \
	base-files-doc"

# experimental packages
OPENDREAMBOX_EXPERIMENTAL = "enigma2"

# legacy tuxbox stuff (enigma, plugins, ...)
OPENDREAMBOX_TUXBOX = "enigma ipkgpl dreamdata enigma-modem"
OPENDREAMBOX_TUXBOX_D = "tuxbox-plugins links-dream"
OPENDREAMBOX_TUXBOX_R = " \
	tuxbox-plugin-snake     tuxbox-plugin-tuxmail \
	tuxbox-plugin-lcdcirc   tuxbox-plugin-soko      tuxbox-plugin-tuxtxt \
	tuxbox-plugin-sol       tuxbox-plugin-vierg  	tuxbox-plugin-master \
	tuxbox-plugin-solitair  tuxbox-plugin-yahtzee 	tuxbox-plugin-mines  \
	tuxbox-plugin-tank  	tuxbox-plugin-pacman    tuxbox-plugin-tetris \
	tuxbox-plugin-satfind   tuxbox-plugin-tuxcom 	links-dream-plugin \
	links-dream-plugin"

# dvb api specific stuff
OPENDREAMBOX_V2_ONLY = "dreambox-dvb-tools tuxbox-stream"
OPENDREAMBOX_V3_ONLY = "dreambox-dvb-tools-v3 dvbtune sctzap dvbtraffic"


# enigma languages
# disabled: enigma-locale-ar enigma-locale-sr enigma-locale-ur
ENIGMA_LANGUAGE = "enigma-locale-cs enigma-locale-da \
	enigma-locale-de enigma-locale-el enigma-locale-es enigma-locale-et \
	enigma-locale-fi enigma-locale-fr enigma-locale-hr enigma-locale-hu \
	enigma-locale-is enigma-locale-it enigma-locale-lt enigma-locale-nl \
	enigma-locale-no enigma-locale-pl enigma-locale-pt enigma-locale-ro \
	enigma-locale-ru enigma-locale-sk enigma-locale-sl \
	enigma-locale-sv enigma-locale-tr"

OPENDREAMBOX_TUXBOX_R += " ${ENIGMA_LANGUAGE}"

# now machine specific:
OPENDREAMBOX_COMMON_MACHINE_dm7020 += "${OPENDREAMBOX_V2_ONLY} ${OPENDREAMBOX_TUXBOX}"
OPENDREAMBOX_COMMON_MACHINE_R_dm7020 += "${OPENDREAMBOX_TUXBOX_R}"
OPENDREAMBOX_COMMON_MACHINE_D_dm7020 += "${OPENDREAMBOX_TUXBOX_D}"
OPENDREAMBOX_COMMON_MACHINE_dm7025 += "${OPENDREAMBOX_V3_ONLY} ${OPENDREAMBOX_EXPERIMENTAL}"
OPENDREAMBOX_COMMON_MACHINE_R_dm7025 += ""
OPENDREAMBOX_COMMON_MACHINE_D_dm7025 += ""

# collect the stuff into OPENDREAMBOX_COMMON
OPENDREAMBOX_COMMON += " ${OPENDREAMBOX_COMMON_MACHINE}"
OPENDREAMBOX_COMMON_R += " ${OPENDREAMBOX_COMMON_MACHINE_R}"
OPENDREAMBOX_COMMON_D += " ${OPENDREAMBOX_COMMON_MACHINE_D}"

# add bootstrap stuff
DEPENDS = "${OPENDREAMBOX_COMMON} ${BOOTSTRAP_EXTRA_DEPENDS} ${OPENDREAMBOX_COMMON_D}"
RDEPENDS = "ncurses-terminfo ${OPENDREAMBOX_COMMON} ${BOOTSTRAP_EXTRA_RDEPENDS} ${OPENDREAMBOX_COMMON_R}"

# we don't want any locales, at least not in the common way.
IMAGE_LINGUAS = " "

export IPKG_INSTALL = '${RDEPENDS}'

inherit image_ipk
