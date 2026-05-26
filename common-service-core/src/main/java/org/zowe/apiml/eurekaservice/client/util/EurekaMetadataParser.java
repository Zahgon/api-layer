/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.eurekaservice.client.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.BooleanUtils;
import org.zowe.apiml.auth.Authentication;
import org.zowe.apiml.auth.AuthenticationScheme;
import org.zowe.apiml.config.ApiInfo;
import org.zowe.apiml.config.CodeSnippet;
import org.zowe.apiml.exception.MetadataValidationException;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.message.yaml.YamlMessageServiceInstance;
import org.zowe.apiml.product.routing.RoutedService;
import org.zowe.apiml.product.routing.RoutedServices;
import org.zowe.apiml.util.UrlUtils;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Supplier;
import static org.zowe.apiml.constants.EurekaMetadataDefinition.*;

public class EurekaMetadataParser {

    private static final String THREE_STRING_MERGE_FORMAT = "%s.%s.%s";

    private static final String FIVE_STRING_MERGE_FORMAT = "%s.%s.%s.%s.%s";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ApimlLogger apimlLog = ApimlLogger.of(EurekaMetadataParser.class, YamlMessageServiceInstance.getInstance());

    /**
     * Parse eureka metadata and construct ApiInfo with the values found
     *
     * @param eurekaMetadata the eureka metadata
     * @return ApiInfo list
     */
    public List<ApiInfo> parseApiInfo(Map<String, String> eurekaMetadata) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parse eureka metadata and add the routes found to the RoutedServices
     *
     * @param eurekaMetadata the eureka metadata
     * @return the RoutedServices
     */
    public RoutedServices parseRoutes(Map<String, String> eurekaMetadata) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parse eureka metadata and return list of routes
     *
     * @param eurekaMetadata the eureka metadata
     * @return list of all routes
     */
    public List<RoutedService> parseToListRoute(Map<String, String> eurekaMetadata) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean filterMetadata(Map.Entry<String, String> metadata) {
        return metadata.getKey().startsWith(ROUTES) && (metadata.getKey().endsWith(ROUTES_GATEWAY_URL) || metadata.getKey().endsWith(ROUTES_SERVICE_URL));
    }

    private RoutedService mapMetadataToRoutedService(Map.Entry<String, String> metadata, Map<String, String> routeMap) {
        String routeKey = metadata.getKey();
        String routeURL = metadata.getValue();
        String[] routeKeys = routeKey.split("\\.");
        if (routeKeys.length != 4) {
            return null;
        }
        String subServiceId = routeKeys[2];
        String routeKeyURL = routeKeys[3];
        return processUrls(routeMap, routeKeyURL, subServiceId, routeURL);
    }

    private RoutedService processUrls(Map<String, String> routeMap, String routeKeyURL, String subServiceId, String routeURL) {
        if (routeKeyURL.equals(ROUTES_GATEWAY_URL)) {
            String gatewayURL = UrlUtils.removeFirstAndLastSlash(routeURL);
            if (routeMap.containsKey(subServiceId)) {
                String serviceUrl = routeMap.get(subServiceId);
                routeMap.remove(subServiceId);
                return new RoutedService(subServiceId, gatewayURL, serviceUrl);
            } else {
                routeMap.put(subServiceId, gatewayURL);
            }
        }
        if (routeKeyURL.equals(ROUTES_SERVICE_URL)) {
            String serviceURL = UrlUtils.addFirstSlash(routeURL);
            if (routeMap.containsKey(subServiceId)) {
                String gatewayUrl = routeMap.get(subServiceId);
                routeMap.remove(subServiceId);
                return new RoutedService(subServiceId, gatewayUrl, serviceURL);
            } else {
                routeMap.put(subServiceId, serviceURL);
            }
        }
        return null;
    }

    /**
     * Generate Eureka metadata for ApiInfo configuration
     *
     * @param serviceId the identifier of a service which ApiInfo configuration belongs
     * @param apiInfo   ApiInfo config data
     * @return the generated Eureka metadata
     */
    public static Map<String, String> generateMetadata(String serviceId, ApiInfo apiInfo) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static void validateUrl(String url, Supplier<String> exceptionSupplier) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new MetadataValidationException(exceptionSupplier.get(), e);
        }
    }

    public Authentication parseAuthentication(Map<String, String> eurekaMetadata) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
