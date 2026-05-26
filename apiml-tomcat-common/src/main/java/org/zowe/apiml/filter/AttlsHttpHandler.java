/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.AbstractServerHttpRequest;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.SslInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.server.adapter.DefaultServerWebExchange;
import org.springframework.web.server.i18n.LocaleContextResolver;
import org.springframework.web.server.session.DefaultWebSessionManager;
import org.springframework.web.server.session.WebSessionManager;
import org.zowe.apiml.message.core.Message;
import org.zowe.apiml.message.core.MessageService;
import org.zowe.commons.attls.ContextIsNotInitializedException;
import org.zowe.commons.attls.InboundAttls;
import org.zowe.commons.attls.IoctlCallException;
import org.zowe.commons.attls.StatConn;
import org.zowe.commons.attls.UnknownEnumValueException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@ConditionalOnProperty(name = "server.attlsServer.enabled", havingValue = "true")
@Slf4j
public class AttlsHttpHandler implements BeanPostProcessor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final MessageService messageService;

    private final LocaleContextResolver localeContextResolver;

    private final WebSessionManager sessionManager = new DefaultWebSessionManager();

    private final ServerCodecConfigurer serverCodecConfigurer = ServerCodecConfigurer.create();

    @Lazy
    public AttlsHttpHandler(MessageService messageService, LocaleContextResolver localeContextResolver) {
        this.messageService = messageService;
        this.localeContextResolver = localeContextResolver;
    }

    private Mono<Void> writeError(ServerHttpRequest request, ServerHttpResponse response, String message) {
        var serverWebExchange = new DefaultServerWebExchange(request, response, sessionManager, serverCodecConfigurer, localeContextResolver);
        response.setRawStatusCode(500);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);
        DataBuffer buffer = serverWebExchange.getResponse().bufferFactory().wrap(message.getBytes(StandardCharsets.UTF_8));
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }

    private String getMessage(String key) {
        Message message = messageService.createMessage(key);
        try {
            return objectMapper.writeValueAsString(message.mapToView());
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    Mono<Void> internalError(ServerHttpRequest request, ServerHttpResponse response) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Mono<Void> unsecureError(ServerHttpRequest request, ServerHttpResponse response) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    ServerHttpRequest updateCertificate(ServerHttpRequest request, HttpServletRequest nativeRequest, byte[] rawCertificate) throws CertificateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Builder
    @Value
    static class AttlsSslInfo implements SslInfo {

        String sessionId;

        X509Certificate[] peerCertificates;
    }
}
