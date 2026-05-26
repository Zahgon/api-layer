/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.security.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.zowe.apiml.product.gateway.GatewayClient;
import org.zowe.apiml.product.instance.ServiceAddress;
import org.zowe.apiml.security.client.handler.RestResponseHandler;
import org.zowe.apiml.security.common.config.AuthConfigurationProperties;
import org.zowe.apiml.security.common.error.ErrorType;
import org.zowe.apiml.security.common.login.LoginRequest;
import org.zowe.apiml.security.common.token.QueryResponse;
import org.zowe.apiml.security.common.token.TokenAuthentication;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Core class of security client
 * provides facility for performing login and validating JWT
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GatewaySecurityService implements GatewaySecurity {

    private static final String MESSAGE_KEY_STRING = "messageKey\":\"";

    private final GatewayClient gatewayClient;

    private final AuthConfigurationProperties authConfigurationProperties;

    private final CloseableHttpClient closeableHttpClient;

    private final RestResponseHandler responseHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Optional<String> login(String username, char[] password, char[] newPassword) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public QueryResponse query(String token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public TokenAuthentication verifyOidc(String token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private ErrorType getErrorType(String detailMessage) {
        if (detailMessage == null) {
            return ErrorType.AUTH_GENERAL;
        }
        int indexOfMessageKey = detailMessage.indexOf(MESSAGE_KEY_STRING);
        if (indexOfMessageKey < 0) {
            return ErrorType.AUTH_GENERAL;
        }
        // substring from `messageKey":"` to next `"` - this is the messageKey value
        String messageKeyToEndOfExceptionMessage = detailMessage.substring(indexOfMessageKey + MESSAGE_KEY_STRING.length());
        String messageKey = messageKeyToEndOfExceptionMessage.substring(0, messageKeyToEndOfExceptionMessage.indexOf("\""));
        try {
            return ErrorType.fromMessageKey(messageKey);
        } catch (IllegalArgumentException e) {
            return ErrorType.AUTH_GENERAL;
        }
    }

    private Optional<String> extractToken(String cookies) {
        String cookieName = authConfigurationProperties.getCookieProperties().getCookieName();
        if (cookies == null || cookies.isEmpty() || !cookies.contains(cookieName)) {
            return Optional.empty();
        } else {
            int end = cookies.indexOf(';');
            String cookie = (end > 0) ? cookies.substring(0, end) : cookies;
            return Optional.of(cookie.replace(cookieName + "=", ""));
        }
    }

    @Data
    @AllArgsConstructor
    static class TokenRequest {

        String token;
    }
}
