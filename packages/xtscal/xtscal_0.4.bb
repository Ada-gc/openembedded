DESCRIPTION = "Touchscreen calibration utility"
MAINTAINER = "Phil Blundell <pb@handhelds.org>"
SECTION = "x11/base"
LICENSE = "GPL"
DEPENDS = "virtual/x11 libxft libxrandr"

SRC_URI = "${GPE_MIRROR}/xtscal-${PV}.tar.bz2"

inherit autotools
