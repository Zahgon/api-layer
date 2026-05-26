/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.filters.security;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import org.zowe.apiml.gateway.service.TokenProvider;
import org.zowe.apiml.product.opentelemetry.OtelRequestContext;
import org.zowe.apiml.security.common.config.AuthConfigurationProperties;
import org.zowe.apiml.security.common.token.TokenAuthentication;
import reactor.core.publisher.Mono;
import java.net.ConnectException;
import static org.apache.http.HttpStatus.SC_SERVICE_UNAVAILABLE;
import static org.zowe.apiml.security.common.token.TokenAuthentication.createAuthenticated;

@RequiredArgsConstructor
public class TokenAuthFilter extends AbstractTokenAuthFilter {

    private final TokenProvider tokenProvider;

    private final AuthConfigurationProperties authConfigurationProperties;

    private final AuthExceptionHandlerReactive authExceptionHandlerReactive;

    @Override
    protected AuthConfigurationProperties getAuthConfigurationProperties() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean isServiceUnavailable(Throwable ex) {
        return ex instanceof ConnectException || ex instanceof WebClientRequestException || (ex instanceof WebClientResponseException webEx && webEx.getStatusCode().value() == SC_SERVICE_UNAVAILABLE);
    }
}
