/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zaas.security.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.zowe.apiml.message.yaml.YamlMessageServiceInstance;
import org.zowe.apiml.zaas.security.config.CompoundAuthProvider;
import org.zowe.apiml.zaas.security.service.zosmf.ZosmfService;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.product.logging.annotations.InjectApimlLogger;
import org.zowe.apiml.security.common.config.AuthConfigurationProperties;
import org.zowe.apiml.security.common.error.ServiceNotAccessibleException;
import java.util.List;
import static org.zowe.apiml.security.common.config.AuthConfigurationProperties.JWT_AUTOCONFIGURATION_MODE.*;

@Slf4j
@RequiredArgsConstructor
public class Providers {

    private final DiscoveryClient discoveryClient;

    private final AuthConfigurationProperties authConfigurationProperties;

    private final CompoundAuthProvider compoundAuthProvider;

    private final ZosmfService zosmfService;

    @InjectApimlLogger
    private ApimlLogger apimlLog = ApimlLogger.of(Providers.class, YamlMessageServiceInstance.getInstance());

    /**
     * This method decides whether the Zosmf service is available.
     *
     * @return Availability of the ZOSMF service in the system.
     * @throws AuthenticationServiceException if the z/OSMF service id is not configured
     */
    public boolean isZosmfAvailable() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Provide configured z/OSMF service ID from the ZAAS configuration.
     *
     * @return service ID of z/OSMF instance
     */
    public String getZosmfServiceId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Verify that the zOSMF is registered in the Discovery service and that we can actually reach it.
     *
     * @return true if the service is registered and properly responds.
     */
    public boolean isZosmfAvailableAndOnline() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This method decides whether the Zosmf is used for authentication
     *
     * @return Usage of the ZOSMF service in the system.
     */
    public boolean isZosfmUsed() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This method decides whether used zOSMF instance supports JWT tokens.
     *
     * @return True is the instance support JWT ; false if its LTPA
     */
    public boolean zosmfSupportsJwt() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This method is used to access configuration provided by the user to determine if zOSMF supports LTPA token
     * instead of JWT.
     *
     * @return true if configuration was set to indicate zOSMF supports LTPA.
     */
    public boolean isZosmfConfigurationSetToLtpa() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
