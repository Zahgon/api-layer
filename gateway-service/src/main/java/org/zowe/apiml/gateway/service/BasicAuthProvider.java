/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.zowe.apiml.security.common.config.AuthConfigurationProperties;
import reactor.core.publisher.Mono;
import java.net.HttpCookie;
import java.util.Collection;
import static org.apache.hc.core5.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.hc.core5.http.HttpStatus.SC_UNAUTHORIZED;

@Component
public class BasicAuthProvider extends AbstractAuthProviderFilter<ClientResponse.Headers> {

    private final AuthConfigurationProperties authConfigurationProperties;

    public BasicAuthProvider(@Qualifier("webClientClientCert") WebClient webClient, InstanceInfoService instanceInfoService, AuthConfigurationProperties authConfigurationProperties) {
        super(webClient, instanceInfoService);
        this.authConfigurationProperties = authConfigurationProperties;
    }

    public String getEndpointPath() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected Mono<ClientResponse.Headers> processResponse(WebClient.RequestHeadersSpec<?> rhs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected WebClient.RequestHeadersSpec<WebClient.RequestBodySpec> createRequest(ServiceInstance instance, String headerValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Mono<String> getToken(String authHeader) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
