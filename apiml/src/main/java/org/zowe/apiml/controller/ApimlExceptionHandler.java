/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.i18n.LocaleContextResolver;
import org.zowe.apiml.cache.StorageException;
import org.zowe.apiml.gateway.controllers.GatewayExceptionHandler;
import org.zowe.apiml.message.core.MessageService;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.passticket.IRRPassTicketGenerationException;
import org.zowe.apiml.passticket.PassTicketException;
import org.zowe.apiml.passticket.UsernameNotProvidedException;
import org.zowe.apiml.product.logging.annotations.InjectApimlLogger;
import org.zowe.apiml.security.common.error.AccessTokenInvalidBodyException;
import org.zowe.apiml.security.common.error.AccessTokenMissingBodyException;
import org.zowe.apiml.security.common.error.ErrorType;
import org.zowe.apiml.security.common.error.ZosAuthenticationException;
import reactor.core.publisher.Mono;
import java.util.Optional;
import static org.apache.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ApimlExceptionHandler extends GatewayExceptionHandler {

    private static final String GENERATE_FAILED_MESSAGE_KEY = "org.zowe.apiml.security.ticket.generateFailed";

    @InjectApimlLogger
    private final ApimlLogger apimlLog = ApimlLogger.empty();

    public ApimlExceptionHandler(ObjectMapper mapper, MessageService messageService, LocaleContextResolver localeContextResolver) {
        super(mapper, messageService, localeContextResolver);
    }

    @ExceptionHandler(AccessTokenInvalidBodyException.class)
    public Mono<Void> handleAccessTokenBodyNotValidException(ServerWebExchange exchange, AccessTokenInvalidBodyException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(AccessTokenMissingBodyException.class)
    public Mono<Void> handleAccessTokenMissingBodyException(ServerWebExchange exchange, AccessTokenMissingBodyException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public Mono<Void> handleAuthenticationCredentialsNotFoundException(ServerWebExchange exchange, AuthenticationCredentialsNotFoundException e) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(InvalidWebFingerConfigurationException.class)
    public Mono<Void> handleInvalidWebFingerConfigurationException(ServerWebExchange exchange, InvalidWebFingerConfigurationException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(IncorrectPassTicketRequestBodyException.class)
    public Mono<Void> handleIncorrectPassTicketRequestBodyException(ServerWebExchange exchange, IncorrectPassTicketRequestBodyException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(SafAccessDeniedException.class)
    public Mono<Void> handleSafAccessDeniedException(ServerWebExchange exchange, SafAccessDeniedException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(UsernameNotProvidedException.class)
    public Mono<Void> handleUsernameNotProvidedException(ServerWebExchange exchange, UsernameNotProvidedException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(PassTicketException.class)
    public Mono<Void> handlePassTicketException(ServerWebExchange exchange, PassTicketException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public Mono<Void> handleBadCredentialsException(ServerWebExchange exchange, BadCredentialsException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(StorageException.class)
    public Mono<Void> handleStorageException(ServerWebExchange exchange, StorageException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(ZosAuthenticationException.class)
    public Mono<Void> handleZosAuthenticationException(ServerWebExchange exchange, ZosAuthenticationException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
