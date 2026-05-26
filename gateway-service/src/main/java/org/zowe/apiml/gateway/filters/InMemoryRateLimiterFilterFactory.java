/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.zowe.apiml.message.core.Message;
import org.zowe.apiml.message.core.MessageService;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.product.logging.annotations.InjectApimlLogger;
import reactor.core.publisher.Mono;
import java.util.List;

@Component
public class InMemoryRateLimiterFilterFactory extends AbstractGatewayFilterFactory<InMemoryRateLimiterFilterFactory.Config> {

    @InjectApimlLogger
    private ApimlLogger apimlLog = ApimlLogger.empty();

    private InMemoryRateLimiter rateLimiter;

    private final KeyResolver keyResolver;

    private final ObjectMapper mapper;

    private final MessageService messageService;

    public InMemoryRateLimiterFilterFactory(InMemoryRateLimiter rateLimiter, KeyResolver keyResolver, ObjectMapper mapper, MessageService messageService) {
        super(Config.class);
        this.rateLimiter = rateLimiter;
        this.keyResolver = keyResolver;
        this.mapper = mapper;
        this.messageService = messageService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Getter
    @Setter
    public static class Config {

        private int capacity;

        private int tokens;

        private int refillDuration;
    }
}
