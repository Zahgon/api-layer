/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.config;

import com.netflix.appinfo.*;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.config.HttpClientProperties;
import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.http.EurekaClientHttpRequestFactorySupplier;
import org.springframework.cloud.netflix.eureka.http.RestClientDiscoveryClientOptionalArgs;
import org.springframework.cloud.netflix.eureka.http.RestClientTransportClientFactories;
import org.springframework.cloud.util.ProxyUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.server.WebFilter;
import org.springframework.web.util.UriComponentsBuilder;
import org.zowe.apiml.config.AdditionalRegistration;
import org.zowe.apiml.config.AdditionalRegistrationCondition;
import org.zowe.apiml.config.AdditionalRegistrationParser;
import org.zowe.apiml.constants.EurekaMetadataDefinition;
import org.zowe.apiml.gateway.filters.proxyheaders.AdditionalRegistrationGatewayRegistry;
import org.zowe.apiml.gateway.filters.proxyheaders.X509AndGwAwareXForwardedHeadersFilter;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.message.yaml.YamlMessageServiceInstance;
import org.zowe.apiml.product.web.DiscoveryRestTemplateConfig;
import org.zowe.apiml.product.web.HttpConfig;
import org.zowe.apiml.security.HttpsConfigError;
import org.zowe.apiml.security.common.util.ConnectionUtil;
import org.zowe.apiml.util.CorsUtils;
import reactor.netty.http.client.HttpClient;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import static org.springframework.cloud.netflix.eureka.EurekaClientConfigBean.DEFAULT_ZONE;
import static org.zowe.apiml.constants.EurekaMetadataDefinition.*;

//TODO this configuration should be removed as redundancy of the HttpConfig in the apiml-common
@Configuration
@Slf4j
@RequiredArgsConstructor
public class ConnectionsConfig {

    private static final ApimlLogger apimlLog = ApimlLogger.of(ConnectionsConfig.class, YamlMessageServiceInstance.getInstance());

    @Value("${eureka.client.serviceUrl.defaultZone}")
    private String eurekaServerUrl;

    @Value("${apiml.service.corsEnabled:false}")
    private boolean corsEnabled;

    @Value("${apiml.service.corsAllowedMethods:GET,HEAD,POST,PATCH,DELETE,PUT,OPTIONS}")
    private List<String> corsAllowedMethods;

    @Value("${server.attlsClient.enabled:false}")
    private boolean isClientAttlsEnabled;

    private final ApplicationContext context;

    private final HttpConfig config;

    @Value("${apiml.service.externalUrl:}")
    private String externalUrl;

    @Value("${apiml.service.corsAllowedEndpoints:/gateway/**}")
    private final List<String> corsEnabledEndpoints;

