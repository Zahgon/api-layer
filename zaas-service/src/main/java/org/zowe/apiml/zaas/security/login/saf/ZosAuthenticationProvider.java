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

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.zowe.apiml.zaas.security.login.LoginProvider;
import org.zowe.apiml.zaas.security.service.AuthenticationService;
import org.zowe.apiml.security.common.auth.saf.PlatformReturned;
import org.zowe.apiml.security.common.error.ZosAuthenticationException;
import org.zowe.apiml.security.common.login.LoginRequest;

@Component
@ConditionalOnExpression("#{('${apiml.security.auth.provider:zosmf}' == 'zosmf') or ('${apiml.security.auth.provider:zosmf}' == 'dummy') or ('${apiml.security.auth.provider:zosmf}' == 'saf')}")
public class ZosAuthenticationProvider implements AuthenticationProvider, InitializingBean {

    private PlatformUser platformUser = null;

    private final String authenticationProvider;

    private final AuthenticationService authenticationService;

    public ZosAuthenticationProvider(AuthenticationService authenticationService, @Value("${apiml.security.auth.provider}") String authenticationProvider) {
        this.authenticationService = authenticationService;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private PlatformUser getPlatformUser() {
        return platformUser;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void afterPropertiesSet() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
