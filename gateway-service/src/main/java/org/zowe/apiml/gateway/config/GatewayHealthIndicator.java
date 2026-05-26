/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.product.compatibility.ApimlHealthCheckHandler;
import org.zowe.apiml.product.constants.CoreService;
import org.zowe.apiml.product.logging.annotations.InjectApimlLogger;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.springframework.boot.actuate.health.Status.DOWN;
import static org.springframework.boot.actuate.health.Status.UP;

/**
 * Gateway health information (/application/health)
 * This class contributes Gateway's information to {@link ApimlHealthCheckHandler}
 */
@Component
@ConditionalOnMissingBean(name = "modulithConfig")
public class GatewayHealthIndicator extends AbstractHealthIndicator {

    protected final DiscoveryClient discoveryClient;

    private final String apiCatalogServiceId;

    @InjectApimlLogger
    private final ApimlLogger apimlLog = ApimlLogger.empty();

    private AtomicBoolean startedInformationPublished = new AtomicBoolean(false);

    private AtomicBoolean applicationReady = new AtomicBoolean(false);

    public GatewayHealthIndicator(DiscoveryClient discoveryClient, @Value("${apiml.catalog.serviceId:}") String apiCatalogServiceId) {
        this.discoveryClient = discoveryClient;
        this.apiCatalogServiceId = apiCatalogServiceId;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent(ApplicationReadyEvent event) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void onFullyUp() {
        if (startedInformationPublished.compareAndSet(false, true)) {
            apimlLog.log("org.zowe.apiml.common.mediationLayerStarted");
        }
    }

    boolean isStartedInformationPublished() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Status toStatus(boolean up) {
        return up ? UP : DOWN;
    }
}
