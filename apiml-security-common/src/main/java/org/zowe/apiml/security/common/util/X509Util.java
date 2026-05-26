/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.security.common.util;

import lombok.Value;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.SslInfo;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.x509.SubjectDnX509PrincipalExtractor;
import org.springframework.security.web.authentication.preauth.x509.X509PrincipalExtractor;
import org.zowe.apiml.security.common.token.X509AuthenticationToken;
import reactor.core.publisher.Mono;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Collections;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;

@Slf4j
@UtilityClass
public class X509Util {

    public String getEncodedClientCertificate(SslInfo sslInfo) throws CertificateEncodingException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public X509PrincipalExtractor x509PrincipalExtractor() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ReactiveAuthenticationManager x509ReactiveAuthenticationManager() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Value
    public static class X509Principal {

        private final X509Certificate x509Certificate;

        private final String username;
    }
}
