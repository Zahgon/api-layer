/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.product.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.message.yaml.YamlMessageServiceInstance;
import java.lang.management.ManagementFactory;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@NoArgsConstructor
public class ServiceStartupEventHandler {

    public static final int DEFAULT_DELAY_FACTOR = 5;

    private final ApimlLogger apimlLog = ApimlLogger.of(ServiceStartupEventHandler.class, YamlMessageServiceInstance.getInstance());

    private final Set<String> registeredServices = ConcurrentHashMap.newKeySet();

    @SuppressWarnings("squid:S1172")
    public void onServiceStartup(String serviceName, int delayFactor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
