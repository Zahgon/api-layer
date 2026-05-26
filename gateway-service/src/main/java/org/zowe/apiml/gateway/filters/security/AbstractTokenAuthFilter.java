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

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.WebFilter;
import org.zowe.apiml.security.common.config.AuthConfigurationProperties;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class AbstractTokenAuthFilter implements WebFilter {

    protected static final String BEARER_PREFIX = "Bearer ";

    protected abstract AuthConfigurationProperties getAuthConfigurationProperties();

    protected Optional<String> resolveToken(ServerHttpRequest request) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
