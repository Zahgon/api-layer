/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.*;
import org.springframework.web.server.adapter.DefaultServerWebExchange;
import org.springframework.web.server.i18n.LocaleContextResolver;
import org.springframework.web.server.session.DefaultWebSessionManager;
import org.zowe.apiml.exception.MetadataValidationException;
import org.zowe.apiml.gateway.filters.ForbidCharacterException;
import org.zowe.apiml.gateway.filters.ForbidSlashException;
import org.zowe.apiml.gateway.filters.ZaasInternalErrorException;
import org.zowe.apiml.message.core.Message;
import org.zowe.apiml.message.core.MessageService;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.product.logging.annotations.InjectApimlLogger;
import org.zowe.apiml.security.common.error.ServiceNotAccessibleException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.net.ssl.SSLException;
import static org.apache.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GatewayExceptionHandler {

    private static final String WWW_AUTHENTICATE = "WWW-Authenticate";

    private static String WWW_AUTHENTICATE_FORMAT = "Basic realm=\"%s\"";

    private static final String DEFAULT_REALM = "Realm";

    private final ObjectMapper mapper;

    private final MessageService messageService;

    private final LocaleContextResolver localeContextResolver;

    @InjectApimlLogger
    private final ApimlLogger apimlLog = ApimlLogger.empty();

    private static String createHeaderValue(String realm) {
        Assert.notNull(realm, "realm cannot be null");
        return String.format(WWW_AUTHENTICATE_FORMAT, realm);
    }

    public Mono<Void> setBodyResponse(ServerWebExchange exchange, int responseCode, String messageCode, Object... args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setWwwAuthenticateResponse(ServerWebExchange exchange) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(WebClientResponseException.BadRequest.class)
    public Mono<Void> handleBadRequestException(ServerWebExchange exchange, WebClientResponseException.BadRequest ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(ForbidCharacterException.class)
    public Mono<Void> handleForbidCharacterException(ServerWebExchange exchange, ForbidCharacterException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(ForbidSlashException.class)
    public Mono<Void> handleForbidSlashException(ServerWebExchange exchange, ForbidSlashException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler({ AuthenticationException.class, WebClientResponseException.Unauthorized.class })
    public Mono<Void> handleAuthenticationException(ServerWebExchange exchange, Exception ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler({ AccessDeniedException.class, WebClientResponseException.Forbidden.class })
    public Mono<Void> handleAccessDeniedException(ServerWebExchange exchange, Exception ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler({ NoResourceFoundException.class, WebClientResponseException.NotFound.class })
    public Mono<Void> handleNoResourceFoundException(ServerWebExchange exchange, Exception ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler({ MethodNotAllowedException.class, WebClientResponseException.MethodNotAllowed.class })
    public Mono<Void> handleMethodNotAllowedException(ServerWebExchange exchange, Exception ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler({ HttpMediaTypeException.class, WebClientResponseException.UnsupportedMediaType.class })
    public Mono<Void> handleHttpMediaTypeException(ServerWebExchange exchange, Exception ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(SSLException.class)
    public Mono<Void> handleSslException(ServerWebExchange exchange, SSLException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler({ Exception.class })
    public Mono<Void> handleInternalError(ServerWebExchange exchange, Exception ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler({ MetadataValidationException.class })
    public Mono<Void> handleMetadataValidationException(ServerWebExchange exchange, Exception ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler({ ResponseStatusException.class })
    public Mono<Void> handleStatusError(ServerWebExchange exchange, ResponseStatusException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler({ ServiceNotAccessibleException.class, WebClientResponseException.ServiceUnavailable.class })
    public Mono<Void> handleServiceNotAccessibleException(ServerWebExchange exchange, Exception ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(ZaasInternalErrorException.class)
    public Mono<Void> handleZaasInternalErrorException(ServerWebExchange exchange, ZaasInternalErrorException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<ErrorInfo>> handleDeserialization(ServerWebInputException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Throwable getRootCause(Throwable ex) {
        Throwable cause = ex;
        while (cause.getCause() != null && cause != cause.getCause()) {
            cause = cause.getCause();
        }
        return cause;
    }

    @Data
    @AllArgsConstructor
    static class ErrorInfo {

        private String error;

        private String exception;
    }
}
