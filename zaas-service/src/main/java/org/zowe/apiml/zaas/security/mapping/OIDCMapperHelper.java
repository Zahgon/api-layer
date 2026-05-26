/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zaas.security.mapping;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.zowe.apiml.message.core.MessageType;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.product.logging.annotations.InjectApimlLogger;
import org.zowe.apiml.zaas.security.service.schema.source.AuthSource;
import org.zowe.apiml.zaas.security.service.schema.source.OIDCAuthSource;
import java.util.function.UnaryOperator;
import static org.zowe.apiml.zaas.security.mapping.model.MapperResponse.OIDC_FAILED_MESSAGE_KEY;

@Component
@ConditionalOnBean(name = "oidcMapper")
public class OIDCMapperHelper implements InitializingBean {

    @Value("${apiml.security.oidc.registry:}")
    protected String registry;

    @InjectApimlLogger
    private final ApimlLogger apimlLog = ApimlLogger.empty();

    protected boolean isConfigError = false;

    @Override
    public void afterPropertiesSet() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Maps the authSource distribution id to a mainframe user. The method validates OIDC mapping configuration and authSource and if these are valid, invokes the mapper.
     * @param authSource OidcAuthSource with the distributed id to map
     * @param mapper the mapper function with the actual mapping logic, accepts the authSource distributed id and returns a mainframe user id on success or null otherwise
     * @return returns result of the mapper or null on validation failure
     */
    public String mapToMainframeUserId(AuthSource authSource, UnaryOperator<String> mapper) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
