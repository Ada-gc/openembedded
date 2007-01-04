inherit rootfs_${IMAGE_PKGTYPE}

PACKAGES = ""

# We need to recursively follow RDEPENDS and RRECOMMENDS for images
BUILD_ALL_DEPS = "1"
do_rootfs[recrdeptask] = "do_package_write"

# Images are generally built explicitly, do not need to be part of world.
EXCLUDE_FROM_WORLD = "1"

USE_DEVFS ?= "0"

PID = "${@os.getpid()}"

DEPENDS += "makedevs-native"
PACKAGE_ARCH = "${MACHINE_ARCH}"

def get_image_deps(d):
	import bb
	str = ""
	for type in (bb.data.getVar('IMAGE_FSTYPES', d, 1) or "").split():
		deps = bb.data.getVar('IMAGE_DEPENDS_%s' % type, d) or ""
		if deps:
			str += " %s" % deps
	return str

DEPENDS += "${@get_image_deps(d)}"

#
# Get a list of files containing device tables to create.
# * IMAGE_DEVICE_TABLE is the old name to an absolute path to a device table file
# * IMAGE_DEVICE_TABLES is a new name for a file, or list of files, seached
#   for in the BBPATH
# If neither are specified then the default name of files/device_table-minimal.txt
# is searched for in the BBPATH (same as the old version.)
#
def get_devtable_list(d):
	import bb
	devtable = bb.data.getVar('IMAGE_DEVICE_TABLE', d, 1)
	if devtable != None:
		return devtable
	str = ""
	devtables = bb.data.getVar('IMAGE_DEVICE_TABLES', d, 1)
	if devtables == None:
		devtables = 'files/device_table-minimal.txt'
	for devtable in devtables.split():
		str += " %s" % bb.which(bb.data.getVar('BBPATH', d, 1), devtable)
	return str

IMAGE_POSTPROCESS_COMMAND ?= ""

# some default locales
IMAGE_LINGUAS ?= "de-de fr-fr en-gb"

LINGUAS_INSTALL = "${@" ".join(map(lambda s: "locale-base-%s" % s, bb.data.getVar('IMAGE_LINGUAS', d, 1).split()))}"

ROOTFS_POSTPROCESS_COMMAND ?= ""

do_rootfs[nostamp] = "1"
do_rootfs[dirs] = "${TOPDIR}"
do_build[nostamp] = "1"

# Must call real_do_rootfs() from inside here, rather than as a separate
# task, so that we have a single fakeroot context for the whole process.
fakeroot do_rootfs () {
	set -x
	rm -rf ${IMAGE_ROOTFS}

	if [ "${USE_DEVFS}" != "1" ]; then
		mkdir -p ${IMAGE_ROOTFS}/dev
		for devtable in ${@get_devtable_list(d)}; do
			makedevs -r ${IMAGE_ROOTFS} -D $devtable
		done
	fi

	rootfs_${IMAGE_PKGTYPE}_do_rootfs

	insert_feed_uris	

	rm -f ${IMAGE_ROOTFS}${libdir}/ipkg/lists/oe
	
	${IMAGE_PREPROCESS_COMMAND}
		
	export TOPDIR=${TOPDIR}
	export MACHINE=${MACHINE}

	for type in ${IMAGE_FSTYPES}; do
		if test -z "$FAKEROOTKEY"; then
			fakeroot -i ${TMPDIR}/fakedb.image bbimage -t $type -e ${FILE}
		else
			bbimage -n "${IMAGE_NAME}" -t "$type" -e "${FILE}"
		fi
	done

	${IMAGE_POSTPROCESS_COMMAND}
}

insert_feed_uris () {
	
	echo "Building feeds for [${DISTRO}].."
		
	for line in ${FEED_URIS}
	do
		# strip leading and trailing spaces/tabs, then split into name and uri
		line_clean="`echo "$line"|sed 's/^[ \t]*//;s/[ \t]*$//'`"
		feed_name="`echo "$line_clean" | sed -n 's/\(.*\)##\(.*\)/\1/p'`"
		feed_uri="`echo "$line_clean" | sed -n 's/\(.*\)##\(.*\)/\2/p'`"					
		
		echo "Added $feed_name feed with URL $feed_uri"
		
		# insert new feed-sources
		echo "src/gz $feed_name $feed_uri" >> ${IMAGE_ROOTFS}/etc/ipkg/${feed_name}-feed.conf
	done			
}

log_check() {
	set +x
	for target in $*
	do
		lf_path="${WORKDIR}/temp/log.do_$target.${PID}"
		
		echo "log_check: Using $lf_path as logfile"
		
		if test -e "$lf_path"
		then
			rootfs_${IMAGE_PKGTYPE}_log_check $target $lf_path
		else
			echo "Cannot find logfile [$lf_path]"
		fi
		echo "Logfile is clean"		
	done

	set -x
}

# set '*' as the rootpassword so the images
# can decide if they want it or not

zap_root_password () {
	sed 's%^root:[^:]*:%root:*:%' < ${IMAGE_ROOTFS}/etc/passwd >${IMAGE_ROOTFS}/etc/passwd.new
	mv ${IMAGE_ROOTFS}/etc/passwd.new ${IMAGE_ROOTFS}/etc/passwd	
} 

create_etc_timestamp() {
	date +%2m%2d%2H%2M%Y >${IMAGE_ROOTFS}/etc/timestamp
}

# Turn any symbolic /sbin/init link into a file
remove_init_link () {
	if [ -h ${IMAGE_ROOTFS}/sbin/init ]; then
		LINKFILE=${IMAGE_ROOTFS}`readlink ${IMAGE_ROOTFS}/sbin/init`
		rm ${IMAGE_ROOTFS}/sbin/init
		cp $LINKFILE ${IMAGE_ROOTFS}/sbin/init
	fi
}

make_zimage_symlink_relative () {
	if [ -L ${IMAGE_ROOTFS}/boot/zImage ]; then
		(cd ${IMAGE_ROOTFS}/boot/ && for i in `ls zImage-* | sort`; do ln -sf $i zImage; done)
	fi
}

# export the zap_root_password, create_etc_timestamp and remote_init_link
EXPORT_FUNCTIONS zap_root_password create_etc_timestamp remove_init_link do_rootfs make_zimage_symlink_relative

addtask rootfs before do_build after do_install
