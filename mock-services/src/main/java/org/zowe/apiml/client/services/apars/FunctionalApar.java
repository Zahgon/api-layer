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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.zowe.apiml.client.model.LoginBody;
import org.zowe.apiml.client.services.JwtTokenService;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings({ "squid:S1452", "squid:S1172" })
public class FunctionalApar implements Apar {

    private static final String COOKIE_HEADER = "cookie";

    private static final String JWT_TOKEN_NAME = "jwtToken";

    private static final String LTPA_TOKEN_NAME = "LtpaToken2";

    private static final String LTPA_TOKEN_VALUE = "paMypL7yRO/IBroQtro21/uSC2LTrJvOuYebHaPc6JAUNWQ7lEHHt1l3CYeXa/nP6aKLFHTuyWy3qlRXvt10PjVdVl+7Q+wavgIsro7odz+PvTaJBp/+r0AH+DHYcdZikKe8dytGYZRH2c2gw8Gv3PliDIMd1iPEazY4HeYTU5VCFM5cBJkeIoTXCfL5ud9wTzrkY2c4h1PQPtx+hYCF4kEpiVkqIypVwjQLzWdJGV1Ihz7NqH/UU9MMJRXY1xMqsWZSibs2fX5MVK77dnyBrNYjVXA7PqYL6U/v5/1UCvuYQ/iEU9+Uy95J+xFEsnTX";

    protected static final String AUTHORIZATION_HEADER = "authorization";

    private final List<String> usernames;

    protected List<String> passwords;

    private JwtTokenService jwtTokenService;

    protected FunctionalApar(List<String> usernames, List<String> passwords) {
        this(usernames, passwords, new JwtTokenService(60));
    }

    protected FunctionalApar(List<String> usernames, List<String> passwords, JwtTokenService tokenService) {
        this.usernames = usernames;
        this.passwords = passwords;
        this.jwtTokenService = tokenService;
    }

    @Override
    public Optional<ResponseEntity<?>> apply(Object... parameters) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Override to provide response entity when the JWT keys are requested from the zOSMF
     */
    protected ResponseEntity<?> handleJwtKeys() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Override to provide a response entity, or set fields (like cookies) in the HTTP response when the create method
     * for the authentication service is called with proper authorization.
     */
    protected ResponseEntity<?> handleAuthenticationCreate(Map<String, String> headers, HttpServletResponse response) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Override to provide a response entity, or set fields (like cookies) in the HTTP response when the verify method
     * for the authentication service is called with proper authorization.
     */
    protected ResponseEntity<?> handleAuthenticationVerify(Map<String, String> headers, HttpServletResponse response) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Override to provide a response entity when the delete method for the authentication service is called
     * with proper authorization.
     */
    protected ResponseEntity<?> handleAuthenticationDelete(Map<String, String> headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Override to provide a response entity when the authentication service with proper authorization and the method
     * is not explicitly handled.
     */
    protected ResponseEntity<?> handleAuthenticationDefault(Map<String, String> headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Override to provide a response entity when the update method for the authentication service is called
     * with proper authorization.
     */
    protected ResponseEntity<?> handleChangePassword(LoginBody body) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Override to provide a response entity when the information service is called with proper authorization.
     */
    protected ResponseEntity<?> handleInformation(Map<String, String> headers, HttpServletResponse response) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Override to provide a response entity when the files service is called with proper authorization.
     */
    protected ResponseEntity<?> handleFiles(Map<String, String> headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected boolean noAuthentication(Map<String, String> headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected boolean containsInvalidOrNoUser(Map<String, String> headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private String getAuthorizationHeader(Map<String, String> headers) {
        return headers.get(AUTHORIZATION_HEADER) != null ? headers.get(AUTHORIZATION_HEADER) : headers.get(HttpHeaders.AUTHORIZATION);
    }

    protected String[] getPiecesOfCredentials(Map<String, String> headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected boolean ltpaIsPresent(Map<String, String> headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected boolean validLtpaCookie(Map<String, String> headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected boolean isValidJwtCookie(Map<String, String> headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected boolean isValidAuthHeader(String authHeader) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isValidTokenInAuthHeader(String authHeader) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private String getAuthCookie(Map<String, String> headers) {
        return headers.get(COOKIE_HEADER) != null ? headers.get(COOKIE_HEADER) : headers.get(HttpHeaders.COOKIE);
    }

    protected void setLtpaToken(HttpServletResponse response) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected ResponseEntity<?> validJwtResponse(HttpServletResponse response, String username, String keystorePath) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
