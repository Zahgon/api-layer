/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.enable.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.zowe.apiml.eurekaservice.client.ApiMediationClient;
import org.zowe.apiml.eurekaservice.client.EurekaClientConfigProvider;
import org.zowe.apiml.eurekaservice.client.EurekaClientProvider;
import org.zowe.apiml.eurekaservice.client.config.ApiMediationServiceConfig;
import org.zowe.apiml.eurekaservice.client.impl.ApiMediationClientImpl;
import org.zowe.apiml.message.core.MessageService;
import org.zowe.apiml.message.yaml.YamlMessageServiceInstance;
import org.zowe.apiml.product.logging.annotations.EnableApimlLogger;

@Configuration
@ComponentScan(value = { "org.zowe.apiml.enable" })
@EnableApimlLogger
public class EnableApiDiscoveryConfig {

    @Bean
    @ConditionalOnMissingBean
    public MessageService messageServiceDiscovery() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean("apiMediationClient")
    public ApiMediationClient apiMediationClient(ObjectProvider<EurekaClientProvider> clientProvider, ObjectProvider<EurekaClientConfigProvider> clientConfigProvider) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ConfigurationProperties(prefix = "apiml.service", ignoreInvalidFields = true)
    @Bean
    public ApiMediationServiceConfig apiMediationServiceConfig() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
