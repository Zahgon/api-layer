/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.product.routing;

import org.apache.commons.lang3.StringUtils;
import org.zowe.apiml.util.UrlUtils;
import java.util.HashMap;
import java.util.Map;

public class RoutedServices {

    private final Map<String, RoutedService> routedService = new HashMap<>();

    /**
     * Add route to the service
     *
     * @param route the route
     */
    public void addRoutedService(RoutedService route) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find RoutedService by Gateway Url
     *
     * @param gatewayUrl the url of gateway
     * @return the route
     */
    public RoutedService findServiceByGatewayUrl(String gatewayUrl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get best matching service url
     *
     * @param serviceUrl service url
     * @param type       service type
     * @return the route
     */
    public RoutedService getBestMatchingServiceUrl(String serviceUrl, ServiceType type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Verify all routing rules if there is any rules with a prefixes on at least on sides gateway or service
     * @return true is no rule or just empty is defined
     */
    public boolean isDefinedOnlyBypassRoutes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get best matching api url
     *
     * @param serviceUrl service url
     * @return the route
     */
    public RoutedService getBestMatchingApiUrl(String serviceUrl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean isServiceTypeMatch(Map.Entry<String, RoutedService> serviceEntry, ServiceType type) {
        String serviceEntryKey = serviceEntry.getKey().toLowerCase();
        String typeName = type.name().toLowerCase();
        return type.equals(ServiceType.ALL) || serviceEntryKey.startsWith(typeName);
    }

    private boolean isMatchingApiRoute(String serviceUrl, String routeServiceUrl) {
        return StringUtils.startsWithIgnoreCase(serviceUrl, routeServiceUrl);
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
