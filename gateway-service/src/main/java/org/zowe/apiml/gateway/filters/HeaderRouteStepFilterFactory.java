/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.filters;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Service;

/**
 * This filter is responsible to update request header during the routing. The header contain a locator to which server
 * route. It could contain also multiple steps separated by /.
 *
 * In the case header contain multiple steps the filter remove just the first part, otherwise it remove header at all.
 *
 * Examples:
 *      "step1/step2/step3" > "step2/step3"
 *      "node"              > null (removed)
 */
@Service
public class HeaderRouteStepFilterFactory extends AbstractGatewayFilterFactory<HeaderRouteStepFilterFactory.Config> {

    public HeaderRouteStepFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Data
    public static class Config {

        private String header;
    }
}
