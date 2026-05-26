/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.client.services;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class JwtTokenService {

    public static final String JWT_TOKEN = "jwtToken=";

    public static final String LTPA_TOKEN = "LtpaToken2=";

    private Set<String> invalidatedTokens = new HashSet<>();

    private int expirationSeconds;

    public JwtTokenService(int expirationSeconds) {
        this.expirationSeconds = expirationSeconds;
    }

    public String generateJwt(String user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean validateJwtToken(String token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void invalidateJwtToken(String token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean containsToken(String token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static JWKSet getKeySet() throws NoSuchAlgorithmException, InvalidKeySpecException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static JWK loadJWK(RSAPublicKey publicKey, String kid) {
        RSAKey rsaKey = new RSAKey.Builder(publicKey).keyID(kid).build();
        return rsaKey.toPublicJWK();
    }

    public static RSAPrivateKey readPemPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static RSAPublicKey readPemPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static RSAPublicKey readAnotherPemPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static RSAPublicKey getRsaPublicKey(String pemKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String publicKeyPEM = pemKey.replace("-----BEGIN PUBLIC KEY-----", "").replaceAll("\n", //NOSONAR
        "").replace("-----END PUBLIC KEY-----", "");
        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public String extractToken(Map<String, String> headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String extractLtpaToken(Map<String, String> headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Optional<String> getTokenFromTheStart(Map<String, String> headers) {
        return headers.entrySet().stream().filter(e -> e.getKey().equalsIgnoreCase("cookie") && e.getValue().startsWith(JWT_TOKEN)).map(Map.Entry::getValue).map(s -> s.replaceFirst(JWT_TOKEN, "")).findFirst();
    }

    private Optional<String> getTokenFromTheMiddle(Map<String, String> headers) {
        return headers.entrySet().stream().filter(e -> e.getKey().equalsIgnoreCase("cookie") && e.getValue().startsWith(LTPA_TOKEN)).map(Map.Entry::getValue).map(s -> s.substring(s.indexOf(JWT_TOKEN) + JWT_TOKEN.length())).findFirst();
    }

    private Optional<String> getLtpaToken(Map<String, String> headers) {
        return headers.entrySet().stream().filter(e -> e.getKey().equalsIgnoreCase("cookie") && e.getValue().startsWith(LTPA_TOKEN)).map(Map.Entry::getValue).map(s -> s.substring(s.indexOf(LTPA_TOKEN) + LTPA_TOKEN.length())).findFirst();
    }
}
