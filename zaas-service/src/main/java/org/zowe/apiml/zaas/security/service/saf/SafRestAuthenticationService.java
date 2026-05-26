/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zaas.security.service.saf;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.Collections;
import static org.springframework.util.StringUtils.hasLength;

/**
 * Authentication provider implementation for the SafIdt Tokens that gets and verifies the tokens across the Restfull
 * interface
 * <p>
 * To work properly the implementation requires two urls:
 * <p>
 * - apiml.security.saf.urls.authenticate - URL to generate token
 * - apiml.security.saf.urls.verify - URL to verify the validity of the token
 */
@RequiredArgsConstructor
@Slf4j
public class SafRestAuthenticationService implements SafIdtProvider {

    private final RestTemplate restTemplate;

    static final HttpHeaders HEADERS = new HttpHeaders();

    static {
        HEADERS.setContentType(MediaType.APPLICATION_JSON);
        HEADERS.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    }

    @Value("${apiml.security.saf.urls.authenticate}")
    String authenticationUrl;

    @Value("${apiml.security.saf.urls.verify}")
    String verifyUrl;

    @Override
    public String generate(String username, char[] password, String applId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean verify(String safToken, String applid) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Token {

        String jwt;

        String appl;
    }

    @lombok.Value
    @Builder
    public static class Authentication {

        String username;

        @JsonSerialize(using = StdArraySerializers.CharArraySerializer.class)
        char[] pass;

        String appl;
    }
}
