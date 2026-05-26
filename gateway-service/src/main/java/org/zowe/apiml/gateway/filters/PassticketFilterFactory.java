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

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;
import org.zowe.apiml.auth.AuthenticationScheme;
import org.zowe.apiml.constants.ApimlConstants;
import org.zowe.apiml.gateway.service.InstanceInfoService;
import org.zowe.apiml.message.core.MessageService;
import org.zowe.apiml.product.opentelemetry.OtelRequestContext;
import org.zowe.apiml.ticket.TicketResponse;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.function.Function;

@Service
public class PassticketFilterFactory extends AbstractAuthSchemeFactory<PassticketFilterFactory.Config, TicketResponse> {

    @Value("${apiml.security.auth.passticket.customUserHeader:}")
    private String customUserHeader;

    @Value("${apiml.security.auth.passticket.customAuthHeader:}")
    private String customPassTicketHeader;

    private final ZaasSchemeTransform zaasSchemeTransform;

    public PassticketFilterFactory(ZaasSchemeTransform zaasSchemeTransform, InstanceInfoService instanceInfoService, MessageService messageService) {
        super(Config.class, instanceInfoService, messageService);
        this.zaasSchemeTransform = zaasSchemeTransform;
    }

    @Override
    protected AuthenticationScheme getAuthenticationScheme() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected Function<RequestCredentials, Mono<AuthorizationResponse<TicketResponse>>> getAuthorizationResponseTransformer(ServerWebExchange exchange) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected RequestCredentials.RequestCredentialsBuilder createRequestCredentials(ServerWebExchange exchange, Config config) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected Mono<Void> processResponse(ServerWebExchange exchange, GatewayFilterChain chain, AuthorizationResponse<TicketResponse> ticketResponse) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public GatewayFilter apply(Config config) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Config extends AbstractAuthSchemeFactory.AbstractConfig {

        private String applicationName;
    }
}
