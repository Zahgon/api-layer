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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.WsWebSocketContainer;
import org.springframework.cloud.gateway.config.HttpClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import org.springframework.web.reactive.socket.server.RequestUpgradeStrategy;
import org.zowe.apiml.gateway.websocket.ApimlRequestUpgradeStrategy;
import org.zowe.apiml.gateway.websocket.ApimlWebSocketClient;
import org.zowe.apiml.product.web.HttpConfig;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebSocketConfig {

    private final HttpClientProperties httpClientProperties;

    @Bean
    @Primary
    WebSocketClient webSocketClient(HttpConfig config) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @Primary
    RequestUpgradeStrategy requestUpgradeStrategy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