    /**
     * @param httpClient             default http client
     * @param headersFiltersProvider header filter for spring gateway router
     * @param properties             client HTTP properties
     * @return instance of NettyRoutingFilterApiml
     */
    @Bean
    NettyRoutingFilterApiml createNettyRoutingFilterApiml(HttpClient httpClient, ObjectProvider<List<HttpHeadersFilter>> headersFiltersProvider, HttpClientProperties properties) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This bean processor is used to override bean routingFilter defined at
     * org.springframework.cloud.gateway.config.GatewayAutoConfiguration.NettyConfiguration#routingFilter(HttpClient, ObjectProvider, HttpClientProperties)
     * <p>
     * There is no simple way how to override this specific bean, but bean processing could handle that.
     *
     * @return bean processor to replace NettyRoutingFilter by NettyRoutingFilterApiml
     */
    @Bean
    static BeanPostProcessor routingFilterHandler(ApplicationContext context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(destroyMethod = "shutdown", name = "eurekaClient")
    @RefreshScope
    @ConditionalOnMissingBean(EurekaClient.class)
    CloudEurekaClient primaryEurekaClient(ApplicationInfoManager manager, EurekaClientConfig config, @Autowired(required = false) HealthCheckHandler healthCheckHandler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public RestClientDiscoveryClientOptionalArgs defaultArgs(EurekaClientHttpRequestFactorySupplier factorySupplier) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @DependsOn("discoveryClient")
    List<AdditionalRegistration> additionalRegistration() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(destroyMethod = "shutdown")
    @Conditional(AdditionalRegistrationCondition.class)
    @RefreshScope
    AdditionalEurekaClientsHolder additionalEurekaClientsHolder(ApplicationInfoManager manager, EurekaClientConfig config, List<AdditionalRegistration> additionalRegistrations, EurekaFactory eurekaFactory, @Autowired(required = false) HealthCheckHandler healthCheckHandler, AdditionalRegistrationGatewayRegistry additionalRegistrationGatewayRegistry, Optional<X509AndGwAwareXForwardedHeadersFilter> x509awareXForwardedHeadersFilter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private CloudEurekaClient registerInTheApimlInstance(EurekaClientConfig config, AdditionalRegistration apimlRegistration, ApplicationInfoManager appManager, EurekaFactory eurekaFactory) {
        log.debug("additional registration: {}", apimlRegistration.getDiscoveryServiceUrls());
        Map<String, String> urls = new HashMap<>();
        urls.put(DEFAULT_ZONE, apimlRegistration.getDiscoveryServiceUrls());
        EurekaClientConfigBean configBean = new EurekaClientConfigBean();
        BeanUtils.copyProperties(config, configBean);
        configBean.setServiceUrl(urls);
        configBean.setRegisterWithEureka(true);
        configBean.setFetchRegistry(true);
        EurekaInstanceConfig eurekaInstanceConfig = appManager.getEurekaInstanceConfig();
        InstanceInfo newInfo = create(eurekaInstanceConfig);
        updateMetadata(newInfo, apimlRegistration);
        RestClientDiscoveryClientOptionalArgs args1 = defaultArgs(DiscoveryRestTemplateConfig.getDefaultEurekaClientHttpRequestFactorySupplier());
        RestClientTransportClientFactories factories = new RestClientTransportClientFactories(args1);
        return eurekaFactory.createCloudEurekaClient(new AdditionalEurekaConfiguration(eurekaInstanceConfig, newInfo), newInfo, configBean, context, factories, args1);
    }

    private boolean isRouteKey(String key) {
        return StringUtils.startsWith(key, ROUTES + ".") && (StringUtils.endsWith(key, "." + ROUTES_GATEWAY_URL) || StringUtils.endsWith(key, "." + ROUTES_SERVICE_URL));
    }

    private void updateMetadata(InstanceInfo instanceInfo, AdditionalRegistration additionalRegistration) {
        var metadata = instanceInfo.getMetadata();
        metadata.put(REGISTRATION_TYPE, EurekaMetadataDefinition.RegistrationType.ADDITIONAL.getValue());
        // if routes were override replace them in the map, otherwise use the default from the primary registration
        if (!CollectionUtils.isEmpty(additionalRegistration.getRoutes())) {
            // remove current routes
            var currentRoutes = metadata.keySet().stream().filter(this::isRouteKey).collect(Collectors.toSet());
            currentRoutes.forEach(metadata::remove);
            // generate new routes metadata
            int index = 0;
            for (var route : additionalRegistration.getRoutes()) {
                metadata.put(String.format("apiml.routes.%d.gatewayUrl", index), route.getGatewayUrl());
                metadata.put(String.format("apiml.routes.%d.serviceUrl", index++), route.getServiceUrl());
            }
        }
    }

    @Bean
    Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    CorsUtils corsUtils() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    WebFilter corsWebFilter(ServiceCorsUpdater serviceCorsUpdater) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public InstanceInfo create(EurekaInstanceConfig config) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @RequiredArgsConstructor
    static class AdditionalEurekaConfiguration implements EurekaInstanceConfig {

        @Delegate(excludes = NonDelegated.class)
        private final EurekaInstanceConfig eurekaInstanceConfig;

        private final InstanceInfo instanceInfo;

        @Override
        public String getHostName(boolean refresh) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String getHealthCheckUrl() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String getSecureHealthCheckUrl() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String getHomePageUrl() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String getStatusPageUrl() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        interface NonDelegated {

            String getHostName(boolean refresh);

            String getHealthCheckUrl();

            String getSecureHealthCheckUrl();

            String getHomePageUrl();

            String getStatusPageUrl();
        }
    }
}
