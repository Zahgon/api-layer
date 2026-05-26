/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.caching.service.inmemory;

import lombok.extern.slf4j.Slf4j;
import org.zowe.apiml.cache.StorageException;
import org.zowe.apiml.cache.Storage;
import org.zowe.apiml.caching.model.KeyValue;
import org.zowe.apiml.caching.service.*;
import org.zowe.apiml.caching.service.inmemory.config.InMemoryConfig;
import org.zowe.apiml.message.core.MessageService;
import org.zowe.apiml.message.log.ApimlLogger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class InMemoryStorage implements Storage {

    private Map<String, Map<String, KeyValue>> storage;

    private EvictionStrategy strategy = new DefaultEvictionStrategy();

    private InMemoryConfig config;

    public InMemoryStorage(InMemoryConfig inMemoryConfig, MessageService messageService) {
        this(inMemoryConfig, new ConcurrentHashMap<>(), ApimlLogger.of(RejectStrategy.class, messageService));
    }

    protected InMemoryStorage(InMemoryConfig inMemoryConfig, Map<String, Map<String, KeyValue>> storage, ApimlLogger apimlLogger) {
        this.storage = storage;
        this.config = inMemoryConfig;
        String evictionStrategy = inMemoryConfig.getGeneralConfig().getEvictionStrategy();
        if (evictionStrategy.equals(Strategies.REJECT.getKey())) {
            strategy = new RejectStrategy(apimlLogger);
        } else if (evictionStrategy.equals(Strategies.REMOVE_OLDEST.getKey())) {
            strategy = new RemoveOldestStrategy(storage);
        }
    }

    @Override
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
    public KeyValue read(String serviceId, String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public KeyValue update(String serviceId, KeyValue toUpdate) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public KeyValue delete(String serviceId, String key) {
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

    @Override
    public void removeNonRelevantRules(String serviceId, String mapKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean isKeyNotInCache(String serviceId, String keyToTest) {
        Map<String, KeyValue> serviceSpecificStorage = storage.get(serviceId);
        return serviceSpecificStorage == null || serviceSpecificStorage.get(keyToTest) == null;
    }

    private boolean aboveThreshold() {
        int currentSize = 0;
        for (Map.Entry<String, Map<String, KeyValue>> serviceStorage : storage.entrySet()) {
            currentSize += serviceStorage.getValue().size();
        }
        log.info("Current Size {}.", currentSize);
        return currentSize >= config.getGeneralConfig().getMaxDataSize();
    }
}
