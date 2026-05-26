/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml;

import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;
import com.netflix.discovery.CacheRefreshedEvent;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.EurekaServerContextHolder;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.servlet.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaServiceInstance;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.TomcatHttpHandlerAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.ServletContextAware;
import org.zowe.apiml.apicatalog.ApiCatalogServiceAvailableEvent;
import org.zowe.apiml.config.ApplicationInfo;
import org.zowe.apiml.discovery.ApimlInstanceRegistry;
import org.zowe.apiml.eurekaservice.client.util.EurekaMetadataParser;
import org.zowe.apiml.filter.PreFluxFilter;
import org.zowe.apiml.gateway.services.ServicesInfoService;
import org.zowe.apiml.message.core.MessageService;
import org.zowe.apiml.message.yaml.YamlMessageServiceInstance;
import org.zowe.apiml.product.constants.CoreService;
import org.zowe.apiml.services.BasicInfoService;
import org.zowe.apiml.services.ServiceInfo;
import org.zowe.apiml.zaas.security.login.Providers;
import org.zowe.apiml.zaas.security.service.JwtSecurity;
import reactor.core.publisher.Flux;
import java.io.IOException;
import java.util.*;
import static org.zowe.apiml.services.ServiceInfoUtils.getInstances;
import static org.zowe.apiml.services.ServiceInfoUtils.getStatus;

@EnableScheduling
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties
@DependsOn(value = { "gatewayHealthIndicator" })
@Slf4j
@OpenAPIDefinition(security = { @SecurityRequirement(name = "LoginBasicAuth"), @SecurityRequirement(name = "ClientCert") }, info = @Info(title = "API Mediation Layer", description = "The API Mediation Layer REST API."))
@SecurityScheme(name = "LoginBasicAuth", type = SecuritySchemeType.HTTP, scheme = "basic")
@SecurityScheme(type = SecuritySchemeType.MUTUALTLS, name = "ClientCert", description = "Client certificate X509")
public class ModulithConfig {

    private final ApplicationContext applicationContext;

    private final Map<String, InstanceInfo> instances = new HashMap<>();

    private final GatewayEurekaInstanceConfigBean eurekaInstanceGw;

    private final CatalogEurekaInstanceConfigBean catalogEurekaInstanceConfigBean;

    private final EurekaClientConfig eurekaConfig;

    private final CachingServiceEurekaInstanceConfigBean cachingServiceEurekaInstanceConfigBean;

    private final ApplicationEventPublisher eventPublisher;

    private final Timer timer = new Timer("PeerReplicated-StaticServices");

    @Value("${server.ssl.enabled:true}")
    private boolean https;

    @Value("${apiml.service.hostname:localhost}")
    private String hostname;

    @Value("${apiml.service.ipAddress:127.0.0.1}")
    private String ipAddress;

    @Value("${apiml.service.port:10010}")
    private int gatewayPort;

    @Value("${apiml.internal-discovery.port:10011}")
    private int discoveryPort;

    @Value("${server.attlsServer.enabled:false}")
    private boolean isServerAttlsEnabled;

    @Value("${apiml.service.externalUrl}")
    private String externalUrl;

    @Bean
    ApplicationInfo applicationInfo() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private int getPort(String serviceId) {
        return Strings.CI.equals(serviceId, CoreService.DISCOVERY.getServiceId()) ? discoveryPort : gatewayPort;
    }

    private InstanceInfo getInstanceInfo(String serviceId) {
        int port = getPort(serviceId);
        var leaseInfo = LeaseInfo.Builder.newBuilder().setDurationInSecs(90).setRegistrationTimestamp(System.currentTimeMillis()).setRenewalTimestamp(System.currentTimeMillis()).setRenewalIntervalInSecs(30).setServiceUpTimestamp(System.currentTimeMillis()).build();
        Map<String, String> metadata = switch(serviceId) {
            case "gateway" ->
                eurekaInstanceGw.getMetadataMap();
            case "cachingservice" ->
                cachingServiceEurekaInstanceConfigBean.getMetadataMap();
            case "apicatalog" ->
                {
                    metadata = catalogEurekaInstanceConfigBean.getMetadataMap();
                    if (isServerAttlsEnabled) {
                        var allowedOrigins = "https://" + hostname + ":" + port + "," + externalUrl;
                        metadata.put("apiml.corsEnabled", "true");
                        metadata.put("apiml.corsAllowedOrigins", allowedOrigins);
                    }
                    yield metadata;
                }
            default ->
                new HashMap<>();
        };
        String homePagePath = metadata.getOrDefault("apiml.homePagePath", "/");
        String scheme = "https";
        if (!https && !isServerAttlsEnabled) {
            scheme = "http";
        }
        return InstanceInfo.Builder.newBuilder().setInstanceId(String.format("%s:%s:%d", hostname, serviceId, port)).setAppName(serviceId).setHostName(hostname).setHomePageUrl(null, String.format("%s://%s:%d%s", scheme, hostname, port, homePagePath)).setStatus(InstanceInfo.InstanceStatus.UP).setIPAddr(ipAddress).setPort(port).setSecurePort(port).enablePort(InstanceInfo.PortType.SECURE, https || isServerAttlsEnabled).enablePort(InstanceInfo.PortType.UNSECURE, !https && !isServerAttlsEnabled).setVIPAddress(serviceId).setDataCenterInfo(() -> DataCenterInfo.Name.MyOwn).setLeaseInfo(leaseInfo).setLastUpdatedTimestamp(System.currentTimeMillis()).setMetadata(metadata).setVIPAddress(serviceId).build();
    }

    static ApimlInstanceRegistry getRegistry() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void createLocalInstances() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationStart() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // TODO find better solution but DON'T JUST REMOVE!
    @Scheduled(initialDelay = 3000, fixedRate = 20_000)
    public void periodicJwtInit() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    ReactiveDiscoveryClient registryReactiveDiscoveryClient(DiscoveryClient registryDiscoveryClient) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    RouteRefreshListener routeRefreshListener(ApplicationEventPublisher publisher) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    DiscoveryClient registryDiscoveryClient() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @Primary
    MessageService messageService() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    public BasicInfoService basicInfoService(DiscoveryClient discoveryClient, EurekaMetadataParser eurekaMetadataParser) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @Primary
    TomcatReactiveWebServerFactory tomcatReactiveWebServerWithFiltersFactory(HttpHandler httpHandler, List<PreFluxFilter> preFluxFilters, ObjectProvider<TomcatConnectorCustomizer> connectorCustomizers, ObjectProvider<TomcatContextCustomizer> contextCustomizers, ObjectProvider<TomcatProtocolHandlerCustomizer<?>> protocolHandlerCustomizers, List<ServletContextAware> servletContextAwareListeners) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static class ServletWithFilters extends TomcatHttpHandlerAdapter {

        private final Servlet servlet;

        private final FilterChain filterChain;

        public ServletWithFilters(HttpHandler httpHandler, TomcatHttpHandlerAdapter servlet, Collection<? extends Filter> filters) {
            super(httpHandler);
            this.servlet = servlet;
            FilterChain chain = servlet::service;
            for (var filter : filters) {
                chain = createFilterChain(filter, chain);
            }
            this.filterChain = chain;
        }

        FilterChain createFilterChain(Filter filter, FilterChain filterChain) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void init(ServletConfig config) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public ServletConfig getServletConfig() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String getServletInfo() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void destroy() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
