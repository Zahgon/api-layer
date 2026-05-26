/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.zowe.apiml.security.common.config.AuthConfigurationProperties;
import reactor.core.publisher.Mono;
import static org.zowe.apiml.constants.ApimlConstants.BEARER_AUTHENTICATION_PREFIX;
import static org.zowe.apiml.security.SecurityUtils.COOKIE_AUTH_NAME;

@Component
@RequiredArgsConstructor
public class HttpUtils {

    private final AuthConfigurationProperties authConfigurationProperties;

    private AuthConfigurationProperties.CookieProperties cp;

    private int cookieMaxAge = -1;

    @PostConstruct
    protected void readConfig() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ResponseCookie createResponseCookie(String jwt) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ResponseCookie createResponseCookieRemoval() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Mono<String> getTokenFromRequest(ServerWebExchange exchange) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Mono<String> getBearerTokenFromHeaderReactive(ServerWebExchange exchange) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Mono<String> getCookieValue(ServerWebExchange exchange, String cookieName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
