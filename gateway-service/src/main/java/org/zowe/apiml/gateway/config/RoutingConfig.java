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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class RoutingConfig {

    @Value("${apiml.service.ignoredHeadersWhenCorsEnabled:}")
    private String ignoredHeadersWhenCorsEnabled;

    @Value("${apiml.security.x509.acceptForwardedCert:false}")
    private boolean acceptForwardedCert;

    @Value("${apiml.service.allowEncodedSlashes:true}")
    private boolean allowEncodedSlashes;

    @Bean
    public List<FilterDefinition> commonNoRetryFilters() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    public List<FilterDefinition> commonFilters(List<FilterDefinition> commonNoRetryFilters) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
