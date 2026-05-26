/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.caching.service.infinispan.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.infinispan.lock.api.ClusteredLock;
import org.infinispan.manager.DefaultCacheManager;
import org.zowe.apiml.cache.Storage;
import org.zowe.apiml.cache.StorageException;
import org.zowe.apiml.caching.model.KeyValue;
import org.zowe.apiml.caching.service.Messages;
import org.zowe.apiml.models.AccessTokenContainer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import static org.zowe.apiml.caching.service.infinispan.config.InfinispanConfig.CACHE_ZOWE;
import static org.zowe.apiml.caching.service.infinispan.config.InfinispanConfig.CACHE_ZOWE_INVALIDATED_TOKEN;

@Slf4j
@RequiredArgsConstructor
public class InfinispanStorage implements Storage {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final DefaultCacheManager defaultCacheManager;

    private final Supplier<ClusteredLock> lockSupplier;

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    private ConcurrentMap<String, KeyValue> getCache() {
        return defaultCacheManager.getCache(CACHE_ZOWE);
    }

    private ConcurrentMap<String, Map<String, String>> getTokenCache() {
        return defaultCacheManager.getCache(CACHE_ZOWE_INVALIDATED_TOKEN);
    }

    @Override
    public KeyValue create(String serviceId, KeyValue toCreate) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public KeyValue storeMapItem(String serviceId, String mapKey, KeyValue toCreate) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Map<String, String> getAllMapItems(String serviceId, String mapKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Map<String, Map<String, String>> getAllMaps(String serviceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public KeyValue read(String serviceId, String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public KeyValue update(String serviceId, KeyValue toUpdate) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public KeyValue delete(String serviceId, String toDelete) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Map<String, KeyValue> readForService(String serviceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void deleteForService(String serviceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void removeNonRelevantTokens(String serviceId, String mapKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void removeToken(String serviceId, String mapKey) {
        Map<String, String> map = getTokenCache().get(serviceId + mapKey);
        if (map != null && !map.isEmpty()) {
            Map<String, String> result = map.entrySet().stream().filter(entry -> {
                try {
                    AccessTokenContainer c = objectMapper.readValue(entry.getValue(), AccessTokenContainer.class);
                    return !c.getExpiresAt().isBefore(LocalDateTime.now());
                } catch (JsonProcessingException e) {
                    log.error("Not able to parse invalidToken json value.", e);
                    return true;
                }
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            getTokenCache().put(serviceId + mapKey, result);
        }
    }

    @Override
    public void removeNonRelevantRules(String serviceId, String mapKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void completeJoin(CompletableFuture<Boolean> complete) {
        try {
            complete.join();
        } catch (CompletionException e) {
            if (e.getCause() instanceof StorageException) {
                throw (StorageException) e.getCause();
            } else {
                log.error("Unexpected error while acquiring the lock ", e);
                throw e;
            }
        }
    }
}
