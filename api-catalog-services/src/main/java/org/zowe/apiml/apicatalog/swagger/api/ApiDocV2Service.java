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

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.models.*;
import io.swagger.parser.SwaggerParser;
import io.swagger.util.Json;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.zowe.apiml.apicatalog.model.ApiDocInfo;
import org.zowe.apiml.apicatalog.exceptions.ApiDocTransformationException;
import org.zowe.apiml.config.ApiInfo;
import org.zowe.apiml.config.ApplicationInfo;
import org.zowe.apiml.product.gateway.GatewayClient;
import org.zowe.apiml.product.instance.ServiceAddress;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class ApiDocV2Service extends AbstractApiDocService<Swagger, Path> {

    @Value("${gateway.scheme.external:https}")
    private String scheme;

    public ApiDocV2Service(ApplicationInfo applicationInfo, GatewayClient gatewayClient) {
        super(applicationInfo, gatewayClient);
    }

    public String transformApiDoc(String serviceId, ApiDocInfo apiDocInfo) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Updates scheme and hostname, and adds API doc link to Swagger
     *
     * @param swagger   the API doc
     * @param serviceId the unique service id
     */
    private void updateSchemeHost(Swagger swagger, String serviceId) {
        log.debug("Updating host for service with id: " + serviceId + " to: " + getHostname());
        swagger.setSchemes(Collections.singletonList(Scheme.forValue(scheme)));
        swagger.setHost(getHostname());
    }

    private void updateSwaggerUrl(Swagger swagger, String serviceId, ApiInfo apiInfo, boolean hidden, String scheme) {
        ServiceAddress gatewayConfigProperties = gatewayClient.getGatewayConfigProperties();
        String swaggerLink = OpenApiUtil.getOpenApiLink(serviceId, apiInfo, gatewayConfigProperties, scheme);
        if (!hidden) {
            swagger.getInfo().setDescription(swagger.getInfo().getDescription() + swaggerLink);
        }
    }

    /**
     * Updates BasePath and Paths in Swagger
     *
     * @param swagger    the API doc
     * @param serviceId  the unique service id
     * @param apiDocInfo the service information
     * @param hidden     do not set Paths for automatically generated API doc
     */
    protected void updatePaths(Swagger swagger, String serviceId, ApiDocInfo apiDocInfo, boolean hidden) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Updates External documentation in Swagger
     *
     * @param swagger    the API doc
     * @param apiDocInfo the service information
     */
    protected void updateExternalDoc(Swagger swagger, ApiDocInfo apiDocInfo) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
