/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.eurekaservice.client.config;

import com.netflix.discovery.DefaultEurekaClientConfig;
import java.util.List;

public class EurekaClientConfiguration extends DefaultEurekaClientConfig {

    private static final int DEFAULT_RENEWAL_INTERVAL = 30;

    private final ApiMediationServiceConfig config;

    public EurekaClientConfiguration(ApiMediationServiceConfig config) {
        this.config = config;
    }

    protected ApiMediationServiceConfig getConfig() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean shouldRegisterWithEureka() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getDecoderName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getRegion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean shouldUseDnsForFetchingServiceUrls() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public List<String> getEurekaServerServiceUrls(String s) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean shouldOnDemandUpdateStatusChange() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getRegistryFetchIntervalSeconds() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getEurekaServerConnectTimeoutSeconds() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getEurekaServerReadTimeoutSeconds() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
