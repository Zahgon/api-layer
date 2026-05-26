/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zaas.security.service.zosmf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.zowe.apiml.security.common.config.AuthConfigurationProperties;
import org.zowe.apiml.security.common.error.ServiceNotAccessibleException;
import org.zowe.apiml.security.common.login.ChangePasswordRequest;
import org.zowe.apiml.security.common.login.LoginRequest;
import org.zowe.apiml.security.common.token.TokenNotValidException;
import org.zowe.apiml.zaas.ZaasTokenResponse;
import org.zowe.apiml.zaas.security.service.AuthenticationService;
import org.zowe.apiml.zaas.security.service.TokenCreationService;
import org.zowe.apiml.zaas.security.service.schema.source.AuthSource;
import org.zowe.apiml.zaas.security.service.token.JWKResolver;
import javax.management.ServiceNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import static org.zowe.apiml.zaas.security.service.zosmf.ZosmfService.TokenType.JWT;
import static org.zowe.apiml.zaas.security.service.zosmf.ZosmfService.TokenType.LTPA;

@Primary
@Service
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ZosmfService extends AbstractZosmfService {

    private static final String JWT_ENDPOINT_ERROR_MSGID = "org.zowe.apiml.security.auth.zosmf.jwtEndpointError";

    private static final String CACHE_INVALIDATED_JWT_TOKENS = "invalidatedJwtTokens";

    /**
     * Enumeration of supported security tokens
     */
    @AllArgsConstructor
    @Getter
    public enum TokenType {

        JWT("jwtToken"), LTPA("LtpaToken2");

        private final String cookieName;
    }

    /**
     * Response of authentication, contains all data to next processing
     */
    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class AuthenticationResponse {

        private String domain;

        private final Map<TokenType, String> tokens;
    }

    /**
     * DTO with base information about z/OSMF (version and realm/domain)
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ZosmfInfo {

        @JsonProperty("zosmf_version")
        private int version;

        @JsonProperty("zosmf_full_version")
        private String fullVersion;

        @JsonProperty(ZOSMF_DOMAIN)
        private String safRealm;
    }

    private final List<TokenValidationStrategy> tokenValidationStrategy;

    private final AuthenticationService authenticationService;

    private final JWKResolver jwkResolver;

    private ZosmfService meAsProxy;

    private TokenCreationService tokenCreationService;

    public ZosmfService(final AuthConfigurationProperties authConfigurationProperties, @Qualifier("restTemplateWithoutKeystore") final RestTemplate restTemplateWithoutKeystore, final ObjectMapper securityObjectMapper, final ApplicationContext applicationContext, final AuthenticationService authenticationService, List<TokenValidationStrategy> tokenValidationStrategy, JWKResolver jwkResolver) {
        super(applicationContext, authConfigurationProperties, restTemplateWithoutKeystore, securityObjectMapper);
        this.tokenValidationStrategy = tokenValidationStrategy;
        this.authenticationService = authenticationService;
        this.jwkResolver = jwkResolver;
    }

    @PostConstruct
    @Override
    public void afterPropertiesSet() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Retryable(value = { TokenNotValidException.class }, maxAttempts = 2, backoff = @Backoff(value = 1500))
    public AuthenticationResponse authenticate(Authentication authentication) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Retryable(maxAttempts = 2, backoff = @Backoff(value = 1500))
    public ResponseEntity<String> changePassword(Authentication authentication) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks if jwtToken is in the list of invalidated tokens.
     *
     * @param jwtToken token to check
     * @return true - token is invalidated, otherwise token is still valid
     */
    @Cacheable(value = CACHE_INVALIDATED_JWT_TOKENS, unless = "true", key = "#jwtToken", condition = "#jwtToken != null")
    public Boolean isInvalidated(String jwtToken) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @return String containing the zosmf realm/domain
     */
    @Cacheable("zosmfInfo")
    public String getZosmfRealm(String infoURIEndpoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Break in ZOWE case is left intentionally
    @SuppressWarnings("java:S128")
    public ZaasTokenResponse exchangeAuthenticationForZosmfToken(String token, AuthSource.Parsed authSource) throws ServiceNotFoundException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Verify whether the service is actually accessible.
     * <p>
     * Note: This method uses getURI, it's also verifying eureka registration
     *
     * @return true when it's possible to access the Info endpoint via GET.
     */
    public boolean isAccessible() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private String getURI(String serviceId, String path) {
        String baseUrl = getURI(serviceId);
        URL url;
        try {
            url = new URL(baseUrl);
        } catch (MalformedURLException e) {
            throw new ServiceNotAccessibleException("Malformed z/OSMF URL", e);
        }
        return UrlUtils.buildFullRequestUrl(url.getProtocol(), url.getHost(), url.getPort(), path, null);
    }

    /**
     * POST to provided url and return authentication response
     *
     * @param authentication with credentials
     * @param url            String containing auth endpoint to be used
     * @return AuthenticationResponse containing auth token, either LTPA or JWT
     */
    protected AuthenticationResponse issueAuthenticationRequest(Authentication authentication, String url, HttpMethod httpMethod) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * PUT to provided url and return authentication response
     *
     * @param authentication with credentials
     * @param url            String containing change password endpoint to be used
     * @return ResponseEntity
     */
    protected ResponseEntity<String> issueChangePasswordRequest(Authentication authentication, String url, HttpMethod httpMethod) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private RuntimeException handleServerErrorOnChangePasswordCall(HttpServerErrorException e) {
        try {
            ZosmfAuthResponse response = securityObjectMapper.readValue(e.getResponseBodyAsByteArray(), ZosmfAuthResponse.class);
            if (response.getReturnCode() == 4) {
                apimlLog.log("org.zowe.apiml.security.auth.zosmf.changePwd.internalError", e.getResponseBodyAsString());
                return new AuthenticationServiceException("z/OSMF internal error: " + e.getResponseBodyAsString());
            } else {
                // TODO https://github.com/zowe/api-layer/issues/2995 - API ML will return 401 in these cases now, the message is still not accurate
                log.debug("Failed to change password, z/OSMF response: {}", e.getResponseBodyAsString());
                return new BadCredentialsException("Failed to change password, z/OSMF response: " + e.getResponseBodyAsString());
            }
        } catch (IOException ioe) {
            log.error("Error processing change password response body: {}", ioe.getMessage());
            return new AuthenticationServiceException("Error processing change password response", ioe);
        }
    }

    /**
     * Check if call to ZOSMF_AUTHENTICATE_END_POINT resolves
     *
     * @param httpMethod HttpMethod to be checked for existence
     * @return boolean, containing true if endpoint resolves
     */
    @Cacheable(value = "zosmfAuthenticationEndpoint", key = "#httpMethod.name()")
    public boolean authenticationEndpointExists(HttpMethod httpMethod, HttpHeaders headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if call to ZOSMF_JWT_END_POINT resolves
     *
     * @return true if endpoint resolves, otherwise false
     */
    @Cacheable(value = "zosmfJwtEndpoint")
    public boolean jwtEndpointExists(HttpHeaders headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tries to call ZOSMF authentication endpoint with HTTP Post method
     *
     * @return true, if zosmf login endpoint is presented
     */
    public boolean loginEndpointExists() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tries to call ZOSMF authentication endpoint with HTTP Delete method
     *
     * @return true, if zosmf logout endpoint is presented
     */
    public boolean logoutEndpointExists() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tries to call ZOSMF JWT Builder endpoint
     *
     * @return true if endpoint exists, otherwise false
     */
    public boolean jwtBuilderEndpointExists() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validates jwt token with all available strategies.
     *
     * @param token
     * @return true if at least one validation strategy evaluates token as valid
     * @throws ServiceNotAccessibleException if all validation strategies failed because of an error
     * @throws TokenNotValidException if all token validation strategies evaluate token as invalid
     */
    public boolean validate(String token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean requestIsAuthenticated(TokenValidationRequest request) {
        return TokenValidationRequest.STATUS.AUTHENTICATED.equals(request.getAuthenticated());
    }

    public Map<String, Boolean> getEndpointMap() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void invalidate(TokenType type, String token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Method reads authentication values from answer of REST call. It read all supported tokens, which are returned
     * from z/OSMF.
     *
     * @param responseEntity answer of REST call
     * @return AuthenticationResponse with all supported tokens from responseEntity
     */
    protected ZosmfService.AuthenticationResponse getAuthenticationResponse(ResponseEntity<String> responseEntity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public JsonWebKeySet getPublicKeys() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
