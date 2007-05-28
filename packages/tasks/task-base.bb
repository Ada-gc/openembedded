DESCRIPTION = "Merge machine and distro options to create a basic machine task/package"
PR = "r32"

PACKAGES = 'task-boot \
            task-base \
            task-distro-base \
            task-machine-base \
            \
            task-base-acpi \
            task-base-alsa \
            task-base-apm \
            task-base-bluetooth \
            task-base-ext2 \
            task-base-irda \
            task-base-keyboard \
            task-base-pci \
            task-base-pcmcia \
            task-base-phone \
            task-base-screen \
            task-base-serial \
            task-base-touchscreen \
            task-base-usbgadget \
            task-base-usbhost \
            task-base-wifi \
            \
            task-base-cramfs \
            task-base-ipsec \
            task-base-ipv6 \
            task-base-nfs \
            task-base-ppp \
            task-base-smbfs \
	    \
            ${@base_contains("MACHINE_FEATURES","kernel26","task-base-kernel26","task-base-kernel24",d)} \
	    '

ALLOW_EMPTY = "1"

PACKAGE_ARCH = "all"

#
# packages which content depend on MACHINE_FEATURES need to be MACHINE_ARCH
#
PACKAGE_ARCH_task-base = "${MACHINE_ARCH}"
PACKAGE_ARCH_task-boot = "${MACHINE_ARCH}"
PACKAGE_ARCH_task-machine-base = "${MACHINE_ARCH}"
PACKAGE_ARCH_task-base-apm = "${MACHINE_ARCH}"
PACKAGE_ARCH_task-base-pcmcia = "${MACHINE_ARCH}"

#
# udev, devfsd, mdev (from busybox) or none
#
DISTRO_DEV_MANAGER ?= "${@base_contains("MACHINE_FEATURES", "kernel26",  "udev","",d)} "

#
# sysvinit, upstart
#
DISTRO_INIT_MANAGER ?= "sysvinit sysvinit-pidof"

#
# tinylogin, getty
#
DISTRO_LOGIN_MANAGER ?= "tinylogin"

#
# linux-hotplug or none
#
HOTPLUG ?= "${@base_contains("MACHINE_FEATURES", "kernel24",  "linux-hotplug","",d)} "

#
# dropbear, openssh or none
#
DISTRO_SSH_DAEMON ?= "dropbear"

#
# pcmciautils for >= 2.6.13-rc1, pcmcia-cs for others
#
PCMCIA_MANAGER ?= "${@base_contains('MACHINE_FEATURES', 'kernel26','pcmciautils','pcmcia-cs',d)} "

#
# those ones can be set in machine config to supply packages needed to get machine booting
#
MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS ?= ""

#
# task-base contain stuff needed for base system (machine related)
#
RDEPENDS_task-base = "\
    task-boot \
    task-distro-base \
    task-machine-base \
    ${DISTRO_SSH_DAEMON} \
    ${HOTPLUG} \
    \
    ${@base_contains('MACHINE_FEATURES', 'kernel26','task-base-kernel26','task-base-kernel24',d)} \
    ${@base_contains('MACHINE_FEATURES', 'apm', 'task-base-apm', '',d)} \
    ${@base_contains('MACHINE_FEATURES', 'acpi', 'task-base-acpi', '',d)} \
    ${@base_contains('MACHINE_FEATURES', 'keyboard', 'task-base-keyboard', '',d)} \
    \
    ${@base_contains('COMBINED_FEATURES', 'alsa', 'task-base-alsa', '',d)} \
    ${@base_contains('COMBINED_FEATURES', 'ext2', 'task-base-ext2', '',d)} \
    ${@base_contains('COMBINED_FEATURES', 'irda', 'task-base-irda', '',d)} \
    ${@base_contains('COMBINED_FEATURES', 'pci', 'task-base-pci', '',d)} \
    ${@base_contains('COMBINED_FEATURES', 'pcmcia', 'task-base-pcmcia', '',d)} \
    ${@base_contains('COMBINED_FEATURES', 'usbgadget', 'task-base-usbgadget', '',d)} \
    ${@base_contains('COMBINED_FEATURES', 'usbhost', 'task-base-usbhost', '',d)} \
    ${@base_contains('COMBINED_FEATURES', 'bluetooth', 'task-base-bluetooth', '',d)} \
    ${@base_contains('COMBINED_FEATURES', 'wifi', 'task-base-wifi', '',d)} \
    ${@base_contains('COMBINED_FEATURES', 'uboot', 'task-base-uboot', '',d)} \
    ${@base_contains('COMBINED_FEATURES', 'redboot', 'task-base-redboot', '',d)} \
    \
    ${@base_contains('DISTRO_FEATURES', 'nfs', 'task-base-nfs', '',d)} \
    ${@base_contains('DISTRO_FEATURES', 'ipv6', 'task-base-ipv6', '',d)} \
    ${@base_contains('DISTRO_FEATURES', 'ipsec', 'task-base-ipsec', '',d)} \
    ${@base_contains('DISTRO_FEATURES', 'ppp', 'task-base-ppp', '',d)} \
    ${@base_contains('DISTRO_FEATURES', 'raid', 'task-base-raid', '',d)} \
    "

