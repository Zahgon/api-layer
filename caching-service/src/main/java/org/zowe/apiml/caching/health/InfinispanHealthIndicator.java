/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.caching.health;

import org.apache.commons.lang3.StringUtils;
import org.infinispan.remoting.transport.Address;
import org.infinispan.spring.embedded.provider.SpringEmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.zowe.apiml.caching.service.infinispan.config.LazyCacheManager;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Component
@ConditionalOnProperty(name = "caching.storage.mode", havingValue = "infinispan")
public class InfinispanHealthIndicator extends AbstractHealthIndicator {

    @Value("${caching.storage.infinispan.initialHosts:}")
    private String initialHosts;

    private final AtomicReference<CacheManager> cacheManager = new AtomicReference<>();

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
