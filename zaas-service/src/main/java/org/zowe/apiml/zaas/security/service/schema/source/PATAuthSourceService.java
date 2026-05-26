/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zaas.security.service.schema.source;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zowe.apiml.message.core.MessageType;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.product.logging.annotations.InjectApimlLogger;
import org.zowe.apiml.security.common.token.AccessTokenProvider;
import org.zowe.apiml.security.common.token.QueryResponse;
import org.zowe.apiml.zaas.security.service.AuthenticationService;
import org.zowe.apiml.zaas.security.service.TokenCreationService;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
@Service
public class PATAuthSourceService extends TokenAuthSourceService {

    public static final String SERVICE_ID_HEADER = "X-Service-Id";

    @InjectApimlLogger
    protected final ApimlLogger logger = ApimlLogger.empty();

    private final AuthenticationService authenticationService;

    private final AccessTokenProvider tokenProvider;

    private final TokenCreationService tokenService;

    @Override
    protected ApimlLogger getLogger() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Function<String, AuthSource> getMapper() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Optional<AuthSource> getAuthSourceFromRequest(HttpServletRequest request) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Optional<String> getToken(HttpServletRequest request) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isValid(AuthSource authSource) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public AuthSource.Parsed parse(AuthSource authSource) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getLtpaToken(AuthSource authSource) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getJWT(AuthSource authSource) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
