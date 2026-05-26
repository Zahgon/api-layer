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

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.zowe.apiml.product.gateway.GatewayClient;
import reactor.core.publisher.Mono;
import static reactor.core.publisher.Mono.empty;
import static reactor.core.publisher.Mono.error;

@Component
@Slf4j
@ConditionalOnMissingBean(name = "modulithConfig")
public class CachingServiceClientRest implements CachingServiceClient {

    private static final String CACHING_SERVICE_RETURNED = ". Caching service returned: ";

    @Value("${apiml.cachingServiceClient.apiPath:/cachingservice/api/v1/cache}")
    private String CACHING_API_PATH;

    private volatile String cachingBalancerUrl;

    private final GatewayClient gatewayClient;

    private static final MultiValueMap<String, String> defaultHeaders = new LinkedMultiValueMap<>();

    static {
        defaultHeaders.add("Content-Type", "application/json");
    }

    private final WebClient webClient;

    public CachingServiceClientRest(@Qualifier("webClientClientCert") WebClient webClientClientCert, GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
        this.webClient = webClientClientCert;
    }

    void updateUrl() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Mono<Void> create(ApiKeyValue keyValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Mono<Void> update(ApiKeyValue keyValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Mono<ApiKeyValue> read(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Deletes {@link ApiKeyValue} from Caching Service
     *
     * @param key Key to delete
     * @return mono with status success / error
     */
    public Mono<Void> delete(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
