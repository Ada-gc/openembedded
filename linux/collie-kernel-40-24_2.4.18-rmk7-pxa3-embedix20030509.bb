SECTION = "kernel"
COLLIE_MEMORY_SIZE=40
COLLIE_RAMDISK_SIZE=24

include ../linux/openzaurus-sa_2.4.18-rmk7-pxa3-embedix20030509.bb

do_deploy_append() {
	! test -d ${DEPLOY_DIR}/images/collie/kernel && mkdir -p ${DEPLOY_DIR}/images/collie/kernel
	mv ${DEPLOY_DIR}/images/${KERNEL_IMAGETYPE}-${DATETIME} ${DEPLOY_DIR}/images/collie/kernel/${KERNEL_IMAGETYPE}_collie-${COLLIE_MEMORY_SIZE}-${COLLIE_RAMDISK_SIZE}
}
