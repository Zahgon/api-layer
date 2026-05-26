/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zaas.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.core.config.DefaultConfiguration;
import org.ehcache.impl.config.persistence.DefaultPersistenceConfiguration;
import org.ehcache.impl.config.store.disk.OffHeapDiskStoreConfiguration;
import org.ehcache.impl.config.store.disk.OffHeapDiskStoreProviderConfiguration;
import org.ehcache.impl.copy.IdentityCopier;
import org.ehcache.impl.copy.SerializingCopier;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.zowe.apiml.cache.CompositeKeyGenerator;
import org.zowe.apiml.cache.CompositeKeyGeneratorWithoutLast;
import org.zowe.apiml.cache.Storage;
import org.zowe.apiml.product.gateway.GatewayClient;
import org.zowe.apiml.security.HttpsConfig;
import org.zowe.apiml.security.common.token.TokenAuthentication;
import org.zowe.apiml.util.CacheUtils;
import org.zowe.apiml.zaas.cache.CachingClient;
import org.zowe.apiml.zaas.cache.CachingServiceClient;
import org.zowe.apiml.zaas.cache.LocalCachingClient;
import org.zowe.apiml.zaas.security.service.schema.source.AuthSource;
import javax.cache.Caching;
import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

/**
 * Spring configuration to use EhCache. This context is using from application and also from tests.
 */
@Slf4j
@EnableCaching
@Configuration
@RequiredArgsConstructor
public class CacheConfig {

    public static final String COMPOSITE_KEY_GENERATOR = "compositeKeyGenerator";

    public static final String COMPOSITE_KEY_GENERATOR_WITHOUT_LAST = "compositeKeyGeneratorWithoutLast";

    private static final String EHCACHE_STORAGE_ENV_PARAM_NAME = "ehcache.disk.store.dir";

    private static final String APIML_CACHE_STORAGE_LOCATION_ENV_PARAM_NAME = "apiml.cache.storage.location";

    @Value("${apiml.caching.enabled:true}")
    private boolean cacheEnabled;

    @Value("${apiml.cache.storage.location:./ehcache}")
    private String cacheDirectory;

    @PostConstruct
    public void afterPropertiesSet() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Primary
    @Bean("cacheManager")
    @ConditionalOnBean(name = "modulithConfig")
    @ConditionalOnProperty(value = "apiml.caching.enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnProperty(name = "caching.storage.mode", havingValue = "inMemory", matchIfMissing = true)
    public CacheManager cacheManagerModulith() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // fix for Redis IT (see setting CACHING_STORAGE_MODE='redis' for all service). This property is not related to microservices at all
    @Primary
    @Bean("cacheManager")
    @ConditionalOnMissingBean(name = "modulithConfig")
    @ConditionalOnProperty(value = "apiml.caching.enabled", havingValue = "true", matchIfMissing = true)
    public CacheManager cacheManagerZaas() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public CacheManager createCacheManager() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @ConditionalOnProperty(value = "apiml.caching.enabled", havingValue = "false")
    @Bean("cacheManager")
    @ConditionalOnMissingBean(name = "modulithConfig")
    public CacheManager cacheManagerNoOp() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(CacheConfig.COMPOSITE_KEY_GENERATOR)
    public KeyGenerator getCompositeKeyGenerator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(CacheConfig.COMPOSITE_KEY_GENERATOR_WITHOUT_LAST)
    public KeyGenerator getCompositeKeyGeneratorWithoutLast() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    public CacheUtils cacheUtils() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @ConditionalOnMissingBean(name = "modulithConfig")
    public CachingClient cachingServiceClient(GatewayClient gatewayClient, @Qualifier("restTemplateWithKeystore") RestTemplate restTemplate) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @ConditionalOnMissingBean
    public CachingClient cachingClient(Storage storage, HttpsConfig httpsConfig) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
