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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.zowe.apiml.security.common.token.QueryResponse;
import reactor.core.publisher.Mono;

@Component
public class TokenProvider extends AbstractAuthProviderFilter<QueryResponse> {

    public TokenProvider(@Qualifier("webClientClientCert") WebClient webClient, InstanceInfoService instanceInfoService) {
        super(webClient, instanceInfoService);
    }

    public String getEndpointPath() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected Mono<QueryResponse> processResponse(WebClient.RequestHeadersSpec<?> rhs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected WebClient.RequestHeadersSpec<?> createRequest(ServiceInstance instance, String token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Mono<QueryResponse> validateToken(String token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
