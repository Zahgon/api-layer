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

import org.zowe.apiml.security.common.auth.saf.PlatformReturned;
import org.zowe.apiml.security.common.error.PlatformPwdErrno;

public class MockPlatformUser implements PlatformUser {

    public static final String VALID_USERID = "USER";

    public static final String VALID_PASSWORD = "validPassword";

    public static final String EXPIRED_PASSWORD = "expiredPassword";

    public static final String INVALID_USERID = "notuser";

    //NOSONAR
    public static final String INVALID_PASSWORD = "notuser";

    @Override
    public PlatformReturned authenticate(String userid, String password) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Object changePassword(String userid, String password, String newPassword) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
