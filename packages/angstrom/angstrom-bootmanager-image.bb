#Angstrom bootmanager image
LICENSE = "MIT"
PR = "r0"

DEPENDS = "task-angstrom"
RDEPENDS = "angstrom-base-depends angstrom-bootmanager"

export IMAGE_BASENAME = "bootmanager-image"
export IMAGE_LINGUAS = ""
export PACKAGE_INSTALL = "${RDEPENDS}"

inherit image



