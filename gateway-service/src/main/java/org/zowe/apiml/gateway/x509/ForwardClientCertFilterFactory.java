/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.x509;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.zowe.apiml.constants.ApimlConstants;
import org.zowe.apiml.security.common.util.X509Util;
import java.security.cert.CertificateEncodingException;
import static org.zowe.apiml.constants.ApimlConstants.HTTP_CLIENT_USE_CLIENT_CERTIFICATE;

/**
 * Objective is to include new header in the request which contains incoming client certificate
 * so that further processing (mapping to mainframe userId) is possible by the domain gateway.
 */
@Service
@Slf4j
public class ForwardClientCertFilterFactory extends AbstractGatewayFilterFactory<ForwardClientCertFilterFactory.Config> {

    public static final String CLIENT_CERT_HEADER = "Client-Cert";

    public ForwardClientCertFilterFactory() {
        super(Config.class);
    }

    /**
     * Filter business logic - Always remove any existing Client-Cert header from incoming request.
     * If feature is enabled, then extracts the client certificate, encode it and put it to new Client-Cert header.
     * If encoding fails then add X-Zowe-Auth-Failure header with the error message to the request.
     *
     * @param config Configuration values of this filter
     * @return GatewayFilter object
     */
    @Override
    public GatewayFilter apply(Config config) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("squid:S2094")
    public static class Config {
    }
}
