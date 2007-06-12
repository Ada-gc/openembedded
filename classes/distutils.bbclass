inherit distutils-base

distutils_do_compile() {
	BUILD_SYS=${BUILD_SYS} HOST_SYS=${HOST_SYS} \
	${STAGING_BINDIR_NATIVE}/python setup.py build || \
	oefatal "python setup.py build execution failed."
}

distutils_do_install() {
	BUILD_SYS=${BUILD_SYS} HOST_SYS=${HOST_SYS} \
	${STAGING_BINDIR_NATIVE}/python setup.py install --prefix=${D}/${prefix} --install-data=${D}/${datadir} || \
	oefatal "python setup.py install execution failed."

        for i in `find ${D} -name "*.py"` ; do \
            sed -i -e s:${D}::g $i
        done

        if test -e ${D}${bindir} ; then	
            for i in ${D}${bindir}/* ; do \
                sed -i -e s:${STAGING_BINDIR_NATIVE}:${bindir}:g $i
            done
        fi

        if test -e ${D}${sbindir} ; then
            for i in ${D}${sbindir}/* ; do \
                sed -i -e s:${STAGING_BINDIR_NATIVE}:${bindir}:g $i
            done
        fi

}

EXPORT_FUNCTIONS do_compile do_install
