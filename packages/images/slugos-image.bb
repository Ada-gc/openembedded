# This describes a generic SlugOS image, even though the bb file is
# called 'slugos-image.bb' the distro specific configuration is
# done in conf/distro/${DISTRO}.conf (which should always include
# conf/distro/slugos.conf to get the standard settings).
#
DESCRIPTION = "Generic SlugOS image"
HOMEPAGE = "http://www.nslu2-linux.org"
LICENSE = "MIT"
PR = "r43"

COMPATIBLE_MACHINE = "nslu2"

# SLUGOS_IMAGENAME defines the name of the image to be build, if it
# is not set this package will be skipped!
IMAGE_BASENAME = "${SLUGOS_IMAGENAME}"
IMAGE_NAME = "${IMAGE_BASENAME}-${MACHINE}-${DISTRO_VERSION}"
IMAGE_FSTYPES = "jffs2"
EXTRA_IMAGECMD_jffs2 = "--pad --${SLUGOS_IMAGESEX} --eraseblock=0x20000 -D ${SLUGOS_DEVICE_TABLE}"
IMAGE_LINGUAS = ""

# Setting USE_DEVFS prevents *any* entries being created initially
# in /dev
USE_DEVFS = "1"

# This is passed to the image command to build the correct /dev
# directory (because only the image program can make actual
# dev entries!)
SLUGOS_DEVICE_TABLE = "${@bb.which(bb.data.getVar('BBPATH', d, 1), 'files/device_table-slugos.txt')}"

# IMAGE_PREPROCESS_COMMAND is run before making the image.  In SlugOS the
# kernel image is removed from the root file system to recover the space used -
# SlugOS is assumed to boot from a separate kernel image in flash (not in the
# root file system), if this is not the case the following must not be done!
IMAGE_PREPROCESS_COMMAND += "rm ${IMAGE_ROOTFS}/boot/zImage*;"
IMAGE_PREPROCESS_COMMAND += "install -c -m 644 ${SLUGOS_DEVICE_TABLE} ${IMAGE_ROOTFS}/etc/device_table;"

# Building a full image.  If required do a post-process command which builds
# the full flash image using slugimage.  At present this only works for NSLU2 images.
PACK_IMAGE = ""
IMAGE_POSTPROCESS_COMMAND += "${PACK_IMAGE}"
PACK_IMAGE_DEPENDS = ""
EXTRA_IMAGEDEPENDS += "${PACK_IMAGE_DEPENDS}"

# This hack removes '${MACHINE}' from the end of the arch.conf for ipk,
# preventing _mach.ipk (with no byte sex) taking precedence over everything
# else.
ROOTFS_POSTPROCESS_COMMAND += "sed -i '$d' '${IMAGE_ROOTFS}/etc/ipkg/arch.conf';"

# These depends define native utilities - they do not get put in the flash and
# are not required to build the image.
IMAGE_TOOLS = ""
EXTRA_IMAGEDEPENDS += "${IMAGE_TOOLS}"

# CONFIG:
# SLUGOS_EXTRA_RDEPENDS: set in conf, things to add to the image
# SLUGOS_SUPPORT:        set here, see below, added to the image.
# SLUGOS_KERNEL:         set here, kernel modules added to the image
#
# Do not override the last two unless you really know what you
# are doing - there is more information below.

# diff, cpio and find are required for reflash and turnup ram.
# Removing these probably leaves the system bootable, but standard
# openslug and ucslugc stuff won't work, so only take these out in
# very non-standard turnkey slugos builds.
#
# udev is the default way of handling devices, there is no guarantee
# that the static device table is completely correct (it is just
# known to be sufficient for boot.)
SLUGOS_SUPPORT ?= "diffutils cpio findutils udev"

SLUGOS_KERNEL ?= ""

SLUGOS_EXTRA_RDEPENDS ?= ""

RDEPENDS = "kernel ixp4xx-npe \
	base-files base-passwd netbase \
        busybox initscripts-slugos slugos-init \
        update-modules sysvinit tinylogin \
	module-init-tools modutils-initscripts \
        ipkg-collateral ipkg ipkg-link \
	portmap \
	dropbear \
	beep \
	e2fsprogs-blkid \
	util-linux-mount \
	util-linux-umount \
	util-linux-swaponoff \
	util-linux-losetup \
	${SLUGOS_SUPPORT} \
	${SLUGOS_KERNEL} \
	${SLUGOS_EXTRA_RDEPENDS}"

IPKG_INSTALL = "${RDEPENDS}"

inherit image_ipk

python () {
    # Don't build slugos images unless the configuration is set up
    # for an image build!
    if bb.data.getVar("SLUGOS_IMAGENAME", d, 1) == '' or bb.data.getVar("SLUGOS_IMAGESEX", d, 1) == '':
        raise bb.parse.SkipPackage("absent or broken SlugOS configuration")
}

