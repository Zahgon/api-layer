/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.filters;

import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;
import org.zowe.apiml.constants.ApimlConstants;
import org.zowe.apiml.gateway.service.InstanceInfoService;
import org.zowe.apiml.message.core.MessageService;
import org.zowe.apiml.product.opentelemetry.OtelRequestContext;
import org.zowe.apiml.util.CookieUtil;
import org.zowe.apiml.zaas.ZaasTokenResponse;
import reactor.core.publisher.Mono;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import static org.zowe.apiml.constants.ApimlConstants.BEARER_AUTHENTICATION_PREFIX;

public abstract class AbstractTokenFilterFactory<T extends AbstractTokenFilterFactory.Config> extends AbstractAuthSchemeFactory<T, ZaasTokenResponse> {

    protected AbstractTokenFilterFactory(Class<T> configClazz, InstanceInfoService instanceInfoService, MessageService messageService) {
        super(configClazz, instanceInfoService, messageService);
    }

    @Override
    public GatewayFilter apply(T config) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    // the internal API cannot define generic more specifically
    @SuppressWarnings("squid:S2092")
    protected Mono<Void> processResponse(ServerWebExchange exchange, GatewayFilterChain chain, AuthorizationResponse<ZaasTokenResponse> tokenResponse) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean isLtpaToken(AtomicReference<ZaasTokenResponse> response) {
        return "LtpaToken2".equals(response.get().getCookieName());
    }

    @EqualsAndHashCode(callSuper = true)
    public static class Config extends AbstractAuthSchemeFactory.AbstractConfig {
    }
}
