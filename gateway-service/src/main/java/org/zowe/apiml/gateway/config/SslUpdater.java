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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.server.Ssl;
import org.springframework.stereotype.Component;
import org.zowe.apiml.security.SecurityUtils;

@Component
public class SslUpdater implements BeanPostProcessor {

    private static final String KEYRING_PASSWORD = "password";

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