RDEPENDS_task-base-extended = "\
    task-base \
    ${ADD_WIFI} \
    ${ADD_BT} \
    "

ADD_WIFI = ""
ADD_BT = ""

python __anonymous () {
    import bb
    # If Distro want wifi and machine feature wifi/pci/pcmcia/usbhost (one of them)
    # then include task-base-wifi in task-base

    distro_features = bb.data.getVar("DISTRO_FEATURES", d, 1)
    machine_features= bb.data.getVar("MACHINE_FEATURES", d, 1)

    if distro_features.find("bluetooth") and not machine_features.find("bluetooth") and (machine_features.find("pcmcia") or machine_features.find("pci")  or  machine_features.find("usbhost")):
	bb.data.setVar("ADD_BT", "task-base-bluetooth", d)

    if distro_features.find("wifi") and not machine_features.find("wifi") and (machine_features.find("pcmcia") or machine_features.find("pci")  or  machine_features.find("usbhost")):
	bb.data.setVar("ADD_WIFI", "task-base-wifi", d)
}

#
# packages added by distribution
#
RDEPENDS_task-distro-base = "${DISTRO_EXTRA_RDEPENDS}"
RRECOMMENDS_task-distro-base = "${DISTRO_EXTRA_RRECOMMENDS}"

#
# packages added by machine config
#
RDEPENDS_task-machine-base = "${MACHINE_EXTRA_RDEPENDS}"
RRECOMMENDS_task-machine-base = "${MACHINE_EXTRA_RRECOMMENDS}"

#
# minimal set of packages - needed to boot
#
RDEPENDS_task-boot = "\
    kernel \
    base-files \
    base-passwd \
    busybox \
    initscripts \
    modutils-initscripts \
    netbase \
    update-alternatives \
    ${DISTRO_DEV_MANAGER} \
    ${DISTRO_INIT_MANAGER} \
    ${DISTRO_LOGIN_MANAGER} \
    ${MACHINE_ESSENTIAL_EXTRA_RDEPENDS}"

RRECOMMENDS_task-boot = "\
    ${MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS}"

RDEPENDS_task-base-kernel24 = "\
    modutils-depmod"

RDEPENDS_task-base-kernel26 = "\
    sysfsutils \
    module-init-tools"

RRECOMMENDS_task-base-kernel24 = "\
    kernel-module-input \
    kernel-module-uinput"

RRECOMMENDS_task-base-kernel26 = "\
    kernel-module-input \
    kernel-module-uinput \
    kernel-module-rtc-dev \
    kernel-module-rtc-proc \
    kernel-module-rtc-sysfs \
    kernel-module-rtc-sa1100"

RDEPENDS_task-base-keyboard = "\
    keymaps"

RDEPENDS_task-base-pci = "\
    pciutils"

RDEPENDS_task-base-acpi = "\
    acpid"

