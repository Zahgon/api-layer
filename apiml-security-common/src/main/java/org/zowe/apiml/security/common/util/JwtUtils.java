/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.security.common.util;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.ExpiredJWTException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.zowe.apiml.security.common.token.TokenExpireException;
import org.zowe.apiml.security.common.token.TokenFormatNotValidException;
import org.zowe.apiml.security.common.token.TokenNotValidException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Slf4j
@UtilityClass
public class JwtUtils {

    private static final String HEADER_NONE_SIGNATURE = Base64.getEncoder().encodeToString("""
        {"typ":"JWT","alg":"none"}""".getBytes(StandardCharsets.UTF_8));

    private static final String TOKEN_IS_NOT_VALID_DUE_TO = "Token is not valid due to: {}.";

    /**
     * This method reads the claims without validating the token signature. It should be used only if the validity was checked in the calling code.
     *
     * @param jwt token to be parsed
     * @return parsed claims
     * @throws TokenNotValidException in case of invalid input, or TokenExpireException if JWT is expired
     */
    public JWTClaimsSet getJwtClaims(String jwt) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This method removes the token signature and replace algorithm with none. It allows to parse payload without
     * public key.
     *
     * @param jwtToken token to modify
     * @return unsigned jwt token
     * @throws BadJWTException
     */
    public String removeJwtSign(String jwtToken) throws BadJWTException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Method to translate original exception to internal one. It is used in case of parsing and verifying of JWT tokens.
     *
     * @param exception original exception
     * @return translated exception (better messaging and allow subsequent handling)
     */
    public RuntimeException handleJwtParserException(Exception exception) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Extracts value of a field from an OIDC token. The value is extracted from a custom path which supports nested objects.
     * @param token to extract the field from
     * @param pathToField list of strings representing path to the field
     * @return list of values extracted from the token field
     *
     * @throws TokenFormatNotValidException in case of the field value cannot be extracted from the token, is null, or empty
     */
    public List<String> getFieldValuesFromToken(String token, List<String> pathToField) throws TokenFormatNotValidException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private List<String> extractHighLevelField(JWTClaimsSet claims, List<String> pathToField) {
        return extractValueAsList(claims.getClaim(pathToField.get(0)));
    }

    @SuppressWarnings({ "rawtypes" })
    private List<String> extractNestedFields(JWTClaimsSet claims, List<String> pathToField) {
        var iterator = pathToField.iterator();
        var key = iterator.next();
        var claim = claims.getClaim(key);
        while (iterator.hasNext()) {
            key = iterator.next();
            if (iterator.hasNext() && claim instanceof Map val) {
                claim = val.get(key);
            }
        }
        return extractValueAsList(((Map) claim).get(key));
    }

    @SuppressWarnings("unchecked")
    private List<String> extractValueAsList(Object rawValue) {
        if (rawValue instanceof String value) {
            return List.of(value);
        } else if (rawValue instanceof List values) {
            return values;
        } else {
            throw new IllegalArgumentException("Field value is neither String nor List of Strings");
        }
    }
}
