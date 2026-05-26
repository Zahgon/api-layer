/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.caching.service.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;
import org.zowe.apiml.caching.model.KeyValue;
import org.zowe.apiml.caching.service.Messages;
import org.zowe.apiml.cache.Storage;
import org.zowe.apiml.cache.StorageException;
import org.zowe.apiml.caching.service.redis.exceptions.RedisOutOfMemoryException;
import org.zowe.apiml.caching.service.redis.exceptions.RetryableRedisException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class handles requests from controller and orchestrates operations on the low level RedisOperator class.
 * <p>
 * Storage eviction and maximum memory usage is left to Redis configuration. If an entry will take more than the maximum
 * configured memory, or there is not enough memory available and a no eviction policy is used, an error message is returned to the user.
 * If another entry will be evicted to make space for a create or update operation, no warning is logged and the eviction
 * is left to Redis.
 */
@Slf4j
public class RedisStorage implements Storage {

    private final RedisOperator redis;

    public RedisStorage(RedisOperator redisOperator) {
        log.info("Using Redis for the cached data");
        this.redis = redisOperator;
    }

    @Override
    @Retryable(value = RetryableRedisException.class)
    public KeyValue create(String serviceId, KeyValue toCreate) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public KeyValue storeMapItem(String serviceId, String mapKey, KeyValue toCreate) throws StorageException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Map<String, String> getAllMapItems(String serviceId, String mapKey) throws StorageException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Map<String, Map<String, String>> getAllMaps(String serviceId) throws StorageException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @Retryable(value = RetryableRedisException.class)
    public KeyValue read(String serviceId, String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @Retryable(value = RetryableRedisException.class)
    public KeyValue update(String serviceId, KeyValue toUpdate) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @Retryable(value = RetryableRedisException.class)
    public KeyValue delete(String serviceId, String toDelete) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @Retryable(value = RetryableRedisException.class)
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

    @Override
    public void removeNonRelevantRules(String serviceId, String mapKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
