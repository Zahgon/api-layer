/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.config;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.parser.OpenAPIV3Parser;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.zowe.apiml.security.common.error.ServiceNotAccessibleException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import static org.zowe.apiml.product.constants.CoreService.ZAAS;

@Slf4j
@Configuration("gatewaySwaggerConfig")
@ConditionalOnMissingBean(name = "modulithConfig")
@RequiredArgsConstructor
@OpenAPIDefinition(security = { @SecurityRequirement(name = "LoginBasicAuth"), @SecurityRequirement(name = "ClientCert") }, info = @Info(title = "API Gateway", description = """
    REST API for the API Gateway, which is a component of the API Mediation Layer.
    Use this API to perform tasks such as logging in with the mainframe credentials and checking authorization to mainframe resources.
    """))
@SecurityScheme(name = "LoginBasicAuth", type = SecuritySchemeType.HTTP, scheme = "basic")
@SecurityScheme(type = SecuritySchemeType.MUTUALTLS, name = "ClientCert", description = "Client certificate X509")
public class SwaggerConfig {

    @Value("${server.attlsClient.enabled:false}")
    private boolean isClientAttlsenabled;

    private final EurekaClient eurekaClient;

    private final WebClient webClient;

    private URI zaasUri;

    @PostConstruct
    void initEurekaListener() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private String updateUrlFromZaas(String zaasUrl) {
        return zaasUrl.replaceFirst("/zaas/", "/gateway/");
    }

    void updatePaths(OpenAPI openApi, String pathToMatch) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String download(URI uri) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    public OpenApiCustomizer servletEndpoints(@Value("${springdoc.pathsToMatch:/}") String pathToMatch) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
