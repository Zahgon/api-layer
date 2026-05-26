/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpUpgradeHandler;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;
import org.zowe.apiml.constants.ApimlConstants;
import org.zowe.apiml.gateway.filters.AbstractAuthSchemeFactory.AuthorizationResponse;
import org.zowe.apiml.gateway.filters.ErrorHeaders;
import org.zowe.apiml.gateway.filters.RequestCredentials;
import org.zowe.apiml.gateway.filters.ZaasInternalErrorException;
import org.zowe.apiml.gateway.filters.ZaasSchemeTransform;
import org.zowe.apiml.message.core.MessageService;
import org.zowe.apiml.passticket.ApplicationNameNotProvidedException;
import org.zowe.apiml.passticket.IRRPassTicketGenerationException;
import org.zowe.apiml.passticket.PassTicketService;
import org.zowe.apiml.product.opentelemetry.OtelRequestContext;
import org.zowe.apiml.ticket.TicketResponse;
import org.zowe.apiml.zaas.ZaasTokenResponse;
import org.zowe.apiml.zaas.security.service.TokenCreationService;
import org.zowe.apiml.zaas.security.service.schema.source.AuthSource;
import org.zowe.apiml.zaas.security.service.schema.source.AuthSourceService;
import org.zowe.apiml.zaas.security.service.schema.source.OIDCAuthSource;
import org.zowe.apiml.zaas.security.service.schema.source.PATAuthSource;
import org.zowe.apiml.zaas.security.service.zosmf.ZosmfService;
import reactor.core.publisher.Mono;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Optional;
import static org.zowe.apiml.security.SecurityUtils.COOKIE_AUTH_NAME;
import static org.zowe.apiml.security.common.filter.CategorizeCertsFilter.ATTR_NAME_CLIENT_AUTH_X509_CERTIFICATE;

/**
 * {@code ZaasSchemeTransformApi} is the internal implementation of {@link ZaasSchemeTransform}
 * <p>
 * Unlike {@code ZaasSchemeTransformRest}, which makes HTTP requests to the ZAAS service,
 * this implementation directly invokes service layer components within the same application context.
 * </p>
 *
 * <p>
 * This class provides support for authentication schemes like:
 * <ul>
 *     <li>PassTicket generation</li>
 *     <li>SAF Identity Token generation</li>
 *     <li>z/OSMF token exchange</li>
 *     <li>Zowe JWT generation</li>
 * </ul>
 *
 * <p>
 * This bean is only active when {@code modulithConfig} is present in the Spring context.
 * </p>
 *
 * @see ZaasSchemeTransform
 * @see org.zowe.apiml.gateway.filters.ZaasSchemeTransformRest
 */
@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnBean(name = "modulithConfig")
public class ZaasSchemeTransformApi implements ZaasSchemeTransform {

    private static final ClientResponse.Headers EMPTY_HEADERS = new ErrorHeaders();

    private final AuthSourceService authSourceService;

    private final PassTicketService passTicketService;

    private final ZosmfService zosmfService;

    private final TokenCreationService tokenCreationService;

    private final MessageService messageService;

    @Value("${apiml.service.apimlId:apiml}")
    private String currentApimlId;

    private ErrorHeaders createErrorMessage(String errorMessage) {
        return new ErrorHeaders(errorMessage);
    }

    private <R> Mono<AuthorizationResponse<R>> createInvalidAuthenticationErrorMessage() {
        var messageKey = "org.zowe.apiml.common.unauthorized";
        var logMessage = messageService.createMessage(messageKey).mapToLogMessage();
        var headers = new ErrorHeaders(logMessage);
        return Mono.just(new AuthorizationResponse<>(headers, null));
    }

    private AuthorizationResponse<String> createMissingAuthenticationErrorMessage() {
        var messageKey = "org.zowe.apiml.zaas.security.schema.missingAuthentication";
        var logMessage = messageService.createMessage(messageKey).mapToLogMessage();
        return new AuthorizationResponse<>(createErrorMessage(logMessage), InsufficientAuthenticationException.class.getName());
    }

    private <R> Mono<AuthorizationResponse<R>> createAuthorizationResponse(ErrorHeaders headers, R response) {
        return Mono.just(new AuthorizationResponse<>(headers, response));
    }

    private <R> Mono<AuthorizationResponse<R>> handleMissingOrInvalidAuth(OtelRequestContext context) {
        var response = createMissingAuthenticationErrorMessage();
        context.authErrorType(response.getBody());
        return createAuthorizationResponse((ErrorHeaders) response.getHeaders(), null);
    }

    private <R> Mono<AuthorizationResponse<R>> handleMissingApplicationName(OtelRequestContext context) {
        context.authErrorType(ApplicationNameNotProvidedException.class.getName());
        return createAuthorizationResponse(createErrorMessage("ApplicationName not provided."), null);
    }

    @Override
    public Mono<AuthorizationResponse<TicketResponse>> passticket(RequestCredentials requestCredentials, ServerWebExchange exchange) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void updateServiceId(Optional<AuthSource> authSource, RequestCredentialsHttpServletRequestAdapter request) {
        authSource.filter(PATAuthSource.class::isInstance).map(PATAuthSource.class::cast).filter(as -> StringUtils.isBlank(as.getDefaultServiceId())).ifPresent(as -> as.setDefaultServiceId(request.getServiceId()));
    }

    @Override
    public Mono<AuthorizationResponse<ZaasTokenResponse>> safIdt(RequestCredentials requestCredentials, ServerWebExchange exchange) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Mono<AuthorizationResponse<ZaasTokenResponse>> zosmf(RequestCredentials requestCredentials, ServerWebExchange exchange) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Mono<AuthorizationResponse<ZaasTokenResponse>> zoweJwt(RequestCredentials requestCredentials, ServerWebExchange exchange) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @RequiredArgsConstructor
    private static class RequestCredentialsHttpServletRequestAdapter implements HttpServletRequest {

        private final RequestCredentials requestCredentials;

        @Delegate(excludes = Exclude.class)
        private HttpServletRequest request;

        public String getServiceId() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Cookie[] getCookies() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String getHeader(String name) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Object getAttribute(String name) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String getRequestURI() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        interface Exclude {

            Enumeration<String> getHeaders(String name);
        }
    }
}
