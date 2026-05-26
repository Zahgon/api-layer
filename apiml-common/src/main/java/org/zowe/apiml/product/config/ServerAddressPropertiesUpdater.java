/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.product.config;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.AbstractConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Order(Ordered.LOWEST_PRECEDENCE)
@Configuration
@Slf4j
public class ServerAddressPropertiesUpdater implements EnvironmentPostProcessor {

    private static final String ADDITIONAL_SUFFIX = ".additional";

    /**
     * map of extra connector to bind a port (see key) to a network interface (see value). It shouldn't contain
     * the main pair (port x network interface), because it is bound by Spring Boot.
     */
    private static final Map<Integer, List<String>> ADDITIONAL_NETWORKS = new HashMap<>();

    // to know what type of customizer to use (Servlet vs. Reactive)
    private static String webApplicationType;

    /**
     * To read the META-INF/spring.factories file that contains also the port definition
     * @return configuration of additional ports
     */
    private Properties readProperties() {
        Properties properties = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("META-INF/spring.factories")) {
            if (is == null) {
                log.debug("META-INF/spring.factories file was not found.");
                return properties;
            }
            properties.load(is);
        } catch (Exception e) {
            log.error("Cannot read Tomcat connector configuration", e);
        }
        return properties;
    }

    /**
     * The method are spliting a original configuration value if there are multiple values. The original value should
     * contain exact one network interfaces. The rest is stored in a new configuration value (the same name with
     * suffix .additional)
     * @param environment Spring env
     * @param overriddenProperties map to collect all new or modified configuration values
     * @param addressKey name of configuration value with network interfaces
     * @param portKey name of configuration to read the port value
     * @param basePort if true, ignore the first value because it is bound by Spring boot
     */
    private void splitProperty(ConfigurableEnvironment environment, Map<String, Object> overriddenProperties, String addressKey, String portKey, boolean basePort) {
        String addressValue = environment.getProperty(addressKey);
        if (addressValue == null) {
            // the value is not configured at all (uses the default one: 0.0.0.0)
            return;
        }
        var addresses = Arrays.asList(addressValue.split(",")).stream().map(String::trim).toList();
        if (basePort) {
            // process the default port - the first value is configured by Spring Boot
            overriddenProperties.putIfAbsent(addressKey, addresses.get(0));
            addresses = addresses.subList(1, addresses.size());
            overriddenProperties.putIfAbsent(addressKey + ADDITIONAL_SUFFIX, StringUtils.join(addresses, ","));
        }
        if (!addresses.isEmpty()) {
            int port = Integer.parseInt(environment.getProperty(portKey));
            ADDITIONAL_NETWORKS.put(port, addresses);
        }
    }

    /**
     * This processor is responsible for normalizing a configuration values. If there are properties like
     * `server.address` it cannot be a list. The processor update this value to contain one value and if there are
     * other values they are stored as a new configuration value (the same name with the suffix `.additional`)
     * @param environment the environment to post-process
     * @param application the application to which the environment belongs
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This bean is responsible for generating new beans that create a new connector. It is implemented as Tomcat
     * customizer. Each additional connector has one new bean.
     * @return processor to create new customizer beans or an empty processor if there are no additional address.
     */
    @Bean
    public static BeanDefinitionRegistryPostProcessor registerAdditionalTomcatConnectors() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @RequiredArgsConstructor
    static abstract class AbstractAdditionalConnector<F extends AbstractConfigurableWebServerFactory> implements WebServerFactoryCustomizer<F> {

        protected final List<TomcatConnectorCustomizer> connectorCustomizers;

        @Setter
        protected int port;

        @Setter
        protected String address;

        protected Connector connector = new Connector();

        private void invokeCustomizer(F factory, Class<? super F> factoryClass, Connector connector) {
            Exception exception;
            try {
                Method method = factoryClass.getDeclaredMethod("customizeConnector", Connector.class);
                method.setAccessible(true);
                method.invoke(factory, connector);
                return;
            } catch (NoSuchMethodException e) {
                if (factoryClass != Object.class) {
                    invokeCustomizer(factory, factoryClass.getSuperclass(), connector);
                    return;
                }
                exception = e;
            } catch (SecurityException | IllegalAccessException | InvocationTargetException e) {
                exception = e;
            }
            log.error("Cannot create the custom Tomcat reactive connector with address {} on port {}: {}", address, port, exception.getMessage());
            throw new RuntimeException(exception);
        }

        @Override
        public void customize(F factory) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        protected abstract void initFactory(F factory);
    }

    /**
     * Customizer to bind a new connector to a network interface on reactive service
     */
    static class AdditionalConnectorReactive extends AbstractAdditionalConnector<TomcatReactiveWebServerFactory> {

        public AdditionalConnectorReactive(List<TomcatConnectorCustomizer> connectorCustomizers) {
            super(connectorCustomizers);
        }

        @Override
        protected void initFactory(TomcatReactiveWebServerFactory factory) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Customizer to bind a new connector to a network interface on servlet service
     */
    static class AdditionalConnectorServlet extends AbstractAdditionalConnector<TomcatServletWebServerFactory> {

        public AdditionalConnectorServlet(List<TomcatConnectorCustomizer> connectorCustomizers) {
            super(connectorCustomizers);
        }

        @Override
        protected void initFactory(TomcatServletWebServerFactory factory) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
