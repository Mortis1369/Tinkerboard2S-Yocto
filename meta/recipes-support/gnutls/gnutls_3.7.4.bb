SUMMARY = "GNU Transport Layer Security Library"
DESCRIPTION = "a secure communications library implementing the SSL, \
TLS and DTLS protocols and technologies around them."
HOMEPAGE = "https://gnutls.org/"
BUGTRACKER = "https://savannah.gnu.org/support/?group=gnutls"

LICENSE = "GPL-3.0-or-later & LGPL-2.1-or-later"
LICENSE:${PN} = "LGPL-2.1-or-later"
LICENSE:${PN}-xx = "LGPL-2.1-or-later"
LICENSE:${PN}-bin = "GPL-3.0-or-later"
LICENSE:${PN}-openssl = "GPL-3.0-or-later"

LIC_FILES_CHKSUM = "file://LICENSE;md5=71391c8e0c1cfe68077e7fce3b586283 \
                    file://doc/COPYING;md5=c678957b0c8e964aa6c70fd77641a71e \
                    file://doc/COPYING.LESSER;md5=a6f89e2100d9b6cdffcea4f398e37343"

DEPENDS = "nettle gmp virtual/libiconv libunistring"
DEPENDS:append:libc-musl = " argp-standalone"

SHRT_VER = "${@d.getVar('PV').split('.')[0]}.${@d.getVar('PV').split('.')[1]}"

SRC_URI = "https://www.gnupg.org/ftp/gcrypt/gnutls/v${SHRT_VER}/gnutls-${PV}.tar.xz \
           file://arm_eabi.patch \
           file://CVE-2022-2509.patch \
           file://CVE-2023-0361.patch \
           file://CVE-2023-5981.patch \
           file://CVE-2024-0553.patch \
           file://CVE-2024-0567.patch \
           file://CVE-2024-28834.patch \
           file://CVE-2024-28835.patch \
           file://CVE-2024-12243.patch \
           "

SRC_URI[sha256sum] = "e6adbebcfbc95867de01060d93c789938cf89cc1d1f6ef9ef661890f6217451f"

inherit autotools texinfo pkgconfig gettext lib_package gtk-doc

PACKAGECONFIG ??= "libidn  ${@bb.utils.filter('DISTRO_FEATURES', 'seccomp', d)}"

# You must also have CONFIG_SECCOMP enabled in the kernel for
# seccomp to work.
PACKAGECONFIG[seccomp] = "--with-libseccomp-prefix=${STAGING_EXECPREFIXDIR},ac_cv_libseccomp=no,libseccomp"
PACKAGECONFIG[libidn] = "--with-idn,--without-idn,libidn2"
PACKAGECONFIG[libtasn1] = "--with-included-libtasn1=no,--with-included-libtasn1,libtasn1"
PACKAGECONFIG[p11-kit] = "--with-p11-kit,--without-p11-kit,p11-kit"
PACKAGECONFIG[tpm] = "--with-tpm,--without-tpm,trousers"

EXTRA_OECONF = " \
    --enable-doc \
    --disable-libdane \
    --disable-guile \
    --disable-rpath \
    --enable-openssl-compatibility \
    --with-libpthread-prefix=${STAGING_DIR_HOST}${prefix} \
    --with-librt-prefix=${STAGING_DIR_HOST}${prefix} \
    --with-default-trust-store-file=${sysconfdir}/ssl/certs/ca-certificates.crt \
"

# Otherwise the tools try and use HOSTTOOLS_DIR/bash as a shell.
export POSIX_SHELL="${base_bindir}/sh"

LDFLAGS:append:libc-musl = " -largp"

do_configure:prepend() {
	for dir in . lib; do
		rm -f ${dir}/aclocal.m4 ${dir}/m4/libtool.m4 ${dir}/m4/lt*.m4
	done
}

PACKAGES =+ "${PN}-openssl ${PN}-xx"

FILES:${PN}-dev += "${bindir}/gnutls-cli-debug"
FILES:${PN}-openssl = "${libdir}/libgnutls-openssl.so.*"
FILES:${PN}-xx = "${libdir}/libgnutlsxx.so.*"

BBCLASSEXTEND = "native nativesdk"