RDEPENDS_task-base-apm = "\
    apm \
    apmd \
    ${@base_contains('MACHINE_FEATURES', 'kernel24', 'network-suspend-scripts', '',d)}"

RDEPENDS_task-base-ext2 = "\
    hdparm \
    e2fsprogs \
    e2fsprogs-e2fsck \
    e2fsprogs-mke2fs"

RDEPENDS_task-base-alsa = "\
    alsa-utils-alsactl \
    alsa-utils-alsamixer"

#
# alsa-state is machine related so can be missing in feed, OSS support is optional
#
RRECOMMENDS_task-base-alsa = "\
    alsa-state \
    kernel-module-snd-mixer-oss \
    kernel-module-snd-pcm-oss"

RDEPENDS_task-base-pcmcia = "\
    ${PCMCIA_MANAGER} \
    ${@base_contains('DISTRO_FEATURES', 'wifi', 'prism3-firmware', '',d)} \
    ${@base_contains('DISTRO_FEATURES', 'wifi', 'prism3-support', '',d)} \
    ${@base_contains('DISTRO_FEATURES', 'wifi', 'spectrum-fw', '',d)} \
    ${@base_contains('DISTRO_FEATURES', 'wifi', 'hostap-conf', '',d)} \
    "

RRECOMMENDS_task-base-pcmcia = "\
    ${@base_contains('MACHINE_FEATURES', 'kernel26', '${task-base-pcmcia26}', '${task-base-pcmcia24}',d)} \
    kernel-module-pcmcia \
    kernel-module-airo-cs \
    kernel-module-pcnet-cs \
    kernel-module-serial-cs \
    kernel-module-ide-cs \
    kernel-module-ide-disk \
    "

task-base-pcmcia24 = "\
    ${@base_contains('DISTRO_FEATURES', 'wifi', 'hostap-modules-cs', '',d)} \
    ${@base_contains('DISTRO_FEATURES', 'wifi', 'orinoco-modules-cs', '',d)} \
    "

task-base-pcmcia26 = "\
    ${@base_contains('DISTRO_FEATURES', 'wifi', 'kernel-module-hostap-cs', '',d)} \
    ${@base_contains('DISTRO_FEATURES', 'wifi', 'kernel-module-spectrum-cs', '',d)}"

RDEPENDS_task-base-bluetooth = "\ 
    blueprobe \
    bluez-utils"

RRECOMMENDS_task-base-bluetooth = "\
    kernel-module-bluetooth \
    kernel-module-l2cap \
    kernel-module-rfcomm \
    kernel-module-hci-vhci \
    kernel-module-bnep \
    kernel-module-hidp \
    kernel-module-hci-uart \
    kernel-module-sco \
    ${@base_contains('COMBINED_FEATURES', 'usbhost', 'kernel-module-hci-usb', '',d)} \
    ${@base_contains('COMBINED_FEATURES', 'pcmcia', 'kernel-module-bluetooth3c-cs', '',d)} \
    ${@base_contains('COMBINED_FEATURES', 'pcmcia', 'kernel-module-bluecard-cs', '',d)} \
    ${@base_contains('COMBINED_FEATURES', 'pcmcia', 'kernel-module-bluetoothuart-cs', '',d)} \
    ${@base_contains('COMBINED_FEATURES', 'pcmcia', 'kernel-module-dtl1-cs', '',d)}"

RDEPENDS_task-base-irda = "\
    irda-utils"

RRECOMMENDS_task-base-irda = "\
    kernel-module-pxaficp-ir \
    kernel-module-irda \
    kernel-module-ircomm \
    kernel-module-ircomm-tty \
    kernel-module-irlan \
    ${@base_contains('DISTRO_FEATURES', 'ppp', 'kernel-module-irnet', '',d)} \
    kernel-module-irport \
    kernel-module-irtty \
    kernel-module-irtty-sir \
    kernel-module-sir-dev \
    ${@base_contains('COMBINED_FEATURES', 'usbhost', 'kernel-module-ir-usb', '',d)} "

