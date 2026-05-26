/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zaas.security.service.zosmf;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.*;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.product.logging.annotations.InjectApimlLogger;
import org.zowe.apiml.security.common.config.AuthConfigurationProperties;
import org.zowe.apiml.security.common.error.ServiceNotAccessibleException;
import org.zowe.apiml.security.common.login.LoginRequest;
import javax.net.ssl.SSLHandshakeException;
import java.net.ConnectException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Supplier;
import static org.zowe.apiml.security.SecurityUtils.readPassword;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractZosmfService {

    protected static final String ZOSMF_INFO_END_POINT = "/zosmf/info";

    protected static final String ZOSMF_AUTHENTICATE_END_POINT = "/zosmf/services/authenticate";

    protected static final String ZOSMF_CSRF_HEADER = "X-CSRF-ZOSMF-HEADER";

    protected static final String ZOSMF_DOMAIN = "zosmf_saf_realm";

    @InjectApimlLogger
    protected ApimlLogger apimlLog = ApimlLogger.empty();

    protected final ApplicationContext applicationContext;

    protected final AuthConfigurationProperties authConfigurationProperties;

    protected final RestTemplate restTemplateWithoutKeystore;

    protected final ObjectMapper securityObjectMapper;

    protected DiscoveryClient discovery;

    @PostConstruct
    protected void afterPropertiesSet() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @return serviceId of z/OSMF service from configuration, which is used
     */
    protected String getZosmfServiceId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Methods construct the value of authentication header by credentials
     *
     * @param authentication credentials to generates header value
     * @return prepared header value (see header Authentication)
     */
    protected String getAuthenticationValue(Authentication authentication) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * TODO: move to a library such as EurekaUtils
     * Construct base URL for specific InstanceInfo
     *
     * @param serviceInstance Instance of service, for which we want to get an URL
     * @return URL to the instance
     */
    public static final String getUrl(ServiceInstance serviceInstance) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return z/OSMF instance uri
     *
     * @param zosmf the z/OSMF service id
     * @return the uri
     * @throws ServiceNotAccessibleException if z/OSMF is not available in discovery service
     */
    protected String getURI(String zosmf) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Method handles exception from REST call to z/OSMF into internal exception. It convert original exception into
     * custom one with better messages and types for subsequent treatment.
     *
     * @param url URL of invoked REST endpoint
     * @param re  original exception
     * @return translated exception
     */
    protected RuntimeException handleExceptionOnCall(String url, RuntimeException re) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Read the token with name cookieName from the cookies
     *
     * @param cookies the cookies
     * @return the token if is set in cookies, otherwise null
     */
    protected String readTokenFromCookie(List<String> cookies, String cookieName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
