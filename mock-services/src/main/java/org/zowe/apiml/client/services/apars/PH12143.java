/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.client.services.apars;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.zowe.apiml.client.services.JwtTokenService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@SuppressWarnings("squid:S1452")
public class PH12143 extends FunctionalApar {

    private final String keystorePath;

    public PH12143(List<String> usernames, List<String> passwords, String keystorePath, Integer timeout) {
        super(usernames, passwords, new JwtTokenService(timeout));
        this.keystorePath = keystorePath;
    }

    @Override
    protected ResponseEntity<?> handleAuthenticationCreate(Map<String, String> headers, HttpServletResponse response) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected ResponseEntity<?> handleAuthenticationVerify(Map<String, String> headers, HttpServletResponse response) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected ResponseEntity<?> handleAuthenticationDelete(Map<String, String> headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected ResponseEntity<?> handleJwtKeys() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
