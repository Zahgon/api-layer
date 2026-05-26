/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.caching.service.vsam;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;
import org.zowe.apiml.caching.model.KeyValue;
import org.zowe.apiml.caching.service.EvictionStrategy;
import org.zowe.apiml.caching.service.Messages;
import org.zowe.apiml.cache.Storage;
import org.zowe.apiml.cache.StorageException;
import org.zowe.apiml.caching.service.vsam.config.VsamConfig;
import org.zowe.apiml.message.log.ApimlLogger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Class handles requests from controller and orchestrates operations on the low level VSAM File class
 */
@Slf4j
public class VsamStorage implements Storage {

    private VsamConfig vsamConfig;

    private EvictionStrategyProducer evictionStrategyProducer;

    private VsamFileProducer producer = new VsamFileProducer();

    private ApimlLogger apimlLog;

    public VsamStorage(VsamConfig vsamConfig, VsamInitializer vsamInitializer, ApimlLogger apimlLog, EvictionStrategyProducer evictionStrategyProducer) {
        log.info("Using VSAM storage for the cached data");
        this.apimlLog = apimlLog;
        String vsamFileName = vsamConfig.getFileName();
        if (vsamFileName == null || vsamFileName.isEmpty()) {
            apimlLog.log("org.zowe.apiml.cache.errorInitializingStorage", "vsam", "wrong Configuration", "VSAM Filename must be valid");
            throw new IllegalArgumentException("Vsam filename must be valid");
        }
        this.vsamConfig = vsamConfig;
        this.evictionStrategyProducer = evictionStrategyProducer;
        log.info("Using Vsam configuration: {}", vsamConfig);
        vsamInitializer.storageWarmup(vsamConfig, apimlLog);
    }

    public VsamStorage(VsamConfig vsamConfig, VsamInitializer vsamInitializer, VsamFileProducer producer, ApimlLogger apimlLogger, EvictionStrategyProducer evictionStrategyProducer) {
        this(vsamConfig, vsamInitializer, apimlLogger, evictionStrategyProducer);
        this.producer = producer;
    }

    private EvictionStrategy provideStrategy(VsamFile file) {
        return evictionStrategyProducer.evictionStrategy(file);
    }

    @Override
    @Retryable(value = { RetryableVsamException.class, IllegalStateException.class, UnsupportedOperationException.class })
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

    private boolean aboveThreshold(int currentSize) {
        return currentSize >= vsamConfig.getGeneralConfig().getMaxDataSize();
    }

    @Override
    @Retryable(value = { RetryableVsamException.class })
    public KeyValue read(String serviceId, String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @Retryable(value = { RetryableVsamException.class, IllegalStateException.class, UnsupportedOperationException.class })
    public KeyValue update(String serviceId, KeyValue toUpdate) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @Retryable(value = { RetryableVsamException.class, IllegalStateException.class, UnsupportedOperationException.class })
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

    @Override
    public void removeNonRelevantRules(String serviceId, String mapKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
