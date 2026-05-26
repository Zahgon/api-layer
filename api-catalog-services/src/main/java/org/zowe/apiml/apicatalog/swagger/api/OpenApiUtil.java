/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.apicatalog.swagger.api;

import lombok.experimental.UtilityClass;
import org.zowe.apiml.config.ApiInfo;
import org.zowe.apiml.product.constants.CoreService;
import org.zowe.apiml.product.instance.ServiceAddress;

@UtilityClass
public class OpenApiUtil {

    private static final String SWAGGER_LOCATION_LINK = "[Swagger/OpenAPI JSON Document]";

    private static final String CATALOG_VERSION = "/api/v1";

    private static final String CATALOG_APIDOC_ENDPOINT = "/apidoc";

    public static final String SEPARATOR = "/";

    public static final String URL_ENCODED_SPACE = "%20";

    public static String getOpenApiLink(String serviceId, ApiInfo apiInfo, ServiceAddress gatewayConfigProperties, String scheme) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String getBasePath(String serviceId, ApiDocPath<?> apiDocPath) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
