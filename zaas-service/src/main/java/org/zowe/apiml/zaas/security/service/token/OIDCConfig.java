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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import java.time.Clock;

@Configuration
public class OIDCConfig {

    @Bean("oidcJwtClock")
    public Clock oidcJwtClock() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean("oidcJwkMapper")
    @Primary
    public ObjectMapper oidcJwkMapper() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
