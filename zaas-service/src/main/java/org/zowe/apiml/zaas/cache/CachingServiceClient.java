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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.zowe.apiml.product.gateway.GatewayClient;
import org.zowe.apiml.product.instance.ServiceAddress;
import java.util.Map;

/**
 * Client for interaction with Caching Service
 * Supports basic CRUD operations
 */
@Slf4j
// literals are repeating in debug logs only
@SuppressWarnings({ "squid:S1192" })
public class CachingServiceClient implements CachingClient {

    private final GatewayClient gatewayClient;

    private final RestTemplate restTemplate;

    @Value("${apiml.cachingServiceClient.apiPath:/cachingservice/api/v1/cache}")
    private String CACHING_API_PATH;

    @Value("${apiml.cachingServiceClient.list.apiPath:/cachingservice/api/v1/cache-list/}")
    private String CACHING_LIST_API_PATH;

    private static final HttpHeaders defaultHeaders = new HttpHeaders();

    static {
        defaultHeaders.add("Content-Type", "application/json");
    }

    public static HttpHeaders getDefaultHeaders() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public CachingServiceClient(RestTemplate restTemplate, GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
        if (restTemplate == null) {
            throw new IllegalStateException("RestTemplate instance cannot be null");
        }
        this.restTemplate = restTemplate;
    }

    private String getGatewayAddress() {
        ServiceAddress gatewayAddress = gatewayClient.getGatewayConfigProperties();
        if (gatewayAddress.getScheme() == null || gatewayAddress.getHostname() == null) {
            throw new IllegalStateException("zaasProtocolHostPort has to have value in format <protocol>://<host>:<port> and not be null");
        }
        return String.format("%s://%s", gatewayAddress.getScheme(), gatewayAddress.getHostname());
    }

    /**
     * Creates {@link KeyValue} in Caching Service.
     *
     * @param kv {@link KeyValue} to store
     * @throws CachingServiceClientException when http response from caching is not 2xx, such as connect exception or cache conflict
     */
    public void create(KeyValue kv) throws CachingServiceClientException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void appendList(String mapKey, KeyValue kv) throws CachingServiceClientException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Map<String, Map<String, String>> readAllMaps() throws CachingServiceClientException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Evict the non-relevant invalidated tokens by deleting the entries in the specified map
     *
     * @param key the map key
     */
    public void evictTokens(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Evict the non-relevant rules by deleting the entries in the specified map
     *
     * @param key the map key
     */
    public void evictRules(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Reads {@link KeyValue} from Caching Service
     *
     * @param key Key to read
     * @return {@link KeyValue}
     * @throws CachingServiceClientException when http response from caching is not 2xx, such as connect exception or 404 key not found in cache
     */
    public KeyValue read(String key) throws CachingServiceClientException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Updates {@link KeyValue} in Caching Service
     *
     * @param kv {@link KeyValue} to update
     * @throws CachingServiceClientException when http response from caching is not 2xx, such as connect exception or 404 key not found in cache
     */
    public void update(KeyValue kv) throws CachingServiceClientException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Deletes {@link KeyValue} from Caching Service
     *
     * @param key Key to delete
     * @throws CachingServiceClientException when http response from caching is not 2xx, such as connect exception or 404 key not found in cache
     */
    public void delete(String key) throws CachingServiceClientException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Data POJO that represents entry in caching service
     */
    @RequiredArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class KeyValue {

        private final String key;

        private final String value;

        @JsonCreator
        public KeyValue() {
            key = "";
            value = "";
        }
    }
}
