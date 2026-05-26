/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.zowe.apiml.security.client.service.GatewaySecurity;
import org.zowe.apiml.security.common.login.LoginRequest;
import org.zowe.apiml.security.common.token.*;
import org.zowe.apiml.zaas.security.config.CompoundAuthProvider;
import org.zowe.apiml.zaas.security.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;
import static org.zowe.apiml.security.common.error.ErrorType.TOKEN_NOT_VALID;

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class GatewaySecurityApi implements GatewaySecurity {

    private final CompoundAuthProvider compoundAuthProvider;

    private final AuthenticationService authenticationService;

    @Nullable
    private final OIDCProvider oidcProvider;

    @Override
    public Optional<String> login(String username, char[] password, char[] newPassword) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public QueryResponse query(String token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public TokenAuthentication verifyOidc(String token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
