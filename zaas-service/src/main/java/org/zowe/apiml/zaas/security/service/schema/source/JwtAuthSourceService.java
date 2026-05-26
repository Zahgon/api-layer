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

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.zowe.apiml.zaas.security.service.AuthenticationService;
import org.zowe.apiml.zaas.security.service.schema.source.AuthSource.Origin;
import org.zowe.apiml.message.core.MessageType;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.product.logging.annotations.InjectApimlLogger;
import org.zowe.apiml.security.common.token.QueryResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.function.Function;

/**
 * Implementation of AuthSourceService which supports JWT token as authentication source.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequiredArgsConstructor
public class JwtAuthSourceService extends TokenAuthSourceService {

    @InjectApimlLogger
    protected final ApimlLogger logger = ApimlLogger.empty();

    private final AuthenticationService authenticationService;

    @Override
    protected ApimlLogger getLogger() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Function<String, AuthSource> getMapper() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Optional<String> getToken(HttpServletRequest request) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validates authentication source (JWT token) using method of {@link AuthenticationService}
     *
     * @param authSource {@link AuthSource} object which hold original source of authentication (JWT token)
     * @return true if token is valid, false otherwise
     */
    public boolean isValid(AuthSource authSource) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validates authentication source (JWT token) using method of {@link AuthenticationService}
     *
     * @param authSource {@link AuthSource} object which hold original source of authentication (JWT token)
     * @return authentication source in parsed form
     */
    public AuthSource.Parsed parse(AuthSource authSource) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Generates LTPA token from current source of authentication (JWT token) using method of {@link AuthenticationService}
     *
     * @param authSource {@link AuthSource} object which hold original source of authentication (JWT token)
     * @return LTPA token
     */
    public String getLtpaToken(AuthSource authSource) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getJWT(AuthSource authSource) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
