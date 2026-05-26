/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zss.services;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.zowe.apiml.zss.model.MapperResponse;
import org.zowe.apiml.zss.model.OIDCRequest;
import org.zowe.apiml.zss.model.ZssResponse;
import java.util.Map;

@Service
@ConfigurationProperties(prefix = "zss")
@Setter
public class OIDCProvider {

    private Map<String, String> userMapping;

    public MapperResponse mapUserIdentity(OIDCRequest oidcRequest) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MapperResponse setCustomResponse(ZssResponse.ZssError zssError) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public HttpStatus setCustomStatus(int statusCode) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
