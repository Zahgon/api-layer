/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zaas.security.mapping.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zowe.apiml.eurekaservice.client.util.EurekaMetadataParser;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.message.yaml.YamlMessageServiceInstance;
import org.zowe.apiml.product.logging.annotations.InjectApimlLogger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapperResponse {

    public static final String OIDC_FAILED_MESSAGE_KEY = "org.zowe.apiml.security.common.OIDCMappingFailed";

    @JsonProperty("userid")
    private String userId;

    @JsonProperty("returnCode")
    private int rc;

    @JsonProperty("safReturnCode")
    private int safRc;

    @JsonProperty("racfReturnCode")
    private int racfRc;

    @JsonProperty("racfReasonCode")
    private int racfRs;

    @InjectApimlLogger
    private final ApimlLogger apimlLog = ApimlLogger.of(EurekaMetadataParser.class, YamlMessageServiceInstance.getInstance());

    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isOIDCResultValid() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
