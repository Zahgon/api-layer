/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.apicatalog.swagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.models.Paths;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.SpringDocCustomizers;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.providers.SpringDocProviders;
import org.springdoc.core.service.AbstractRequestService;
import org.springdoc.core.service.GenericResponseService;
import org.springdoc.core.service.OpenAPIService;
import org.springdoc.core.service.OperationService;
import org.springdoc.webflux.api.OpenApiWebfluxResource;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.zowe.apiml.apicatalog.exceptions.ApiDocNotFoundException;
import org.zowe.apiml.apicatalog.model.ApiDocInfo;
import org.zowe.apiml.config.ApiInfo;
import org.zowe.apiml.product.gateway.GatewayClient;
import reactor.core.publisher.Mono;
import java.util.*;

@Slf4j
@Service
public class ApiDocRetrievalServiceLocal {

    private static final String SLASH = "/";

    private final Map<String, OpenApiWebfluxResource> apiDocResource = new HashMap<>();

    public ApiDocRetrievalServiceLocal(List<GroupedOpenApi> groupedOpenApis, ObjectFactory<OpenAPIService> openAPIBuilderObjectFactory, AbstractRequestService requestBuilder, GenericResponseService responseBuilder, OperationService operationParser, SpringDocConfigProperties springDocConfigProperties, SpringDocProviders springDocProviders, GatewayClient gatewayClient) {
        groupedOpenApis.forEach(groupedOpenApi -> {
            String group = groupedOpenApi.getGroup();
            SpringDocConfigProperties.GroupConfig groupConfig = new SpringDocConfigProperties.GroupConfig(group, groupedOpenApi.getPathsToMatch(), groupedOpenApi.getPackagesToScan(), groupedOpenApi.getPackagesToExclude(), groupedOpenApi.getPathsToExclude(), groupedOpenApi.getProducesToMatch(), groupedOpenApi.getConsumesToMatch(), groupedOpenApi.getHeadersToMatch(), groupedOpenApi.getDisplayName());
            springDocConfigProperties.addGroupConfig(groupConfig);
            Set<OpenApiCustomizer> openApiCustomizers = new HashSet<>(groupedOpenApi.getOpenApiCustomizers());
            openApiCustomizers.add(normalizePathsCustomizer(groupedOpenApi.getPathsToMatch()));
            var openApiWebfluxResource = new OpenApiWebfluxResource(groupedOpenApi.getGroup(), openAPIBuilderObjectFactory, requestBuilder, responseBuilder, operationParser, springDocConfigProperties, springDocProviders, new SpringDocCustomizers(Optional.of(openApiCustomizers), Optional.of(groupedOpenApi.getOperationCustomizers()), Optional.of(groupedOpenApi.getRouterOperationCustomizers()), Optional.of(groupedOpenApi.getOpenApiMethodFilters()), Optional.empty(), Optional.empty())) {

                @Override
                protected String getServerUrl(ServerHttpRequest serverHttpRequest, String apiDocsUrl) {
                    throw new UnsupportedOperationException("STUB: not implemented");
                }
            };
            apiDocResource.put(group, openApiWebfluxResource);
        });
    }

    String getCommonBasePath(List<String> paths) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    OpenApiCustomizer normalizePathsCustomizer(List<String> paths) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isSupported(String serviceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Mono<ApiDocInfo> retrieveApiDoc(ServiceInstance serviceInstance, ApiInfo apiInfo) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
