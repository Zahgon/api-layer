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

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisCommandExecutionException;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.masterreplica.MasterReplica;
import io.lettuce.core.masterreplica.StatefulRedisMasterReplicaConnection;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.zowe.apiml.caching.model.KeyValue;
import org.zowe.apiml.caching.service.redis.exceptions.RedisEntryException;
import org.zowe.apiml.caching.service.redis.exceptions.RedisOutOfMemoryException;
import org.zowe.apiml.caching.service.redis.exceptions.RetryableRedisException;
import org.zowe.apiml.message.log.ApimlLogger;
import jakarta.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Class used to connect to and operate on a Redis instance or cluster.
 * Contains the CRUD operations enacted on Redis with serialized read and write.
 */
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Component
@ConditionalOnProperty(name = "caching.storage.mode", havingValue = "redis")
public class RedisOperator {

    private RedisClient redisClient;

    private StatefulRedisMasterReplicaConnection<String, String> redisConnection;

    private RedisAsyncCommands<String, String> redis;

    public RedisOperator(RedisClient redisClient, RedisURI redisUri, ApimlLogger apimlLog) {
        try {
            this.redisClient = redisClient;
            redisConnection = MasterReplica.connect(this.redisClient, StringCodec.UTF8, redisUri);
            redis = redisConnection.async();
            log.info("Connected to Redis {}", redisUri);
        } catch (Exception e) {
            apimlLog.log("org.zowe.apiml.cache.errorInitializingStorage", "redis", e.getCause().getMessage(), e);
            System.exit(1);
        }
    }

    @PreDestroy
    public void closeConnection() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates a given entry in Redis.
     *
     * @param entryToAdd RedisEntry containing the service ID for which to create the entry, and the key and value.
     * @return true if the key does not exist for the service ID and the entry was created, otherwise false.
     */
    public boolean create(RedisEntry entryToAdd) throws RedisOutOfMemoryException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Updates a given entry in Redis.
     *
     * @param entryToUpdate RedisEntry containing the service ID and key to update, with the new value.
     * @return true if the key exists for a service ID and the value was updated, otherwise false.
     */
    public boolean update(RedisEntry entryToUpdate) throws RedisOutOfMemoryException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieve an entry for a given service with the corresponding key.
     *
     * @return RedisEntry instance if the service ID and key exist, otherwise null.
     */
    public RedisEntry get(String serviceId, String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieves all entries for a given service.
     *
     * @return List of RedisEntry instances. If there are no entries an empty List is returned.
     */
    public List<RedisEntry> get(String serviceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private List<RedisEntry> collectEntries(String serviceId, Map<String, String> redisEntries) {
        List<RedisEntry> entries = new ArrayList<>();
        for (Map.Entry<String, String> entry : redisEntries.entrySet()) {
            try {
                entries.add(new RedisEntry(serviceId, entry.getValue()));
            } catch (RedisEntryException e) {
                log.warn("Error retrieving entry: {}|{}. Error: {}", serviceId, entry, e.getMessage());
            }
        }
        return entries;
    }

    /**
     * Deletes all entries with the given key for a given service.
     *
     * @return true if at least one entry was deleted, otherwise false.
     */
    public boolean delete(String serviceId, String toDelete) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Deletes all entries for a given service.
     *
     * @return true if at least one entry was deleted, otherwise false.
     */
    public boolean delete(String serviceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void handleWriteOperationExecutionException(ExecutionException e) throws RedisOutOfMemoryException {
        Throwable cause = e.getCause();
        if (cause instanceof RedisCommandExecutionException && cause.getMessage().contains("maxmemory")) {
            throw new RedisOutOfMemoryException(cause);
        } else {
            throw new RetryableRedisException(e);
        }
    }

    private void handleInterruptedException(InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RetryableRedisException(e);
    }
}
