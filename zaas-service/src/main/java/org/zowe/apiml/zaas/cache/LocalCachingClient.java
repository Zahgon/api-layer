/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zaas.cache;

import lombok.RequiredArgsConstructor;
import org.zowe.apiml.cache.Storage;
import org.zowe.apiml.caching.model.KeyValue;
import org.zowe.apiml.security.HttpsConfig;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class LocalCachingClient implements CachingClient {

    private final Storage storage;

    private final HttpsConfig httpsConfig;

    @Override
    public void create(CachingServiceClient.KeyValue kv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void appendList(String mapKey, CachingServiceClient.KeyValue kv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Map<String, Map<String, String>> readAllMaps() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void evictTokens(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void evictRules(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CachingServiceClient.KeyValue read(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void update(CachingServiceClient.KeyValue kv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void delete(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    KeyValue convert(CachingServiceClient.KeyValue kv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    CachingServiceClient.KeyValue convert(KeyValue kv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String getServiceId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
