/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.zowe.apiml.auth.AuthenticationScheme;
import org.zowe.apiml.constants.ApimlConstants;
import org.zowe.apiml.message.core.MessageService;
import org.zowe.apiml.product.opentelemetry.OtelRequestContext;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import static org.zowe.apiml.constants.ApimlConstants.HTTP_CLIENT_USE_CLIENT_CERTIFICATE;

@Service
@Slf4j
public class X509FilterFactory extends AbstractGatewayFilterFactory<X509FilterFactory.Config> {

    public static final String PUBLIC_KEY = "X-Certificate-Public";

    public static final String DISTINGUISHED_NAME = "X-Certificate-DistinguishedName";

    public static final String COMMON_NAME = "X-Certificate-CommonName";

    private final MessageService messageService;

    public X509FilterFactory(MessageService messageService) {
        super(Config.class);
        this.messageService = messageService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private ServerHttpRequest updateHeadersForError(ServerWebExchange exchange) {
        String headerValue = messageService.createMessage("org.zowe.apiml.gateway.security.schema.missingX509Authentication").mapToLogMessage();
        ServerHttpRequest request = exchange.getRequest().mutate().header(ApimlConstants.AUTH_FAIL_HEADER, headerValue).build();
        exchange.getResponse().getHeaders().add(ApimlConstants.AUTH_FAIL_HEADER, headerValue);
        return request;
    }

    public void setHeader(HttpHeaders headers, String[] headerNames, X509Certificate certificate) throws CertificateEncodingException, InvalidNameException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String getCommonName(LdapName ldapDN) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static class Config {

        private String headers;

        public String getHeaders() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void setHeaders(String headers) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
