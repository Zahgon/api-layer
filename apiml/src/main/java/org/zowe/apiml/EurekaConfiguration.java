/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
/*
 * Copyright 2013-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.zowe.apiml;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.converters.EurekaJacksonCodec;
import com.netflix.discovery.converters.wrappers.CodecWrapper;
import com.netflix.discovery.converters.wrappers.CodecWrappers;
import com.netflix.discovery.shared.transport.jersey.TransportClientFactories;
import com.netflix.discovery.shared.transport.jersey3.Jersey3TransportClientFactories;
import com.netflix.eureka.DefaultEurekaServerContext;
import com.netflix.eureka.EurekaServerConfig;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.cluster.PeerEurekaNodes;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import com.netflix.eureka.resources.*;
import com.netflix.eureka.transport.EurekaServerHttpClientFactory;
import com.netflix.eureka.transport.Jersey3EurekaServerHttpClientFactory;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ext.Provider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.jvnet.hk2.spring.bridge.api.SpringBridge;
import org.jvnet.hk2.spring.bridge.api.SpringIntoHK2Bridge;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.actuator.HasFeatures;
import org.springframework.cloud.netflix.eureka.EurekaConstants;
import org.springframework.cloud.netflix.eureka.server.*;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Gunnar Hillert
 * @author Biju Kunjummen
 * @author Fahim Farook
 * @author Weix Sun
 * @author Robert Bleyl
 * @author Olga Maciaszek-Sharma
 */
@Configuration(proxyBeanMethods = false)
@Import(EurekaServerInitializerConfiguration.class)
@EnableConfigurationProperties({ EurekaDashboardProperties.class, InstanceRegistryProperties.class, EurekaProperties.class })
@PropertySource("classpath:/eureka/server.properties")
public class EurekaConfiguration implements WebMvcConfigurer {

    private static final Log log = LogFactory.getLog(EurekaConfiguration.class);

    /**
     * List of packages containing Jersey resources required by the Eureka server.
     */
    private static final String[] EUREKA_PACKAGES = new String[] { "com.netflix.discovery", "com.netflix.eureka" };

    /**
     * Static content pattern for dashboard elements (images, css, etc...).
     */
    private static final String STATIC_CONTENT_PATTERN = "/(fonts|images|css|js)/.*";

    @Autowired
    private ApplicationInfoManager applicationInfoManager;

    @Autowired
    private EurekaServerConfig eurekaServerConfig;

    @Autowired
    private EurekaClientConfig eurekaClientConfig;

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private InstanceRegistryProperties instanceRegistryProperties;

    /**
     * A {@link CloudJacksonJson} instance.
     */
    public static final CloudJacksonJson JACKSON_JSON = new CloudJacksonJson();

    @Bean
    HasFeatures eurekaServerFeature() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static {
        CodecWrappers.registerWrapper(JACKSON_JSON);
        EurekaJacksonCodec.setInstance(JACKSON_JSON.getCodec());
    }

    @Bean
    ServerCodecs serverCodecs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @ConditionalOnMissingBean
    ReplicationClientAdditionalFilters replicationClientAdditionalFilters() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @ConditionalOnMissingBean(TransportClientFactories.class)
    Jersey3TransportClientFactories jersey3TransportClientFactories() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @ConditionalOnMissingBean(EurekaServerHttpClientFactory.class)
    Jersey3EurekaServerHttpClientFactory jersey3EurekaServerHttpClientFactory() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    ApplicationsResource applicationsResource() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    VIPResource vipResource() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    ServerInfoResource serverInfoResource() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    SecureVIPResource secureVIPResource() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    InstancesResource instancesResource() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    ASGResource asgResource() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    PeerReplicationResource peerReplicationResource() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @ConditionalOnMissingBean
    EurekaServerContext eurekaServerContext(ServerCodecs serverCodecs, PeerAwareInstanceRegistry registry, PeerEurekaNodes peerEurekaNodes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    EurekaServerBootstrap eurekaServerBootstrap(PeerAwareInstanceRegistry registry, EurekaServerContext serverContext) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Register the Jersey filter.
     *
     * @param eurekaJerseyApp an {@link Application} for the filter to be registered
     * @return a jersey {@link FilterRegistrationBean}
     */
    @Bean
    FilterRegistrationBean<?> jerseyFilterRegistration(ResourceConfig eurekaJerseyApp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    FilterRegistrationBean<?> eurekaVersionFilterRegistration(ServerProperties serverProperties, Environment env) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Construct a Jersey {@link jakarta.ws.rs.core.Application} with all the resources
     * required by the Eureka server.
     *
     * @param environment    an {@link Environment} instance to retrieve classpath resources
     * @param resourceLoader a {@link ResourceLoader} instance to get classloader from
     * @return created {@link Application} object
     */
    @Bean
    ResourceConfig jerseyApplication(Environment environment, ResourceLoader resourceLoader, BeanFactory beanFactory) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @ConditionalOnBean(name = "httpTraceFilter")
    FilterRegistrationBean<?> traceFilterRegistration(@Qualifier("httpTraceFilter") Filter filter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Configuration(proxyBeanMethods = false)
    protected static class EurekaServerConfigBeanConfiguration {

        @Bean
        @ConditionalOnMissingBean
        EurekaServerConfig eurekaServerConfig(EurekaClientConfig clientConfig) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    class CloudServerCodecs extends DefaultServerCodecs {

        CloudServerCodecs(EurekaServerConfig serverConfig) {
            super(getFullJson(serverConfig), CodecWrappers.getCodec(CodecWrappers.JacksonJsonMini.class), getFullXml(serverConfig), CodecWrappers.getCodec(CodecWrappers.JacksonXmlMini.class));
        }

        private static CodecWrapper getFullJson(EurekaServerConfig serverConfig) {
            CodecWrapper codec = CodecWrappers.getCodec(serverConfig.getJsonCodecName());
            return codec == null ? CodecWrappers.getCodec(JACKSON_JSON.codecName()) : codec;
        }

        private static CodecWrapper getFullXml(EurekaServerConfig serverConfig) {
            CodecWrapper codec = CodecWrappers.getCodec(serverConfig.getXmlCodecName());
            return codec == null ? CodecWrappers.getCodec(CodecWrappers.XStreamXml.class) : codec;
        }
    }
}
