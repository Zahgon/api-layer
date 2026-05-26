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

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.headers.XForwardedHeadersFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.AuthenticatedPrincipalServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.ServerAuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.firewall.StrictServerWebExchangeFirewall;
import org.springframework.security.web.server.savedrequest.CookieServerRequestCache;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.zowe.apiml.gateway.config.oidc.ClientConfiguration;
import org.zowe.apiml.gateway.controllers.GatewayExceptionHandler;
import org.zowe.apiml.gateway.filters.proxyheaders.AdditionalRegistrationGatewayRegistry;
import org.zowe.apiml.gateway.filters.proxyheaders.X509AndGwAwareXForwardedHeadersFilter;
import org.zowe.apiml.gateway.filters.security.AuthExceptionHandlerReactive;
import org.zowe.apiml.gateway.filters.security.BasicAuthFilter;
import org.zowe.apiml.gateway.filters.security.TokenAuthFilter;
import org.zowe.apiml.gateway.service.BasicAuthProvider;
import org.zowe.apiml.gateway.service.TokenProvider;
import org.zowe.apiml.product.constants.CoreService;
import org.zowe.apiml.security.HttpsConfig;
import org.zowe.apiml.security.common.config.AuthConfigurationProperties;
import org.zowe.apiml.security.common.config.SafSecurityConfigurationProperties;
import org.zowe.apiml.security.common.util.X509Util;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static org.zowe.apiml.gateway.services.ServicesInfoController.SERVICES_FULL_URL;
import static org.zowe.apiml.gateway.services.ServicesInfoController.SERVICES_SHORT_URL;
import static org.zowe.apiml.security.SecurityUtils.COOKIE_AUTH_NAME;

@Configuration
@RequiredArgsConstructor
@EnableReactiveMethodSecurity
@EnableConfigurationProperties(SafSecurityConfigurationProperties.class)
public class WebSecurity {

    public static final String CONTEXT_PATH = "/" + CoreService.GATEWAY.getServiceId();

    public static final String REGISTRY_PATH = CONTEXT_PATH + "/api/v1/registry";

    public static final String CONFORMANCE_SHORT_URL = CONTEXT_PATH + "/conformance/**";

    public static final String CONFORMANCE_LONG_URL = CONTEXT_PATH + "/api/v1" + "/conformance/**";

    public static final String VALIDATE_SHORT_URL = "gateway/validate";

    public static final String VALIDATE_LONG_URL = "gateway/api/v1/validate";

    public static final String COOKIE_NONCE = "oidc_nonce";

    public static final String COOKIE_STATE = "oidc_state";

    public static final String COOKIE_RETURN_URL = "oidc_return_url";

    private static final Pattern CLIENT_REG_ID = Pattern.compile("^" + CONTEXT_PATH + "/login/oauth2/code/([^/]+)$");

    private static final Predicate<HttpCookie> HAS_NO_VALUE = cookie -> cookie == null || StringUtils.isEmpty(cookie.getValue());

    private static final List<String> COOKIES = Arrays.asList(COOKIE_NONCE, COOKIE_STATE, COOKIE_RETURN_URL);

    public static final String OAUTH_2_AUTHORIZATION = CONTEXT_PATH + "/oauth2/authorization/**";

    public static final String OAUTH_2_AUTHORIZATION_BASE_URI = CONTEXT_PATH + "/oauth2/authorization/";

    public static final String OAUTH_2_AUTHORIZATION_URI = CONTEXT_PATH + "/oauth2/authorization/{registrationId}";

    public static final String OAUTH_2_REDIRECT_URI = CONTEXT_PATH + "/login/oauth2/code/**";

    public static final String OAUTH_2_REDIRECT_LOGIN_URI = CONTEXT_PATH + "/login/oauth2/code/{registrationId}";

    @Value("${apiml.security.oidc.cookie.sameSite:Lax}")
    public String sameSite;

    @Value("${apiml.security.x509.registry.allowedUsers:#{null}}")
    private String allowedUsers;

    @Value("${apiml.health.protected:true}")
    private boolean isHealthEndpointProtected;

    @Value("${apiml.security.enableStrictUrlValidation:false}")
    private boolean isStrictUrlValidationEnabled;

    private final ClientConfiguration clientConfiguration;

    private final TokenProvider tokenProvider;

    private final BasicAuthProvider basicAuthProvider;

    private final ApplicationContext applicationContext;

    private Predicate<String> usernameAuthorizationTester;

    @PostConstruct
    void initScopes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private ResponseCookie.ResponseCookieBuilder defaultCookieAttr(ResponseCookie.ResponseCookieBuilder builder) {
        return builder.path("/").sameSite(sameSite).httpOnly(true).secure(true);
    }

    private ResponseCookie createCookie(String name, String value) {
        return defaultCookieAttr(ResponseCookie.from(name, value)).build();
    }

