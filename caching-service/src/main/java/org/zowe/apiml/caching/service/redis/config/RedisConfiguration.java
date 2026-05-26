/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.caching.service.redis.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SslOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zowe.apiml.cache.Storage;
import org.zowe.apiml.caching.service.redis.RedisOperator;
import org.zowe.apiml.caching.service.redis.RedisStorage;
import org.zowe.apiml.message.core.MessageService;
import org.zowe.apiml.message.log.ApimlLogger;
import java.io.File;
import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "caching.storage.mode", havingValue = "redis")
public class RedisConfiguration {

    private final RedisConfig redisConfig;

    @Bean
    public Storage redis(MessageService messageService) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Package protected for unit testing.
     */
    RedisURI createRedisUri() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Package protected for unit testing.
     */
    RedisClient createRedisClient() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
