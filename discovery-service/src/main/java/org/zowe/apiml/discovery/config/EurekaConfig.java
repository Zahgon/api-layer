/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.discovery.config;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.eureka.*;
import com.netflix.eureka.cluster.PeerEurekaNodes;
import com.netflix.eureka.resources.ServerCodecs;
import com.netflix.eureka.transport.EurekaServerHttpClientFactory;
import com.netflix.eureka.util.EurekaMonitors;
import jakarta.ws.rs.client.ClientRequestFilter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.cloud.netflix.eureka.server.InstanceRegistryProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.zowe.apiml.discovery.ApimlInstanceRegistry;
import org.zowe.apiml.discovery.eureka.RefreshablePeerEurekaNodes;
import javax.net.ssl.SSLContext;
import java.util.Collection;

/**
 * Configuration to rewrite default Eureka's implementation with custom one
 */
@Configuration
@Slf4j
public class EurekaConfig {

    private static final String PEER_AWARE_INSTANCE_REGISTRY = "peerAwareInstanceRegistry";

    @Value("${apiml.discovery.serviceIdPrefixReplacer:#{null}}")
    private String tuple;

    @Value("${apiml.discovery.maxPeerRetries:10}")
    private int maxPeerRetries;

    /**
     * This is a fix of impossible overriding of the original bean.
     *
     * @return bean definition processor to remove original bean peerAwareInstanceRegistry
     */
    @Bean
    public static BeanDefinitionRegistryPostProcessor deleteEurekaPeerAwareInstanceRegistry() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @Primary
    public ApimlInstanceRegistry apimlInstanceRegistry(EurekaServerConfig serverConfig, EurekaClientConfig clientConfig, ServerCodecs serverCodecs, EurekaClient eurekaClient, EurekaServerHttpClientFactory eurekaServerHttpClientFactory, InstanceRegistryProperties instanceRegistryProperties, ApplicationContext appCntx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @Primary
    public PeerEurekaNodes peerEurekaNodes(EurekaServerConfig serverConfig, EurekaClientConfig clientConfig, ServerCodecs serverCodecs, ApimlInstanceRegistry apimlInstanceRegistry, EurekaClient eurekaClient, Collection<ClientRequestFilter> replicationClientAdditionalFilters, @Qualifier("secureSslContext") SSLContext secureSslContext) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @Primary
    public EurekaServerContext eurekaServerContext(EurekaServerConfig serverConfig, ServerCodecs serverCodecs, ApimlInstanceRegistry apimlInstanceRegistry, EurekaClient eurekaClient, PeerEurekaNodes peerEurekaNodes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Getter
    public static class Tuple {

        boolean valid;

        String oldPrefix;

        String newPrefix;

        public Tuple(String tuple) {
            if (isValidTuple(tuple)) {
                String[] prefixes = tuple.split(",");
                this.oldPrefix = prefixes[0];
                this.newPrefix = prefixes[1];
                this.valid = true;
            }
        }

        public static boolean isValidTuple(String tuple) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
