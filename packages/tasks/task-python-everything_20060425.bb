DESCRIPTION= "Everything Python"
HOMEPAGE = "http://www.vanille.de/projects/python.spy"
LICENSE = "MIT"
PR = "ml11"

BROKEN_BECAUSE_GCC4 = "\
		python-egenix-mx-base"

RDEPENDS = "\
		python-ao 		\
		python-pybluez		\
		python-constraint	\
		python-crypto		\
		python-dialog		\
		python-pycurl		\
		python-fnorb		\
		python-fpconst		\
		python-gammu		\
		python-gmpy		\
		python-gnosis		\
		python-hmm		\
		python-inotify		\
		python-irclib		\
		python-itools		\
		python-logilab		\
		python-libgmail		\
		python-lxml		\
		python-mad		\
		python-native		\
		python-numeric		\
		python-ogg		\
		python-pexpect		\
		python-pychecker	\
		python-pycodes		\
		python-pyephem		\
		python-pyfits		\
		python-pyflakes		\
		python-pygame		\
		python-pygoogle		\
		python-pygtk		\
		python-pygtk2		\
		python-pylinda		\
		python-pylint		\
		python-pyqt		\
		python-pyqwt		\
		python-pyraf		\
		python-pyreverse	\
		python-pyrex		\
		python-pyro		\
		python-pyserial		\
		python-pytest		\
		python-pyvisa		\
		python-pyweather	\
		python-pyxml		\
		python-pyxmlrpc		\
		python-quicklauncher    \
		python-scapy		\
		python-scons		\
		python-setuptools	\
		python-sip		\
		python-sgmlop		\
		python-snmplib		\
		python-soappy		\
		python-spydi		\
		python-spyro		\
		python-sword		\
		python-pysqlite		\
		python-pysqlite2	\
		python-tlslite		\
		python-urwid		\
		python-vmaps		\
		python-vorbis		\
		python-webpy		\
		moin			\
		plone			\
		twisted			\
		zope"


#fixme add python-pycap once libdnet is in again
#fixme add python-pyx once kpathwhich-native is there
#fixme add packages dynamically
#fixme python-numarray doesn't work with soft-float
LICENSE = "MIT"
