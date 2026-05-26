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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.zowe.apiml.product.instance.ServiceAddress;
import java.util.Map;

@UtilityClass
public class ValidatorFactory {

    private static final String NON_CONFORMANT_KEY = "org.zowe.apiml.gateway.verifier.nonConformant";

    public AbstractSwaggerValidator parseSwagger(String swaggerDoc, Map<String, String> metadata, ServiceAddress gatewayServiceAddress, String serviceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
