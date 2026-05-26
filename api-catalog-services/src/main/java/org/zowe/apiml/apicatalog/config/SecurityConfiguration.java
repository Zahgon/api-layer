/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.apicatalog.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.WebFilter;
import org.zowe.apiml.apicatalog.security.ApiCatalogLogoutSuccessHandler;
import org.zowe.apiml.config.ApplicationInfo;
import org.zowe.apiml.constants.ApimlConstants;
import org.zowe.apiml.message.api.ApiMessageView;
import org.zowe.apiml.message.core.MessageService;
import org.zowe.apiml.security.client.EnableApimlAuth;
import org.zowe.apiml.security.client.service.GatewaySecurity;
import org.zowe.apiml.security.common.config.AuthConfigurationProperties;
import org.zowe.apiml.security.common.config.SafSecurityConfigurationProperties;
import org.zowe.apiml.security.common.login.LoginFilter;
import org.zowe.apiml.security.common.token.TokenAuthentication;
import org.zowe.apiml.security.common.token.TokenNotValidException;
import org.zowe.apiml.security.common.util.X509Util;
import reactor.core.publisher.Mono;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import static org.apache.hc.core5.http.HttpStatus.SC_UNAUTHORIZED;
import static org.zowe.apiml.constants.ApimlConstants.HEADER_OIDC_TOKEN;
import static org.zowe.apiml.security.common.token.TokenAuthentication.createAuthenticated;

/**
 * Main configuration class of Spring web security for Api Catalog
 * binds authentication managers
 * configures ignores for static content
 * adds endpoints and secures them
 * adds security filters
 */
@Slf4j
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@EnableApimlAuth
@EnableReactiveMethodSecurity
@EnableConfigurationProperties(SafSecurityConfigurationProperties.class)
public class SecurityConfiguration {

    private static final String APIDOC_ROUTES = "/apidoc/**";

    private static final String STATIC_REFRESH_ROUTE = "/static-api/refresh";

    private static final String APPLICATION_HEALTH = "/application/health";

    private final ApplicationInfo applicationInfo;

    private final GatewaySecurity gatewaySecurity;

    private final AuthConfigurationProperties authConfigurationProperties;

    private final MessageService messageService;

    private final ObjectMapper objectMapper;

    @Value("${apiml.security.ssl.verifySslCertificatesOfServices:true}")
    private boolean verifySslCertificatesOfServices;

    private WebFilter basicAuthenticationFilter;

    private WebFilter tokenAuthenticationFilter;

    private WebFilter oidcAuthenticationFilter;

    @PostConstruct
    void initFilters() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private String[] getFullUrls(String... baseUrl) {
        String prefix = applicationInfo.isModulith() ? "/apicatalog/api/v1" : "/apicatalog";
        for (int i = 0; i < baseUrl.length; i++) {
            baseUrl[i] = prefix + baseUrl[i];
        }
        return baseUrl;
    }

    @Bean
    @Order(1)
    @ConditionalOnMissingBean(name = "modulithConfig")
    SecurityWebFilterChain loginSecurityWebFilterChain(ServerHttpSecurity http, ServerAuthenticationEntryPoint serverAuthenticationEntryPoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @Order(2)
    @ConditionalOnMissingBean(name = "modulithConfig")
    SecurityWebFilterChain logoutSecurityWebFilterChain(ServerHttpSecurity http, ServerAuthenticationEntryPoint serverAuthenticationEntryPoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Filter chain for protecting /apidoc/** endpoints with MF credentials for client certificate.
     */
    @Bean
    @Order(3)
    @ConditionalOnMissingBean(name = "modulithConfig")
    SecurityWebFilterChain basicAuthOrTokenOrCertApiDocFilterChain(ServerHttpSecurity http, ServerAuthenticationEntryPoint serverAuthenticationEntryPoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @Order(4)
    @ConditionalOnMissingBean(name = "modulithConfig")
    SecurityWebFilterChain healthEndpointSecurityWebFilterChain(ServerHttpSecurity http, @Value("${apiml.health.protected:true}") boolean isHealthEndpointProtected, ServerAuthenticationEntryPoint serverAuthenticationEntryPoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @Order(5)
    @ConditionalOnMissingBean(name = "modulithConfig")
    SecurityWebFilterChain basicAuthOrTokenAllEndpointsFilterChain(ServerHttpSecurity http, ServerAuthenticationEntryPoint serverAuthenticationEntryPoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Default filter chain to protect all routes with MF credentials.
     */
    @Bean
    @Order(6)
    SecurityWebFilterChain webSecurityCustomizer(ServerHttpSecurity http, ServerAuthenticationEntryPoint serverAuthenticationEntryPoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private ServerHttpSecurity baseConfiguration(ServerHttpSecurity http, ServerAuthenticationEntryPoint serverAuthenticationEntryPoint, WebFilter... webFiltersAuthorization) {
        var antMatcher = new AntPathMatcher();
        http.csrf(ServerHttpSecurity.CsrfSpec::disable).securityContextRepository(NoOpServerSecurityContextRepository.getInstance()).formLogin(ServerHttpSecurity.FormLoginSpec::disable).httpBasic(ServerHttpSecurity.HttpBasicSpec::disable).headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.hsts(ServerHttpSecurity.HeaderSpec.HstsSpec::disable).frameOptions(ServerHttpSecurity.HeaderSpec.FrameOptionsSpec::disable)).exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec.authenticationEntryPoint((exchange, exception) -> {
            String requestedPath = exchange.getRequest().getPath().toString();
            log.debug("Unauthorized access to '{}' endpoint", requestedPath);
            if (Stream.of(getFullUrls("/application/**", APIDOC_ROUTES, STATIC_REFRESH_ROUTE)).anyMatch(pattern -> antMatcher.match(pattern, requestedPath))) {
                exchange.getResponse().getHeaders().add(HttpHeaders.WWW_AUTHENTICATE, ApimlConstants.BASIC_AUTHENTICATION_PREFIX);
            }
            return serverAuthenticationEntryPoint.commence(exchange, exception);
        }));
        Stream.of(webFiltersAuthorization).forEach(webFilter -> http.addFilterBefore(webFilter, SecurityWebFiltersOrder.AUTHENTICATION));
        return http;
    }

    WebFilter basicAuthenticationFilter(GatewaySecurity gatewaySecurity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    WebFilter tokenAuthenticationFilter(GatewaySecurity gatewaySecurity, AuthConfigurationProperties authConfigurationProperties, MessageService messageService, ObjectMapper mapper) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    WebFilter oidcAuthenticationFilter(GatewaySecurity gatewaySecurity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    ReactiveAuthenticationManager reactiveAuthenticationManager() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    ServerAuthenticationEntryPoint serverAuthenticationEntryPoint(MessageService messageService, ObjectMapper mapper) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
