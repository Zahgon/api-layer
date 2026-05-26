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

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryRateLimiter implements RateLimiter<InMemoryRateLimiter.Config> {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    @Value("${apiml.gateway.rateLimiterCapacity:20}")
    int capacity;

    @Value("${apiml.gateway.rateLimiterTokens:20}")
    int tokens;

    @Value("${apiml.gateway.rateLimiterRefillDuration:1}")
    int refillDuration;

    @Override
    public Mono<Response> isAllowed(String routeId, String id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Bucket newBucket(String id) {
        Bandwidth limit = Bandwidth.builder().capacity(capacity).refillIntervally(tokens, Duration.ofMinutes(refillDuration)).build();
        return Bucket.builder().addLimit(limit).build();
    }

    private Map<String, String> getHeaders(Bucket bucket) {
        Map<String, String> headers = new ConcurrentHashMap<>();
        headers.put("X-RateLimit-Remaining", String.valueOf(bucket.getAvailableTokens()));
        return headers;
    }

    public void setParameters(int capacity, int tokens, int refillDuration) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Map<String, Config> getConfig() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Class<Config> getConfigClass() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Config newConfig() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Setter
    @Getter
    public static class Config {

        private int capacity;

        private int tokens;

        private int refillDuration;
    }
}
