/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.websocket;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.Endpoint;
import jakarta.websocket.server.ServerEndpointConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.config.HttpClientProperties;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.lang.Nullable;
import org.springframework.web.reactive.socket.HandshakeInfo;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.adapter.ContextWebSocketHandler;
import org.springframework.web.reactive.socket.adapter.StandardWebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.StandardWebSocketUpgradeStrategy;
import org.springframework.web.server.ServerWebExchange;
import org.zowe.apiml.gateway.config.ApimlServerEndpointConfig;
import reactor.core.publisher.Mono;
import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;
import static reactor.core.publisher.Mono.*;

@RequiredArgsConstructor
public class ApimlRequestUpgradeStrategy extends StandardWebSocketUpgradeStrategy {

    private final HttpClientProperties httpClientProperties;

    @Override
    protected void upgradeHttpToWebSocket(HttpServletRequest request, HttpServletResponse response, ServerEndpointConfig endpointConfig, Map<String, String> pathParams) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Mono<Void> upgrade(ServerWebExchange exchange, WebSocketHandler handler, @Nullable String subProtocol, Supplier<HandshakeInfo> handshakeInfoFactory) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
