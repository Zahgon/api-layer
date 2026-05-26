/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zaas.security.service.token;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWKException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.http.HttpHeaders;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.zowe.apiml.constants.ApimlConstants;
import org.zowe.apiml.security.common.token.OIDCProvider;
import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.time.Clock;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Slf4j
@ConditionalOnExpression("'${apiml.security.oidc.enabled:false}' == 'true'")
public class OIDCTokenProvider implements OIDCProvider {

    @Value("${apiml.security.oidc.jwks.uri}")
    private List<String> jwksUri;

    @Value("${apiml.security.oidc.jwks.refreshInternalHours:1}")
    private int jwkRefreshInterval;

    @Qualifier("oidcJwtClock")
    private final Clock clock;

    @Value("${apiml.security.oidc.userInfo.uri}")
    private String endpointUrl;

    private final JWKResolver jwkResolver;

    private final CloseableHttpClient secureHttpClientWithKeystore;

    @Getter
    private final Map<String, JsonWebKey> publicKeys = new ConcurrentHashMap<>();

    @Getter
    private JsonWebKeySet jwkSet;

    @PostConstruct
    public void afterPropertiesSet() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Retryable
    void fetchJWKSet() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isValid(String token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isValidExternal(String token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    JWTClaimsSet getClaims(String token) throws ParseException, BadJOSEException, JOSEException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private JWTClaimsSet getClaims(SignedJWT jwt) throws JOSEException, ParseException, BadJOSEException {
        var keyId = jwt.getHeader().getKeyID();
        if (StringUtils.isBlank(keyId)) {
            throw new JWKException("Token does not provide kid. It uses an unsupported type of signature.");
        }
        var jsonWebKey = publicKeys.get(keyId);
        if (jsonWebKey != null) {
            var rsaVerifier = new RSASSAVerifier((RSAPublicKey) jsonWebKey.getKey());
            var verified = jwt.verify(rsaVerifier);
            if (verified) {
                var claims = jwt.getJWTClaimsSet();
                if (claims.getExpirationTime().toInstant().isBefore(clock.instant())) {
                    log.debug("OIDC Token is expired");
                    return null;
                }
                return claims;
            } else {
                throw new BadJOSEException("Provided OIDC JWT token has invalid signature");
            }
        } else {
            throw new JWKException("Key with id " + keyId + " is null in JWK");
        }
    }
}
