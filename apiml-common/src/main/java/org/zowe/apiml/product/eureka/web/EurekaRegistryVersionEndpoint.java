/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.product.eureka.web;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaEvent;
import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
@Endpoint(id = "eurekaversion")
@ConditionalOnMissingBean(name = "modulithConfig")
@Slf4j
public class EurekaRegistryVersionEndpoint {

    private static final Pattern VERSION_PATTERN = Pattern.compile("UP_(\\d+)_");

    private Long version = -1L;

    private final EurekaClient eurekaClient;

    @PostConstruct
    void registerListener() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @EventListener
    void onRegistryUpdate(EurekaEvent event) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ReadOperation(produces = APPLICATION_JSON)
    public VersionDto status() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Builder
    @Value
    static class VersionDto {

        private Long version;
    }
}
