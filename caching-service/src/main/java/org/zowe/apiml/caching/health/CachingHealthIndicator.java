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

import com.netflix.discovery.shared.Application;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.zowe.apiml.eurekaservice.client.ApiMediationClient;
import org.zowe.apiml.product.constants.CoreService;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Caching service health information (/cachingservice/application/health)
 */
@Component
@RequiredArgsConstructor
@ConditionalOnMissingBean(name = "modulithConfig")
public class CachingHealthIndicator extends AbstractHealthIndicator implements ApplicationListener<ApplicationReadyEvent> {

    private final AtomicBoolean serviceUp = new AtomicBoolean();

    private final ApiMediationClient apiMediationClient;

    private final Optional<InfinispanHealthIndicator> infinispanHealthIndicator;

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onApplicationEvent(@Nonnull final ApplicationReadyEvent event) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
