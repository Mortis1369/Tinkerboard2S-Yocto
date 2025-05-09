SUMMARY = "Command line tool and library for client-side URL transfers"
DESCRIPTION = "It uses URL syntax to transfer data to and from servers. \
curl is a widely used because of its ability to be flexible and complete \
complex tasks. For example, you can use curl for things like user authentication, \
HTTP post, SSL connections, proxy support, FTP uploads, and more!"
HOMEPAGE = "https://curl.se/"
BUGTRACKER = "https://github.com/curl/curl/issues"
SECTION = "console/network"
LICENSE = "curl"
LIC_FILES_CHKSUM = "file://COPYING;md5=190c514872597083303371684954f238"

SRC_URI = "https://curl.se/download/${BP}.tar.xz \
           file://CVE-2022-22576.patch \
           file://CVE-2022-27775.patch \
           file://CVE-2022-27776.patch \
           file://CVE-2022-27774-1.patch \
           file://CVE-2022-27774-2.patch \
           file://CVE-2022-27774-3.patch \
           file://CVE-2022-27774-4.patch \
           file://CVE-2022-30115.patch \
           file://CVE-2022-27780.patch \
           file://CVE-2022-27781.patch \
           file://CVE-2022-27779.patch \
           file://CVE-2022-27782-1.patch \
           file://CVE-2022-27782-2.patch \
           file://0001-openssl-fix-CN-check-error-code.patch \
           file://CVE-2022-32205.patch \
           file://CVE-2022-32206.patch \
           file://CVE-2022-32207.patch \
           file://CVE-2022-32208.patch \
           file://CVE-2022-35252.patch \
           file://CVE-2022-32221.patch \
           file://CVE-2022-42916.patch \
           file://CVE-2022-42915.patch \
           file://CVE-2022-43551.patch \
           file://CVE-2022-43552.patch \
           file://CVE-2023-23914_5-1.patch \
           file://CVE-2023-23914_5-2.patch \
           file://CVE-2023-23914_5-3.patch \
           file://CVE-2023-23914_5-4.patch \
           file://CVE-2023-23914_5-5.patch \
           file://CVE-2023-23916.patch \
           file://CVE-2023-27533.patch \
           file://CVE-2023-27534.patch \
           file://CVE-2023-27535-pre1.patch \
           file://CVE-2023-27535_and_CVE-2023-27538.patch \
           file://CVE-2023-27536.patch \
           file://CVE-2023-28319.patch \
           file://CVE-2023-28320.patch \
           file://CVE-2023-28320-fol1.patch \
           file://CVE-2023-28321.patch \
           file://CVE-2023-28322-1.patch \
           file://CVE-2023-28322-2.patch \
           file://CVE-2023-38545.patch \
           file://CVE-2023-38546.patch \
           file://CVE-2023-46218.patch \
           file://CVE-2023-46219-0001.patch \
           file://CVE-2023-46219-0002.patch \
           file://CVE-2023-46219-0003.patch \
           file://CVE-2024-2398.patch \
           file://CVE-2024-7264_1.patch \
           file://CVE-2024-7264_2.patch \
           file://CVE-2024-8096.patch \
           file://0001-url-free-old-conn-better-on-reuse.patch \
           file://CVE-2024-9681.patch \
           "
SRC_URI[sha256sum] = "0aaa12d7bd04b0966254f2703ce80dd5c38dbbd76af0297d3d690cdce58a583c"

# Curl has used many names over the years...
CVE_PRODUCT = "haxx:curl haxx:libcurl curl:curl curl:libcurl libcurl:libcurl daniel_stenberg:curl"

# This CVE reports that apple had to upgrade curl because of other already reported CVEs
CVE_CHECK_IGNORE += "CVE-2023-42915"
# ignored: CURLOPT_SSL_VERIFYPEER was disabled on google cloud services causing a potential man in the middle attack
CVE_CHECK_IGNORE += "CVE-2024-32928"
# ignored: gzip decompression of content-encoded HTTP responses with the `CURLOPT_ACCEPT_ENCODING` option, using zlib 1.2.0.3 or older
CVE_CHECK_IGNORE += "CVE-2025-0725"

inherit autotools pkgconfig binconfig multilib_header

# Entropy source for random PACKAGECONFIG option
RANDOM ?= "/dev/urandom"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'ipv6', d)} libidn openssl proxy random threaded-resolver verbose zlib"
PACKAGECONFIG:class-native = "ipv6 openssl proxy random threaded-resolver verbose zlib"
PACKAGECONFIG:class-nativesdk = "ipv6 openssl proxy random threaded-resolver verbose zlib"

