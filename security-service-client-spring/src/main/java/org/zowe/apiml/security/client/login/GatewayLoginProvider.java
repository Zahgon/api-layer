/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.security.client.login;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.zowe.apiml.security.client.service.GatewaySecurity;
import org.zowe.apiml.security.common.login.LoginRequest;
import org.zowe.apiml.security.common.token.TokenAuthentication;
import java.util.Arrays;
import java.util.Optional;
import static org.zowe.apiml.security.SecurityUtils.readPassword;

/**
 * Authentication provider that authenticates UsernamePasswordAuthenticationToken against Gateway
 */
@Component
@RequiredArgsConstructor
@ConditionalOnMissingBean(name = "modulithConfig")
public class GatewayLoginProvider implements AuthenticationProvider {

    private final GatewaySecurity gatewaySecurity;

    /**
     * Authenticate the credentials
     *
     * @param authentication that was presented to the provider for validation
     * @return the authenticated token
     */
    @Override
    public Authentication authenticate(Authentication authentication) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean supports(Class<?> auth) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
