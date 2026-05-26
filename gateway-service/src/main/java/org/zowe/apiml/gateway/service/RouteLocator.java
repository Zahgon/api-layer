/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.stereotype.Service;
import org.springframework.util.PatternMatchUtils;
import org.zowe.apiml.auth.Authentication;
import org.zowe.apiml.auth.AuthenticationScheme;
import org.zowe.apiml.eurekaservice.client.util.EurekaMetadataParser;
import org.zowe.apiml.gateway.service.routing.RouteDefinitionProducer;
import org.zowe.apiml.gateway.service.scheme.SchemeHandler;
import org.zowe.apiml.product.routing.RoutedService;
import org.zowe.apiml.util.StringUtils;
import reactor.core.publisher.Flux;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import static org.zowe.apiml.constants.EurekaMetadataDefinition.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class RouteLocator implements RouteDefinitionLocator {

    private static final EurekaMetadataParser metadataParser = new EurekaMetadataParser();

    @Value("${apiml.routing.ignoredServices:}")
    private String[] ignoredServices;

    @Value("${apiml.service.forwardClientCertEnabled:false}")
    private boolean forwardingClientCertEnabled;

    @Value("${otel.sdk.disabled:true}")
    boolean otelDisabled;

    @Value("${apiml.gateway.servicesToLimitRequestRate:}")
    List<String> servicesToLimitRequestRateProperty;

    List<String> servicesToLimitRequestRate;

    @Value("${apiml.gateway.servicesToDisableRetry:}")
    List<String> servicesToDisableRetryProperty;

    List<String> servicesToDisableRetry;

    private final ReactiveDiscoveryClient discoveryClient;

    @Qualifier("commonFilters")
    private final List<FilterDefinition> commonFilters;

    @Qualifier("commonNoRetryFilters")
    private final List<FilterDefinition> commonNoRetryFilters;

    private final List<RouteDefinitionProducer> routeDefinitionProducers;

    private final List<SchemeHandler> schemeHandlersList;

    private final Map<AuthenticationScheme, SchemeHandler> schemeHandlers = new EnumMap<>(AuthenticationScheme.class);

    @PostConstruct
    void afterPropertiesSet() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Flux<List<ServiceInstance>> getServiceInstances() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void setAuth(ServiceInstance serviceInstance, RouteDefinition routeDefinition, Authentication auth) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Stream<RoutedService> getRoutedService(ServiceInstance serviceInstance) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static <T> List<T> join(List<T> a, List<T> b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    List<FilterDefinition> getPostRoutingFilters(ServiceInstance serviceInstance, RoutedService routedService) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private List<RouteDefinition> getAuthFilterPerRoute(AtomicInteger orderHolder, ServiceInstance serviceInstance) {
        Authentication auth = metadataParser.parseAuthentication(serviceInstance.getMetadata());
        // iterate over routing definition (ordered from the longest one to match with the most specific)
        return getRoutedService(serviceInstance).map(routedService -> routeDefinitionProducers.stream().sorted(Comparator.comparingInt(RouteDefinitionProducer::getOrder)).map(rdp -> {
            // generate a new routing rule by a specific produces
            RouteDefinition routeDefinition = rdp.get(serviceInstance, routedService);
            routeDefinition.setOrder(orderHolder.getAndIncrement());
            routeDefinition.getFilters().addAll(getPostRoutingFilters(serviceInstance, routedService));
            setAuth(serviceInstance, routeDefinition, auth);
            return routeDefinition;
        }).toList()).flatMap(List::stream).toList();
    }

    /**
     * It generates each rule for each combination of instance x routing x generator ({@link RouteDefinitionProducer})
     * The routes are sorted by serviceUrl to avoid clashing between multiple levels of paths, ie. / vs. /a.
     * Sorting routes and generators by order allows to redefine order of each rule. There is no possible to have
     * multiple valid rules for the same case at one moment.
     *
     * @return routing rules
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean filterIgnored(String serviceId) {
        return !PatternMatchUtils.simpleMatch(ignoredServices, serviceId.toLowerCase());
    }
}
