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

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import static org.zowe.apiml.constants.ApimlConstants.X_INSTANCEID;

/**
 * Filter that adds the 'X-InstanceId' header to the response if it is present in the request.
 */
@Component
@ConditionalOnProperty(name = "apiml.routing.instanceIdHeader", havingValue = "true")
public class DeterministicRoutingResponseHeaderFilterFactory implements GlobalFilter, Ordered {

    /**
     * Filters the request and response to add the 'X-InstanceId' header to the response if present in the request.
     *
     * @param exchange the current server exchange
     * @param chain provides a way to delegate to the next filter
     * @return Mono<Void> to indicate when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getOrder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
