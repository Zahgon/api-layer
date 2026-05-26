/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zaas.security.login.saf;

import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.message.yaml.YamlMessageServiceInstance;
import org.zowe.apiml.product.logging.annotations.InjectApimlLogger;
import org.zowe.apiml.util.ClassOrDefaultProxyUtils;

public class SafPlatformClassFactory implements PlatformClassFactory {

    @InjectApimlLogger
    private final ApimlLogger apimlLog = ApimlLogger.of(SafPlatformClassFactory.class, YamlMessageServiceInstance.getInstance());

    @Override
    public Class<?> getPlatformUserClass() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Class<?> getPlatformReturnedClass() throws ClassNotFoundException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Object getPlatformUser() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
