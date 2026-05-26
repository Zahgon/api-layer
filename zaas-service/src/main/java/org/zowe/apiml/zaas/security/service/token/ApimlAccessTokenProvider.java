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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.zowe.apiml.cache.StorageException;
import org.zowe.apiml.models.AccessTokenContainer;
import org.zowe.apiml.security.common.token.AccessTokenProvider;
import org.zowe.apiml.security.common.token.QueryResponse;
import org.zowe.apiml.zaas.cache.CachingClient;
import org.zowe.apiml.zaas.cache.CachingServiceClient;
import org.zowe.apiml.zaas.cache.CachingServiceClientException;
import org.zowe.apiml.zaas.security.service.AuthenticationService;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApimlAccessTokenProvider implements AccessTokenProvider {

    static final String INVALID_TOKENS_KEY = "invalidTokens";

    static final String INVALID_USERS_KEY = "invalidUsers";

    static final String INVALID_SCOPES_KEY = "invalidScopes";

    private final CachingClient cachingServiceClient;

    private final AuthenticationService authenticationService;

    @Qualifier("oidcJwkMapper")
    private final ObjectMapper objectMapper;

    public void invalidateToken(String token) throws CachingServiceClientException, JsonProcessingException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void invalidateAllTokensForUser(String userId, long timestamp) throws CachingServiceClientException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void invalidateAllTokensForService(String serviceId, long timestamp) throws CachingServiceClientException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isInvalidated(String token) throws CachingServiceClientException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Optional<Boolean> checkInvalidToken(Map<String, String> invalidTokens, String tokenId) {
        if (invalidTokens != null && !invalidTokens.isEmpty() && invalidTokens.containsKey(tokenId)) {
            String s = invalidTokens.get(tokenId);
            try {
                AccessTokenContainer c = objectMapper.readValue(s, AccessTokenContainer.class);
                return Optional.of(c != null);
            } catch (JsonProcessingException e) {
                log.error("Not able to parse invalidToken json value.", e);
            }
        }
        return Optional.empty();
    }

    private Optional<Boolean> checkRule(Map<String, String> tokenRules, String ruleId, QueryResponse parsedToken) {
        if (tokenRules != null && !tokenRules.isEmpty() && tokenRules.containsKey(ruleId)) {
            String timestampStr = tokenRules.get(ruleId);
            try {
                long timestamp = Long.parseLong(timestampStr);
                var tokenTime = parsedToken.getCreation().getTime();
                boolean result = tokenTime <= timestamp;
                if (result) {
                    return Optional.of(true);
                }
            } catch (NumberFormatException e) {
                log.error("Not able to convert timestamp value to number.", e);
            }
        }
        return Optional.empty();
    }

    public void evictNonRelevantTokensAndRules() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private String getHash(String token, byte[] salt) throws CachingServiceClientException {
        return getSecurePassword(token, salt);
    }

    public String getHash(String token) throws CachingServiceClientException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String initializeSalt() throws CachingServiceClientException, SecureTokenInitializationException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getToken(String username, int expirationTime, Set<String> scopes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isValidForScopes(String jwtToken, String serviceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] getSalt() throws CachingServiceClientException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void storeSalt(byte[] salt) throws CachingServiceClientException {
        cachingServiceClient.create(new CachingServiceClient.KeyValue("salt", new String(salt)));
    }

    public static byte[] generateSalt() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String getSecurePassword(String password, byte[] salt) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
