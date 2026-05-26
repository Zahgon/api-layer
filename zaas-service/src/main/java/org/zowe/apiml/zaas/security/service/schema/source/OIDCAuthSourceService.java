/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zaas.security.service.schema.source;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.zowe.apiml.message.core.MessageType;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.product.logging.annotations.InjectApimlLogger;
import org.zowe.apiml.security.common.audit.RauditxService;
import org.zowe.apiml.security.common.token.*;
import org.zowe.apiml.zaas.security.mapping.AuthenticationMapper;
import org.zowe.apiml.zaas.security.service.AuthenticationService;
import org.zowe.apiml.zaas.security.service.TokenCreationService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import static org.zowe.apiml.security.common.util.JwtUtils.getFieldValuesFromToken;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "apiml.security.oidc.enabled", havingValue = "true")
public class OIDCAuthSourceService extends TokenAuthSourceService implements InitializingBean {

    @InjectApimlLogger
    protected final ApimlLogger logger = ApimlLogger.empty();

    @Qualifier("oidcMapper")
    private final AuthenticationMapper mapper;

    private final AuthenticationService authenticationService;

    private final OIDCProvider oidcProvider;

    private final TokenCreationService tokenService;

    private final RauditxService rauditxService;

    @Value("${apiml.security.rauditx.onOidcUserIsMapped:false}")
    private boolean rauditxOnOidcUserIsMapped;

    @Value("${apiml.security.rauditx.oidcSourceUserPaths:sub}")
    private List<String> oidcSourceUserPaths;

    @Value("${apiml.security.oidc.userIdField:sub}")
    protected String userIdFieldPathProperty;

    private List<String> userIdFieldPath;

    @Override
    public void afterPropertiesSet() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected ApimlLogger getLogger() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Function<String, AuthSource> getMapper() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Optional<String> getToken(HttpServletRequest request) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isValid(AuthSource authSource) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean extractUserId(OIDCAuthSource authSource) {
        try {
            var userIds = getFieldValuesFromToken(authSource.getRawSource(), userIdFieldPath);
            logger.log(MessageType.DEBUG, "UserId values {} extracted from OIDC token field {}.", userIds, String.join(".", userIdFieldPath));
            authSource.setDistributedId(userIds);
            return true;
        } catch (TokenFormatNotValidException e) {
            logger.log(MessageType.DEBUG, "Cannot extract distributed id from OIDC token. Reason: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public AuthSource.Parsed parse(AuthSource authSource) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parse OIDC token
     *
     * @param oidcAuthSource{@link OIDCAuthSource} object which hold original source of authentication - OIDC token.
     * @param mapper               instance of {@link AuthenticationMapper} to use for parsing.
     * @return parsed authentication source.
     */
    private AuthSource.Parsed parseOIDCToken(OIDCAuthSource oidcAuthSource, AuthenticationMapper mapper) {
        String token = oidcAuthSource.getRawSource();
        logger.log(MessageType.DEBUG, "Calling identity mapper to retrieve mainframe user id.");
        String mappedUser = mapper.mapToMainframeUserId(oidcAuthSource);
        if (StringUtils.isEmpty(mappedUser)) {
            logger.log(MessageType.DEBUG, "No mainframe user id retrieved. Cancel parsing of OIDC token.");
            throw new NoMainframeIdentityException("No mainframe identity found.", token, true);
        } else {
            if (rauditxOnOidcUserIsMapped) {
                var rauditx = rauditxService.builder().authentication().alwaysLogSuccesses().userId(mappedUser).messageSegment("The OIDC token was mapped to the user account").success();
                try {
                    getFieldValuesFromToken(token, oidcSourceUserPaths).forEach(rauditx::sourceUserId);
                } catch (Exception e) {
                    log.debug("Cannot obtain source users from the OIDC token", e);
                }
                rauditx.issue();
            }
        }
        logger.log(MessageType.DEBUG, "Parsing OIDC token.");
        QueryResponse response = authenticationService.parseJwtToken(token).getQueryResponse();
        AuthSource.Origin origin = AuthSource.Origin.valueByTokenSource(response.getSource());
        return new ParsedTokenAuthSource(mappedUser, response.getCreation(), response.getExpiration(), origin);
    }

    //this method should be removed from the unrelated auth sources
    @Override
    public String getLtpaToken(AuthSource authSource) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getJWT(AuthSource authSource) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
