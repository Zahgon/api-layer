/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.caching;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.netflix.discovery.EurekaClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import static reactor.core.publisher.Mono.*;

@Component
@Slf4j
public class LoadBalancerCache {

    private static final String CACHING_SERVICE_ID = "cachingservice";

    private final Map<String, LoadBalancerCacheRecord> localCache;

    private final CachingServiceClient remoteCache;

    private final EurekaClient eurekaClient;

    private final ObjectMapper mapper = new ObjectMapper();

    public static final String LOAD_BALANCER_KEY_PREFIX = "lb.";

    public LoadBalancerCache(EurekaClient eurekaClient, CachingServiceClient cachingServiceClient) {
        this.remoteCache = cachingServiceClient;
        this.eurekaClient = eurekaClient;
        localCache = new ConcurrentHashMap<>();
        mapper.registerModule(new JavaTimeModule());
    }

    private Mono<Boolean> cachingServiceAvailability() {
        return Mono.fromCallable(() -> eurekaClient.getApplication(CACHING_SERVICE_ID)).map(app -> !app.getInstances().isEmpty()).switchIfEmpty(Mono.just(false));
    }

    /**
     * Store information about instance the user is balanced towards.
     * If there is already existing record, it will be updated
     *
     * @param user     User being routed towards southbound service
     * @param service  Service towards which is the user routed
     * @param loadBalancerCacheRecord  Object containing the selected instance and its creation time
     * @return Mono success / error
     */
    public Mono<Void> store(String user, String service, LoadBalancerCacheRecord loadBalancerCacheRecord) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Mono<Void> storeToRemoteCache(String user, String service, LoadBalancerCacheRecord loadBalancerCacheRecord) {
        try {
            String serializedRecord = mapper.writeValueAsString(loadBalancerCacheRecord);
            CachingServiceClient.ApiKeyValue toStore = new CachingServiceClient.ApiKeyValue(getKey(user, service), serializedRecord);
            return createToRemoteCache(user, service, loadBalancerCacheRecord, toStore);
        } catch (JsonProcessingException e) {
            log.debug("Failed to serialize record for user: {}, service: {}, record {},  with exception: ", user, service, loadBalancerCacheRecord, e);
            return error(e);
        }
    }

    private Mono<Void> createToRemoteCache(String user, String service, LoadBalancerCacheRecord loadBalancerCacheRecord, CachingServiceClient.ApiKeyValue toStore) {
        return remoteCache.create(toStore).onErrorResume(createException -> {
            if (isCausedByCacheConflict(createException)) {
                return updateToRemoteCache(user, service, loadBalancerCacheRecord, toStore);
            } else {
                log.debug("Failed to create record for user: {}, service: {}, record {}, with exception: ", user, service, loadBalancerCacheRecord, createException);
                return error(createException);
            }
        }).doOnSuccess(v -> log.debug("Created record to remote cache for user: {}, service: {}, record: {}", user, service, loadBalancerCacheRecord));
    }

    private Mono<Void> updateToRemoteCache(String user, String service, LoadBalancerCacheRecord loadBalancerCacheRecord, CachingServiceClient.ApiKeyValue toStore) {
        return remoteCache.update(toStore).doOnSuccess(v -> log.debug("Updated record to remote cache for user: {}, service: {}, record: {}", user, service, loadBalancerCacheRecord)).doOnError(updateException -> log.debug("Failed to update record for user: {}, service: {}, record {}, with exception: ", user, service, loadBalancerCacheRecord, updateException));
    }

    private boolean isCausedByCacheConflict(Throwable e) {
        return e instanceof CachingServiceClientException ex && ex.getStatusCode() == HttpStatus.SC_CONFLICT;
    }

    /**
     * Retrieve information about selected instance for combination of User and Service.
     *
     * @param user    User being routed towards southbound service
     * @param service Service towards which is the user routed
     * @return Retrieved record containing the instance to use for this user and its creation time.
     */
    public Mono<LoadBalancerCacheRecord> retrieve(String user, String service) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Delete information stored for given user and service.
     *
     * @param user    User being routed towards southbound service
     * @param service Service towards which is the user routed
     */
    public Mono<Void> delete(String user, String service) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private String getKey(String user, String service) {
        return LOAD_BALANCER_KEY_PREFIX + user.toLowerCase() + ":" + service.toLowerCase();
    }

    /**
     * Data POJO that represents entry in load balancing service cache
     */
    @Data
    public static class LoadBalancerCacheRecord {

        public static final LoadBalancerCacheRecord NONE = new LoadBalancerCacheRecord(null, null);

        private final String instanceId;

        private final LocalDateTime creationTime;

        public LoadBalancerCacheRecord(String instanceId) {
            this(instanceId, LocalDateTime.now());
        }

        @JsonCreator
        public LoadBalancerCacheRecord(@JsonProperty("instanceId") String instanceId, @JsonProperty("creationTime") LocalDateTime creationTime) {
            this.instanceId = instanceId;
            this.creationTime = creationTime;
        }
    }
}