#--------------------------------------------------------------------------------
# NSLU2 specific
#
#NOTE: you do not actually need the boot loader in normal use because it is
# *not* overwritten by a standard upslug upgrade, so you can make an image with
# just non-LinkSys software which can be flashed into the NSLU2.  Because
# LinkSys have made "EraseAll" available, however, (this does overwrite RedBoot)
# it is a bad idea to produce flash images without a valid RedBoot - that allows
# an innocent user upgrade attempt to instantly brick the NSLU2.
PACK_IMAGE += "${@['', 'slugos_pack_image;'][bb.data.getVar('SLUGOS_FLASH_IMAGE', d, 1) == '1']}"
PACK_IMAGE_DEPENDS += "${@['', 'slugimage-native nslu2-linksys-firmware apex ixp4xx-npe'][bb.data.getVar('SLUGOS_FLASH_IMAGE', d, 1) == '1']}"

NSLU2_SLUGIMAGE_ARGS ?= ""

slugos_pack_image() {
	install -d ${DEPLOY_DIR_IMAGE}/slug
	install -m 0644 ${STAGING_LIBDIR}/nslu2-binaries/RedBoot \
			${STAGING_LIBDIR}/nslu2-binaries/Trailer \
			${STAGING_LIBDIR}/nslu2-binaries/SysConf \
			${DEPLOY_DIR_IMAGE}/slug/
	install -m 0644 ${STAGING_LOADER_DIR}/apex.bin ${DEPLOY_DIR_IMAGE}/slug/
	install -m 0644 ${DEPLOY_DIR_IMAGE}/zImage-nslu2${ARCH_BYTE_SEX} \
		${DEPLOY_DIR_IMAGE}/slug/vmlinuz
	install -m 0644 ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.jffs2 \
		${DEPLOY_DIR_IMAGE}/slug/flashdisk.jffs2
	install -m 0644 ${STAGING_FIRMWARE_DIR}/NPE-B ${DEPLOY_DIR_IMAGE}/slug/
	cd ${DEPLOY_DIR_IMAGE}/slug
	slugimage -p -b RedBoot -s SysConf -L apex.bin -k vmlinuz \
		-r Flashdisk:flashdisk.jffs2 -m NPE-B -t Trailer \
		-o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}-nslu2.bin \
		${NSLU2_SLUGIMAGE_ARGS}
	rm -rf ${DEPLOY_DIR_IMAGE}/slug

	# Create an image for the DSM-G600 as well
	install -d ${DEPLOY_DIR_IMAGE}/firmupgrade
	install -m 0755 ${DEPLOY_DIR_IMAGE}/zImage-dsmg600${ARCH_BYTE_SEX} \
		${DEPLOY_DIR_IMAGE}/firmupgrade/ip-ramdisk
	install -m 0644 ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.jffs2 \
		${DEPLOY_DIR_IMAGE}/firmupgrade/rootfs.gz
	touch ${DEPLOY_DIR_IMAGE}/firmupgrade/usr.cramfs
	chmod 0644 ${DEPLOY_DIR_IMAGE}/firmupgrade/usr.cramfs
	echo "hwid=1.0.1"      >${DEPLOY_DIR_IMAGE}/firmupgrade/version.msg
	echo "model=dsm-g600" >>${DEPLOY_DIR_IMAGE}/firmupgrade/version.msg
	echo "vendor=dlink"   >>${DEPLOY_DIR_IMAGE}/firmupgrade/version.msg
	echo ""               >>${DEPLOY_DIR_IMAGE}/firmupgrade/version.msg
	chmod 0744 ${DEPLOY_DIR_IMAGE}/firmupgrade/version.msg
	tar -c -f ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}-dsmg600.bin \
		-C ${DEPLOY_DIR_IMAGE} firmupgrade
	rm -rf ${DEPLOY_DIR_IMAGE}/firmupgrade

	# Create an image for the NAS 100d as well
	install -d ${DEPLOY_DIR_IMAGE}/firmupgrade
	install -m 0755 ${DEPLOY_DIR_IMAGE}/zImage-nas100d${ARCH_BYTE_SEX} \
		${DEPLOY_DIR_IMAGE}/firmupgrade/ip-ramdisk
	install -m 0644 ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.jffs2 \
		${DEPLOY_DIR_IMAGE}/firmupgrade/rootfs.gz
	touch ${DEPLOY_DIR_IMAGE}/firmupgrade/usr.cramfs
	chmod 0644 ${DEPLOY_DIR_IMAGE}/firmupgrade/usr.cramfs
	echo "hwid=1.0.1"      >${DEPLOY_DIR_IMAGE}/firmupgrade/version.msg
	echo "model=koala"    >>${DEPLOY_DIR_IMAGE}/firmupgrade/version.msg
	echo "vendor=iomega"  >>${DEPLOY_DIR_IMAGE}/firmupgrade/version.msg
	echo ""               >>${DEPLOY_DIR_IMAGE}/firmupgrade/version.msg
	chmod 0744 ${DEPLOY_DIR_IMAGE}/firmupgrade/version.msg
	tar -c -f ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}-nas100d.bin \
		-C ${DEPLOY_DIR_IMAGE} firmupgrade
	rm -rf ${DEPLOY_DIR_IMAGE}/firmupgrade
}

# upslug2 (in tmp/work/upslug2-native-*) is the program to write the NSLU2 flash
IMAGE_TOOLS_nslu2 = "upslug2-native"
