/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zaas.zaas;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.zowe.apiml.message.api.ApiMessageView;
import org.zowe.apiml.message.core.MessageService;
import org.zowe.apiml.passticket.IRRPassTicketGenerationException;
import org.zowe.apiml.passticket.UsernameNotProvidedException;
import org.zowe.apiml.security.common.auth.saf.EndpointImproperlyConfigureException;
import org.zowe.apiml.security.common.auth.saf.UnsupportedResourceClassException;
import org.zowe.apiml.security.common.token.TokenExpireException;
import org.zowe.apiml.security.common.token.TokenNotValidException;
import org.zowe.apiml.zaas.security.service.saf.SafIdtAuthException;
import org.zowe.apiml.zaas.security.service.saf.SafIdtException;
import org.zowe.apiml.zaas.security.service.schema.source.AuthSchemeException;
import org.zowe.apiml.passticket.ApplicationNameNotProvidedException;
import javax.management.ServiceNotFoundException;
import javax.net.ssl.SSLException;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@ConditionalOnMissingBean(name = "modulithConfig")
public class ZaasExceptionHandler {

    private final MessageService messageService;

    @ExceptionHandler(value = { IRRPassTicketGenerationException.class })
    public ResponseEntity<ApiMessageView> handlePassTicketException(IRRPassTicketGenerationException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(value = { UsernameNotProvidedException.class })
    public ResponseEntity<ApiMessageView> handleUsernameNotProvidedException(UsernameNotProvidedException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(value = { SafIdtException.class, SafIdtAuthException.class })
    public ResponseEntity<ApiMessageView> handleSafIdtExceptions(RuntimeException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(value = { ApplicationNameNotProvidedException.class, HttpMessageNotReadableException.class })
    public ResponseEntity<ApiMessageView> handleApplIdNotFoundException() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(value = { ServiceNotFoundException.class })
    public ResponseEntity<ApiMessageView> handleServiceNotFoundException(ServiceNotFoundException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(value = { IllegalStateException.class })
    public ResponseEntity<ApiMessageView> handleZoweJwtCreationErrors(IllegalStateException ex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(value = { TokenNotValidException.class, AuthSchemeException.class })
    public ResponseEntity<ApiMessageView> handleTokenNotValidException() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(value = { TokenExpireException.class })
    public ResponseEntity<ApiMessageView> handleTokenExpiredException() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiMessageView> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException accessDeniedException) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiMessageView> handleNoResourceFoundException(NoHandlerFoundException e) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiMessageView> handleMethodNotAllowedException(HttpServletRequest request, HttpRequestMethodNotSupportedException notAllowedMethodException) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(SSLException.class)
    public ResponseEntity<ApiMessageView> handleSslException(HttpServletRequest request, SSLException sslException) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(UnsupportedResourceClassException.class)
    public ResponseEntity<ApiMessageView> handleUnsupportedResourceClassException(UnsupportedResourceClassException unsupportedResourceClassException) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(EndpointImproperlyConfigureException.class)
    public ResponseEntity<ApiMessageView> handleendpointImproperlyConfigureException(EndpointImproperlyConfigureException improprietyConfigureException) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiMessageView> handleInternalException(Exception exception) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler({ IllegalArgumentException.class, MissingServletRequestParameterException.class })
    public ResponseEntity<ApiMessageView> handleIllegalArguments(Exception exception) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiMessageView> handleUnsupportedMediaException(HttpMediaTypeNotSupportedException exception) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
