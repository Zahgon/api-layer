/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.service.routing;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;
import org.zowe.apiml.product.routing.RoutedService;
import org.zowe.apiml.util.StringUtils;

/**
 * Routing rule by path modify the path of the request. It makes this replacement:
 * <p>
 * from: /<serviceId>/<gatewayUrl>/<path>
 * to: /<serviceUrl>/<path>
 */
@Component
public class ByBasePath extends RouteDefinitionProducer {

    private static final String TARGET_HEADER_NAME = "X-Forward-To";

    public ByBasePath(DiscoveryLocatorProperties properties) {
        super(properties);
    }

    static String constructUrl(String... parts) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void setCondition(RouteDefinition routeDefinition, ServiceInstance serviceInstance, RoutedService routedService) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void setFilters(RouteDefinition routeDefinition, ServiceInstance serviceInstance, RoutedService routedService) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getOrder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