# 'ares' and 'threaded-resolver' are mutually exclusive
PACKAGECONFIG[ares] = "--enable-ares,--disable-ares,c-ares,,,threaded-resolver"
PACKAGECONFIG[brotli] = "--with-brotli,--without-brotli,brotli"
PACKAGECONFIG[builtinmanual] = "--enable-manual,--disable-manual"
# Don't use this in production
PACKAGECONFIG[debug] = "--enable-debug,--disable-debug"
PACKAGECONFIG[dict] = "--enable-dict,--disable-dict,"
PACKAGECONFIG[gnutls] = "--with-gnutls,--without-gnutls,gnutls"
PACKAGECONFIG[gopher] = "--enable-gopher,--disable-gopher,"
PACKAGECONFIG[imap] = "--enable-imap,--disable-imap,"
PACKAGECONFIG[ipv6] = "--enable-ipv6,--disable-ipv6,"
PACKAGECONFIG[krb5] = "--with-gssapi,--without-gssapi,krb5"
PACKAGECONFIG[ldap] = "--enable-ldap,--disable-ldap,openldap"
PACKAGECONFIG[ldaps] = "--enable-ldaps,--disable-ldaps,openldap"
PACKAGECONFIG[libgsasl] = "--with-libgsasl,--without-libgsasl,libgsasl"
PACKAGECONFIG[libidn] = "--with-libidn2,--without-libidn2,libidn2"
PACKAGECONFIG[libssh2] = "--with-libssh2,--without-libssh2,libssh2"
PACKAGECONFIG[mbedtls] = "--with-mbedtls=${STAGING_DIR_TARGET},--without-mbedtls,mbedtls"
PACKAGECONFIG[mqtt] = "--enable-mqtt,--disable-mqtt,"
PACKAGECONFIG[nghttp2] = "--with-nghttp2,--without-nghttp2,nghttp2"
PACKAGECONFIG[openssl] = "--with-openssl,--without-openssl,openssl"
PACKAGECONFIG[pop3] = "--enable-pop3,--disable-pop3,"
PACKAGECONFIG[proxy] = "--enable-proxy,--disable-proxy,"
PACKAGECONFIG[random] = "--with-random=${RANDOM},--without-random"
PACKAGECONFIG[rtmpdump] = "--with-librtmp,--without-librtmp,rtmpdump"
PACKAGECONFIG[rtsp] = "--enable-rtsp,--disable-rtsp,"
PACKAGECONFIG[smb] = "--enable-smb,--disable-smb,"
PACKAGECONFIG[smtp] = "--enable-smtp,--disable-smtp,"
PACKAGECONFIG[nss] = "--with-nss,--without-nss,nss"
PACKAGECONFIG[telnet] = "--enable-telnet,--disable-telnet,"
PACKAGECONFIG[tftp] = "--enable-tftp,--disable-tftp,"
PACKAGECONFIG[threaded-resolver] = "--enable-threaded-resolver,--disable-threaded-resolver,,,,ares"
PACKAGECONFIG[verbose] = "--enable-verbose,--disable-verbose"
PACKAGECONFIG[zlib] = "--with-zlib=${STAGING_LIBDIR}/../,--without-zlib,zlib"
PACKAGECONFIG[zstd] = "--with-zstd,--without-zstd,zstd"

EXTRA_OECONF = " \
    --disable-libcurl-option \
    --disable-ntlm-wb \
    --enable-crypto-auth \
    --with-ca-bundle=${sysconfdir}/ssl/certs/ca-certificates.crt \
    --without-libpsl \
    --enable-optimize \
    ${@'--without-ssl' if (bb.utils.filter('PACKAGECONFIG', 'gnutls mbedtls nss openssl', d) == '') else ''} \
"

do_install:append:class-target() {
	# cleanup buildpaths from curl-config
	sed -i \
	    -e 's,--sysroot=${STAGING_DIR_TARGET},,g' \
	    -e 's,--with-libtool-sysroot=${STAGING_DIR_TARGET},,g' \
	    -e 's|${DEBUG_PREFIX_MAP}||g' \
	    -e 's|${@" ".join(d.getVar("DEBUG_PREFIX_MAP").split())}||g' \
	    ${D}${bindir}/curl-config
}

PACKAGES =+ "lib${BPN}"

FILES:lib${BPN} = "${libdir}/lib*.so.*"
RRECOMMENDS:lib${BPN} += "ca-certificates"

FILES:${PN} += "${datadir}/zsh"

inherit multilib_script
MULTILIB_SCRIPTS = "${PN}-dev:${bindir}/curl-config"

BBCLASSEXTEND = "native nativesdk"
