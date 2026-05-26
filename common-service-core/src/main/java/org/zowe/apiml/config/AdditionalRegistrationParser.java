/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.apache.commons.lang.StringUtils.isNotBlank;

public class AdditionalRegistrationParser {

    public static final String ADDITIONAL_REGISTRATION_INDEX_GROUP_NAME = "index";

    public static final Pattern DISCOVERYSERVICEURLS_PATTERN = Pattern.compile("^ZWE_CONFIGS_APIML_SERVICE_ADDITIONALREGISTRATION_(?<index>\\d+)_DISCOVERYSERVICEURLS$", Pattern.CASE_INSENSITIVE);

    public static final Pattern ROUTE_SERVICEURL_PATTERN = Pattern.compile("^ZWE_CONFIGS_APIML_SERVICE_ADDITIONALREGISTRATION_(?<index>\\d+)_ROUTES_(?<routeIndex>\\d+)_SERVICEURL$", Pattern.CASE_INSENSITIVE);

    public static final Pattern ROUTE_GATEWAYURL_PATTERN = Pattern.compile("^ZWE_CONFIGS_APIML_SERVICE_ADDITIONALREGISTRATION_(?<index>\\d+)_ROUTES_(?<routeIndex>\\d+)_GATEWAYURL$", Pattern.CASE_INSENSITIVE);

    public List<AdditionalRegistration> extractAdditionalRegistrations(Map<String, String> allProperties) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static void putRouteServiceUrl(Map<Integer, AdditionalRegistration> map, Pair<Integer, Long> pair, String value) {
        AdditionalRegistration registration = map.get(pair.getKey());
        if (registration != null && StringUtils.isNoneBlank(value)) {
            int routeIndex = pair.getValue().intValue();
            while (registration.getRoutes().size() <= routeIndex) {
                registration.getRoutes().add(new AdditionalRegistration.Route());
            }
            registration.getRoutes().get(routeIndex).setServiceUrl(value);
        }
    }

    private static void putRouteGatewayUrl(Map<Integer, AdditionalRegistration> map, Pair<Integer, Long> pair, String value) {
        AdditionalRegistration registration = map.get(pair.getKey());
        if (registration != null && StringUtils.isNoneBlank(value)) {
            int routeIndex = pair.getValue().intValue();
            while (registration.getRoutes().size() <= routeIndex) {
                registration.getRoutes().add(new AdditionalRegistration.Route());
            }
            registration.getRoutes().get(routeIndex).setGatewayUrl(value);
        }
    }

    private static void putAdditionalRegistration(Map<Integer, AdditionalRegistration> map, Integer index, String value) {
        if (StringUtils.isNoneBlank(value)) {
            map.put(index, AdditionalRegistration.builder().discoveryServiceUrls(value).routes(new ArrayList<>()).build());
        }
    }

    public static Optional<Integer> matchDiscoveryUrl(String line) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Optional<Pair<Integer, Long>> parseServiceUrl(String line) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Optional<Pair<Integer, Long>> parseGatewayUrl(String line) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static boolean isRouteDefined(AdditionalRegistration.Route route) {
        return route != null && (isNotBlank(route.getGatewayUrl()) || isNotBlank(route.getServiceUrl()));
    }
}
