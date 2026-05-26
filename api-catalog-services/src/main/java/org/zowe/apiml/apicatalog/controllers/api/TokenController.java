/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.apicatalog.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import org.zowe.apiml.security.client.service.GatewaySecurity;
import org.zowe.apiml.security.common.config.AuthConfigurationProperties;
import org.zowe.apiml.security.common.login.LoginFilter;
import org.zowe.apiml.security.common.login.LoginRequest;
import org.zowe.apiml.security.common.token.QueryResponse;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.hc.core5.http.HttpStatus.SC_NO_CONTENT;

@RestController
@RequestMapping("/apicatalog/auth")
@ConditionalOnMissingBean(name = "modulithConfig")
@RequiredArgsConstructor
public class TokenController {

    private final ObjectMapper mapper;

    private final GatewaySecurity gatewaySecurity;

    private final AuthConfigurationProperties authConfigurationProperties;

    private AuthConfigurationProperties.CookieProperties cp;

    private int cookieMaxAge = -1;

    @PostConstruct
    void initConstants() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @PostMapping(value = "/login")
    public Mono<Object> login(@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String requestHeader, ServerWebExchange exchange) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @GetMapping("/query")
    public Mono<QueryResponse> login(ServerWebExchange exchange) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