    /**
     * Security chain for oauth2 client. To enable this chain, please refer to Zowe OIDC configuration.
     */
    @Bean
    SecurityWebFilterChain oauth2WebFilterChain(ServerHttpSecurity http, Optional<ReactiveOAuth2AuthorizedClientService> reactiveOAuth2AuthorizedClientService, Optional<ApimlServerAuthorizationRequestRepository> requestRepository, Optional<ServerOAuth2AuthorizationRequestResolver> authorizationRequestResolver) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Mono<Object> updateCookies(WebFilterExchange webFilterExchange, OAuth2AuthorizedClient oAuth2AuthorizedClient) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void redirect(ServerHttpResponse response, String location) {
        response.getHeaders().set(HttpHeaders.LOCATION, location);
        response.setStatusCode(HttpStatusCode.valueOf(302));
    }

    private void clearCookies(WebFilterExchange webFilterExchange) {
        COOKIES.forEach(cookie -> webFilterExchange.getExchange().getResponse().addCookie(defaultCookieAttr(ResponseCookie.from(cookie).maxAge(0)).build()));
    }

    @Bean
    ReactiveOAuth2AuthorizedClientService authorizedClientService(Optional<ReactiveClientRegistrationRepository> clientRegistrationRepository) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    ServerOAuth2AuthorizationRequestResolver authorizationRequestResolver(Optional<InMemoryReactiveClientRegistrationRepository> inMemoryReactiveClientRegistrationRepository) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    ApimlServerAuthorizationRequestRepository requestRepository(Optional<ServerOAuth2AuthorizationRequestResolver> authorizationRequestResolver) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    ReactiveClientRegistrationRepository clientRegistrationRepository() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    ServerOAuth2AuthorizedClientRepository serverOAuth2AuthorizedClientRepository(Optional<ReactiveOAuth2AuthorizedClientService> clientService) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @ConditionalOnBean(ReactiveClientRegistrationRepository.class)
    ReactiveOAuth2AuthorizedClientManager gatewayReactiveOAuth2AuthorizedClientManager(Optional<ReactiveClientRegistrationRepository> clientRegistrationRepository, Optional<ReactiveOAuth2AuthorizedClientService> authorizedClientService) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private List<ClientRegistration> getClientRegistrations() {
        return clientConfiguration.getConfigurations().values().stream().map(c -> ClientRegistration.withRegistrationId(c.getId()).clientId(c.getRegistration().getClientId()).clientSecret(c.getRegistration().getClientSecret()).clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC).authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE).redirectUri(c.getRegistration().getRedirectUri()).scope(c.getRegistration().getScope()).authorizationUri(c.getProvider().getAuthorizationUri()).tokenUri(c.getProvider().getTokenUri()).userInfoUri(c.getProvider().getUserInfoUri()).userNameAttributeName(c.getProvider().getUserNameAttribute()).jwkSetUri(c.getProvider().getJwkSetUri()).clientName(c.getId()).build()).toList();
    }

    public ServerHttpSecurity defaultSecurityConfig(ServerHttpSecurity http) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    SecurityWebFilterChain defaultSecurityWebFilterChain(ServerHttpSecurity http) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @Order(1)
    @ConditionalOnMissingBean(name = "modulithConfig")
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, AuthConfigurationProperties authConfigurationProperties, AuthExceptionHandlerReactive authExceptionHandlerReactive) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @Primary
    ReactiveUserDetailsService userDetailsService() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static String getClientRegistrationId(ServerWebExchange exchange) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @RequiredArgsConstructor
    class ApimlServerAuthorizationRequestRepository implements ServerAuthorizationRequestRepository<OAuth2AuthorizationRequest> {

        final ServerOAuth2AuthorizationRequestResolver authorizationRequestResolver;

        @Override
        public Mono<OAuth2AuthorizationRequest> loadAuthorizationRequest(ServerWebExchange exchange) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public OAuth2AuthorizationRequest createAuthorizationRequest(ServerWebExchange exchange, OAuth2AuthorizationRequest original) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private static String createHash(String value) {
            try {
                var md = MessageDigest.getInstance("SHA-256");
                byte[] digest = md.digest(value.getBytes(StandardCharsets.US_ASCII));
                return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override
        public Mono<Void> saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, ServerWebExchange exchange) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        String getReturnUrl(ServerWebExchange exchange) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Mono<OAuth2AuthorizationRequest> removeAuthorizationRequest(ServerWebExchange exchange) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    WebFilter writeableHeaders() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    StrictServerWebExchangeFirewall httpFirewall() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @Primary
    @ConditionalOnProperty(name = "spring.cloud.gateway.x-forwarded.enabled", matchIfMissing = true)
    XForwardedHeadersFilter xForwardedHeadersFilter(@Value("${apiml.security.forwardHeader.trustedProxies:#{null}}") String trustedProxies, HttpsConfig httpsConfig, AdditionalRegistrationGatewayRegistry additionalRegistrationGatewayRegistry) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
