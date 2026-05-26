/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.product.logging;

import ch.qos.logback.core.rolling.RollingFileAppender;

/**
 * Appender which is conditional upon the debugging mode and provided location to store the files in.
 * The conditionality is checked on the start of the Appender to limit the overhead.
 */
public class // NOSONAR
ApimlRollingFileAppender<// NOSONAR
E> extends // NOSONAR
RollingFileAppender<E> {

    @Override
    public void start() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Verifies that the appender should be enabled and that there is a location to use within the zowe instance.
     * @return true if everything is ok, false otherwise.
     */
    protected boolean verifyStartupParams() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
