DESCRIPTION = "An X11-based distribution with the Enlightenment Window Manager"

DEPENDS = "${MACHINE_TASK_PROVIDER} \
           xserver-kdrive \
           task-e-x11-core"

PREFERRED_PROVIDER_virtual/xserver = "xserver-kdrive"
PREFERRED_PROVIDER_virtual/evas = "evas-x11"
PREFERRED_PROVIDER_virtual/ecore = "ecore-x11"
PREFERRED_PROVIDER_virtual/imlib2 = "imlib2-x11"
PREFERRED_PROVIDER_virtual/libxine = "libxine-x11"
PREFERRED_PROVIDER_libx11 = "libx11"

IMAGE_INSTALL = "${MACHINE_TASK_PROVIDER} task-e-x11-core xserver-kdrive-fbdev glibc-charmap-utf-8 glibc-localedata-i18n"
IMAGE_LINGUAS = ""

inherit image
