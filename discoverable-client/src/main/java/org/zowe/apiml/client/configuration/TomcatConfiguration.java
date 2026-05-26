/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.client.configuration;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.buf.EncodedSolidusHandling;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

/**
 * Configuration of Tomcat
 */
@Configuration
public class TomcatConfiguration {

    @Bean
    public TomcatConnectorCustomizer urlTomcatCustomizer() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    public ServletWebServerFactory servletContainer(List<TomcatConnectorCustomizer> connectorCustomizers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static class UrlTomcatCustomizer implements TomcatConnectorCustomizer {

        @Override
        public void customize(Connector connector) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
