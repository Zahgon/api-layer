/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.security.client.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.zowe.apiml.product.gateway.GatewayNotAvailableException;
import org.zowe.apiml.security.common.auth.saf.PlatformReturned;
import org.zowe.apiml.security.common.error.AuthMethodNotSupportedException;
import org.zowe.apiml.security.common.error.ErrorType;
import org.zowe.apiml.security.common.error.ServiceNotAccessibleException;
import org.zowe.apiml.security.common.error.ZosAuthenticationException;
import org.zowe.apiml.security.common.token.InvalidTokenTypeException;
import org.zowe.apiml.security.common.token.NoMainframeIdentityException;
import org.zowe.apiml.security.common.token.TokenNotProvidedException;
import org.zowe.apiml.security.common.token.TokenNotValidException;

/**
 * Handler for exceptions that are thrown during the security client rest calls
 */
@Slf4j
@Component
public class RestResponseHandler {

    public void handleErrorType(HttpResponse response, ErrorType errorType, Object... logParameters) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void handleException(Exception exception) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void addDebugMessage(Exception exception, String genericLogErrorMessage, Object... logParameters) {
        if (genericLogErrorMessage != null) {
            if (logParameters.length > 0) {
                log.debug(genericLogErrorMessage, logParameters);
            } else {
                log.debug(genericLogErrorMessage, exception);
            }
        }
    }
}
