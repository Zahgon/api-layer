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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.core.jackson.mixin.MediaTypeMixin;
import io.swagger.v3.core.jackson.mixin.SchemaMixin;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.zowe.apiml.apicatalog.model.ApiDocInfo;
import org.zowe.apiml.apicatalog.exceptions.ApiDocTransformationException;
import org.zowe.apiml.apicatalog.swagger.SecuritySchemeSerializer;
import org.zowe.apiml.config.ApiInfo;
import org.zowe.apiml.config.ApplicationInfo;
import org.zowe.apiml.product.gateway.GatewayClient;
import org.zowe.apiml.product.instance.ServiceAddress;
import org.zowe.apiml.product.routing.RoutedService;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
public class ApiDocV3Service extends AbstractApiDocService<OpenAPI, PathItem> {

    @Value("${gateway.scheme.external:https}")
    private String scheme;

    public ApiDocV3Service(ApplicationInfo applicationInfo, GatewayClient gatewayClient) {
        super(applicationInfo, gatewayClient);
    }

    public String transformApiDoc(String serviceId, ApiDocInfo apiDocInfo) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void updateServer(OpenAPI openAPI) {
        if (openAPI.getServers() != null) {
            openAPI.getServers().forEach(server -> server.setUrl(String.format("%s://%s/%s", scheme, getHostname(), server.getUrl())));
        }
    }

    private void updateSwaggerUrl(OpenAPI openAPI, String serviceId, ApiInfo apiInfo, boolean hidden, String scheme) {
        ServiceAddress gatewayConfigProperties = gatewayClient.getGatewayConfigProperties();
        String swaggerLink = OpenApiUtil.getOpenApiLink(serviceId, apiInfo, gatewayConfigProperties, scheme);
        if (!hidden) {
            openAPI.getInfo().setDescription(openAPI.getInfo().getDescription() + swaggerLink);
        }
    }

    /**
     * Updates Servers and Paths in OpenAPI
     *
     * @param openAPI    the API doc
     * @param serviceId  the unique service id
     * @param apiDocInfo the service information
     * @param hidden     do not set Paths for automatically generated API doc
     */
    protected void updatePaths(OpenAPI openAPI, String serviceId, ApiDocInfo apiDocInfo, boolean hidden) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Server getBestMatchingServer(List<Server> servers, ApiDocInfo apiDocInfo) {
        if (servers != null && !servers.isEmpty()) {
            for (Server server : servers) {
                String basePath = getBasePath(server.getUrl());
                RoutedService route = getRoutedServiceByApiInfo(apiDocInfo, basePath);
                if (route != null) {
                    return server;
                }
            }
            return servers.get(0);
        }
        return null;
    }

    private String getBasePath(String serverUrl) {
        String basePath = "";
        try {
            URI uri = new URI(serverUrl);
            basePath = uri.getPath();
        } catch (Exception e) {
            log.debug("serverUrl is not parse-able");
        }
        return basePath;
    }

    /**
     * Updates External documentation in OpenAPI
     *
     * @param openAPI    the API doc
     * @param apiDocInfo the service information
     */
    protected void updateExternalDoc(OpenAPI openAPI, ApiDocInfo apiDocInfo) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void updateServerUrl(OpenAPI openAPI, Server server, String basePath) {
        if (server != null) {
            // server expects no / at start of url
            server.setUrl(basePath.startsWith("/") ? basePath.substring(1) : basePath);
            openAPI.setServers(Collections.singletonList(server));
        } else {
            openAPI.addServersItem(new Server().url(basePath));
        }
    }

    private boolean isHidden(List<Tag> tags) {
        return tags != null && tags.stream().anyMatch(tag -> tag.getName().equals(HIDDEN_TAG));
    }

    ObjectMapper objectMapper() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
