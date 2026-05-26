/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.client;

import org.springframework.context.annotation.Import;
import org.zowe.apiml.enable.EnableApiDiscovery;
import org.zowe.apiml.product.logging.annotations.EnableApimlLogger;
import org.zowe.apiml.product.monitoring.LatencyUtilsConfigInitializer;
import org.zowe.apiml.product.service.ServiceStartupEventHandler;
import org.zowe.apiml.product.version.BuildInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.zowe.apiml.product.web.ApimlTomcatCustomizer;
import org.zowe.apiml.product.web.TomcatAcceptFixConfig;
import org.zowe.apiml.product.web.TomcatKeyringFix;

@SpringBootApplication
@EnableApiDiscovery
@EnableWebSocket
@EnableApimlLogger
@RequiredArgsConstructor
@Import({ TomcatKeyringFix.class, TomcatAcceptFixConfig.class, ApimlTomcatCustomizer.class })
public class DiscoverableClientSampleApplication implements ApplicationListener<ApplicationReadyEvent> {

    private final ServiceStartupEventHandler handler;

    public static void main(String[] args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
