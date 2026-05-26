/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.conformance;

import org.zowe.apiml.product.instance.ServiceAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.apache.commons.lang3.math.NumberUtils.isCreatable;

public abstract class AbstractSwaggerValidator {

    protected final Map<String, String> metadata;

    protected final ServiceAddress gatewayServiceAddress;

    protected final String serviceId;

    protected AbstractSwaggerValidator(Map<String, String> metadata, ServiceAddress gatewayServiceAddress, String serviceId) {
        this.metadata = metadata;
        this.gatewayServiceAddress = gatewayServiceAddress;
        this.serviceId = serviceId;
    }

    /**
     * Returns list of issues with compliance with the OpenAPI specification
     */
    abstract List<String> getMessages();

    /**
     * Returns list of all documented endpoints
     */
    abstract Set<Endpoint> getAllEndpoints();

    /**
     * Returns list of issues with the endpoint URLs, mainly regarding the versioning part of conformance criteria
     */
    public List<String> getProblemsWithEndpointUrls() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String searchMetadata(Map<String, String> metadata, String... partsOfKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean containsAll(String main, String... keywords) {
        for (String keyword : keywords) {
            if (!main.contains(keyword)) {
                return false;
            }
        }
        return true;
    }
}
