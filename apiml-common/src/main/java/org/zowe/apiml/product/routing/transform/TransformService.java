/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.product.routing.transform;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.product.gateway.GatewayClient;
import org.zowe.apiml.product.instance.ServiceAddress;
import org.zowe.apiml.product.logging.annotations.InjectApimlLogger;
import org.zowe.apiml.product.routing.RoutedService;
import org.zowe.apiml.product.routing.RoutedServices;
import org.zowe.apiml.product.routing.ServiceType;
import org.zowe.apiml.util.UrlUtils;
import java.net.URI;

/**
 * Class for producing service URL using Gateway hostname and service route
 */
@RequiredArgsConstructor
public class TransformService {

    private static final String SEPARATOR = "/";

    private final GatewayClient gatewayClient;

    @InjectApimlLogger
    private ApimlLogger apimlLog = ApimlLogger.empty();

    /**
     * Construct the URL using gateway hostname and route
     *
     * @param type        the type of the route
     * @param serviceId   the service id
     * @param serviceUrl  the service URL
     * @param routes      the routes
     * @param httpsScheme https scheme flag
     * @return the new URL
     * @throws URLTransformationException if the path of the service URL is not valid
     */
    public String transformURL(ServiceType type, String serviceId, String serviceUrl, RoutedServices routes, boolean httpsScheme) throws URLTransformationException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private String transformURL(String serviceId, String serviceUriPath, RoutedService route, boolean httpsScheme, URI originalUri) throws URLTransformationException {
        String endPoint = getShortEndpoint(route.getServiceUrl(), serviceUriPath);
        if (!endPoint.isEmpty() && !endPoint.startsWith("/")) {
            throw new URLTransformationException("The path " + originalUri.getPath() + " of the service URL " + originalUri + " is not valid.");
        }
        ServiceAddress gatewayConfigProperties = gatewayClient.getGatewayConfigProperties();
        if (originalUri != null && originalUri.toString().startsWith("//")) {
            return String.format("//%s/%s%s%s", gatewayConfigProperties.getHostname(), serviceId, StringUtils.isEmpty(route.getGatewayUrl()) ? "" : "/" + route.getGatewayUrl(), endPoint);
        }
        String scheme = httpsScheme ? "https" : gatewayConfigProperties.getScheme();
        return String.format("%s://%s/%s%s%s", scheme, gatewayConfigProperties.getHostname(), serviceId, StringUtils.isEmpty(route.getGatewayUrl()) ? "" : "/" + route.getGatewayUrl(), endPoint);
    }

    public String transformAbsoluteURL(String serviceId, String locationUri, RoutedService route) throws URLTransformationException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean isRelative(String endpoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Construct the API base path using the route
     *
     * @param serviceId  the service id
     * @param serviceUrl the service URL
     * @param routes     the routes
     * @return the new URL
     * @throws URLTransformationException if the path of the service base path is not valid or cannot be found
     */
    public String retrieveApiBasePath(String serviceId, String serviceUrl, RoutedServices routes) throws URLTransformationException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get short endpoint
     *
     * @param routeServiceUrl service url of route
     * @param endPoint        the endpoint of method
     * @return short endpoint
     */
    private String getShortEndpoint(String routeServiceUrl, String endPoint) {
        String shortEndPoint = endPoint;
        if (!SEPARATOR.equals(routeServiceUrl) && StringUtils.isNotBlank(routeServiceUrl)) {
            shortEndPoint = shortEndPoint.replaceFirst(UrlUtils.removeLastSlash(routeServiceUrl), "");
        }
        return shortEndPoint;
    }
}
