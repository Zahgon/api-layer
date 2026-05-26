/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.client.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.zowe.apiml.message.core.MessageType;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.product.logging.annotations.InjectApimlLogger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Component
public class DiscoverableClientWebSocketConfigurer implements WebSocketConfigurer {

    @InjectApimlLogger
    private final ApimlLogger logger = ApimlLogger.empty();

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Configure buffer sizes for inbound messages
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