RRECOMMENDS_task-base-usbgadget = "\
    kernel-module-pxa27x_udc \
    kernel-module-gadgetfs \
    kernel-module-g-file-storage \
    kernel-module-g-serial \
    kernel-module-g-ether"

RDEPENDS_task-base-usbhost = "\
    usbutils "

RRECOMMENDS_task-base-usbhost = "\
    kernel-module-uhci-hcd \
    kernel-module-ohci-hcd \
    kernel-module-ehci-hcd \
    kernel-module-usbcore \
    kernel-module-usbhid \
    kernel-module-usbnet \
    kernel-module-sd-mod \
    kernel-module-scsi-mod \
    kernel-module-usbmouse \
    kernel-module-mousedev \
    kernel-module-usbserial \
    kernel-module-usb-storage "

RDEPENDS_task-base-uboot = "\
    uboot-utils"

RDEPENDS_task-base-redboot = "\
    fis"

RDEPENDS_task-base-ppp = "\
    ppp \
    ppp-dialin"

RRECOMMENDS_task-base-ppp = "\
    kernel-module-ppp-async \
    kernel-module-ppp-deflate \
    kernel-module-ppp-mppe"

RDEPENDS_task-base-ipsec = "\
    openswan"

RRECOMMENDS_task-base-ipsec = "\
    kernel-module-ipsec"

#
# task-base-wifi contain everything needed to get WiFi working
# WEP/WPA connection needs to be supported out-of-box
#
RDEPENDS_task-base-wifi = "\
    wireless-tools \
    ${@base_contains('COMBINED_FEATURES', 'pcmcia', 'hostap-utils', '',d)} \
    ${@base_contains('COMBINED_FEATURES', 'pci', 'hostap-utils', '',d)} \
    ${@base_contains('COMBINED_FEATURES', 'pci', 'madwifi-ng-tools', '',d)} \
    wpa-supplicant"

RRECOMMENDS_task-base-wifi = "\
    ${@base_contains('COMBINED_FEATURES', 'pci', 'madwifi-ng-modules', '',d)} \
    ${@base_contains('COMBINED_FEATURES', 'usbhost', 'kernel-module-zd1211rw', '',d)} \
    kernel-module-ieee80211-crypt \
    kernel-module-ieee80211-crypt-ccmp \
    kernel-module-ieee80211-crypt-tkip \
    kernel-module-ieee80211-crypt-wep \
    kernel-module-ecb \
    kernel-module-arc4 \
    kernel-module-michael-mic \
    kernel-module-aes"

RRECOMMENDS_task-base-smbfs = "\
    kernel-module-cifs \
    kernel-module-smbfs"

RRECOMMENDS_task-base-cramfs = "\
    kernel-module-cramfs"

#
# task-base-nfs provides ONLY client support - server is in nfs-utils package
#
RDEPENDS_task-base-nfs = "\
    portmap"

RRECOMMENDS_task-base-nfs = "\
    kernel-module-nfs "

RDEPENDS_task-base-raid = "\
	"

RDEPENDS_task-base-screen = "\
	"

#
# GPE/OPIE/OpenMoko provide own touchscreen calibration utils
#
RDEPENDS_task-base-touchscreen = "\
    tslib-tests \
    tslib-calibrate "

RDEPENDS_task-base-ipv6 = "\
    "

RRECOMMENDS_task-base-ipv6 = "\
    kernel-module-ipv6 "

RDEPENDS_task-base-serial = "\
    setserial \
    lrzsz "

# Tosort
# kernel-module-nvrd
# kernel-module-mip6-mn
# kernel-module-tun
# kernel-module-ide-probe-mo
# kernel-module-loop
# kernel-module-vfat
# kernel-module-ext2
# kernel-module-sco
# kernel-module-af_packet
# kernel-module-ip-gre
# kernel-module-ip-tables
# kernel-module-ipip
# kernel-module-des
# kernel-module-md5
# kernel-module-8250
# Should be DISTRO_EXTRA_RRECOMMENDS: lrzsz
