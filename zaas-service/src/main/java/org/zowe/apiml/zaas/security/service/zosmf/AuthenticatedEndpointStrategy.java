/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zaas.security.service.zosmf;

import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.product.logging.annotations.InjectApimlLogger;
import org.zowe.apiml.security.common.error.ServiceNotAccessibleException;
import static org.zowe.apiml.zaas.security.service.zosmf.AbstractZosmfService.ZOSMF_CSRF_HEADER;

/**
 * Strategy to validate token through Authentication endpoint of zOSMF
 */
@RequiredArgsConstructor
public class AuthenticatedEndpointStrategy implements TokenValidationStrategy {

    private final RestTemplate restTemplateWithoutKeystore;

    @InjectApimlLogger
    protected ApimlLogger apimlLog = ApimlLogger.empty();

    public final String authenticatedEndpoint;

    private final HttpMethod httpMethod;

    @Override
    public void validate(TokenValidationRequest request) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean endpointExists(TokenValidationRequest request, String endpoint) {
        if (request.getEndpointExistenceMap() == null || request.getEndpointExistenceMap().isEmpty()) {
            return true;
        } else {
            return request.getEndpointExistenceMap().entrySet().stream().filter(entry -> entry.getKey().equalsIgnoreCase(request.getZosmfBaseUrl() + endpoint)).findFirst().map(Entry::getValue).orElse(true);
        }
    }

    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
