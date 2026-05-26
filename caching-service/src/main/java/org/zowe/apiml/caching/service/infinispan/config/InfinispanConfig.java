/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.caching.service.infinispan.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.infinispan.commons.dataconversion.MediaType;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.StorageType;
import org.infinispan.configuration.parsing.ConfigurationBuilderHolder;
import org.infinispan.configuration.parsing.ParserRegistry;
import org.infinispan.lock.EmbeddedClusteredLockManagerFactory;
import org.infinispan.lock.api.ClusteredLock;
import org.infinispan.lock.api.ClusteredLockManager;
import org.infinispan.lock.exception.ClusteredLockException;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.partitionhandling.AvailabilityException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.zowe.apiml.cache.Storage;
import org.zowe.apiml.cache.StorageException;
import org.zowe.apiml.caching.service.Messages;
import org.zowe.apiml.caching.service.infinispan.ApimlSslKeyExchange;
import org.zowe.apiml.caching.service.infinispan.exception.InfinispanConfigException;
import org.zowe.apiml.caching.service.infinispan.storage.InfinispanStorage;
import org.zowe.apiml.config.ApplicationInfo;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import static org.zowe.apiml.security.SecurityUtils.formatKeyringUrl;
import static org.zowe.apiml.security.SecurityUtils.isKeyring;

@Slf4j
@Configuration
@ConfigurationProperties(value = "caching.storage.infinispan")
@ConditionalOnProperty(name = "caching.storage.mode", havingValue = "infinispan")
public class InfinispanConfig implements InitializingBean {

    private static final String KEYRING_PASSWORD = "password";

    private static final String ZWE_HAINSTANCE_ID = "ZWE_haInstance_id";

    private static final String LOCK_ZOWE_INVALIDATED = "zoweInvalidatedTokenLock";

    public static final String CACHE_ZOWE = "zoweCache";

    public static final String CACHE_ZOWE_INVALIDATED_TOKEN = "zoweInvalidatedTokenCache";

    private static final long SMALL_CACHE_SIZE = 10;

    private static final long BIG_CACHE_SIZE = 1000;

    @Value("${caching.storage.infinispan.initialHosts}")
    private String initialHosts;

    @Value("${server.ssl.keyStoreType}")
    private String keyStoreType;

    @Value("${server.ssl.keyStore}")
    private String keyStore;

    @Value("${server.ssl.keyStorePassword}")
    private String keyStorePass;

    @Value("${server.ssl.trustStoreType}")
    private String trustStoreType;

    @Value("${server.ssl.trustStore}")
    private String trustStore;

    @Value("${server.ssl.trustStorePassword}")
    private String trustStorePass;

    @Value("${jgroups.bind.port}")
    private String port;

    @Value("${jgroups.bind.address}")
    private String address;

    @Value("${jgroups.keyExchange.socketTimeout:5000}")
    private String keyExchangeSocketTimeout;

    @Value("${jgroups.keyExchange.port:7601}")
    private String keyExchangePort;

    @Value("${jgroups.tcp.diag.enabled:false}")
    private String tcpDiagEnabled;

    @Value("${attlsEnabledOnInfinispanTest:${server.attlsServer.enabled:false}}")
    private boolean isServerAttlsEnabled;

    @Value("${caching.storage.infinispan.distributedSyncTimeoutSecs:360}")
    private int distributedSyncTimeout;

    @Value("${caching.storage.infinispan.numSegments:256}")
    private int numSegments;

    private final AtomicReference<ClusteredLock> zoweInvalidatedTokenLock = new AtomicReference<>();

    @Override
    public void afterPropertiesSet() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @PostConstruct
    void updateKeyring() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static String getRootFolder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getInfinispanConfigFile() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private String loadInfinispanConfigFile(ResourceLoader resourceLoader) {
        String fileName = getInfinispanConfigFile();
        try (var inputStream = resourceLoader.getResource("classpath:" + fileName).getInputStream()) {
            String config = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            config = config.replace("jgroup:SSL_KEY_EXCHANGE", ApimlSslKeyExchange.class.getCanonicalName());
            return config;
        } catch (IOException ioe) {
            throw new InfinispanConfigException("Can't read configuration file", ioe);
        }
    }

    private ConfigurationBuilderHolder getCacheManagerConfig(ResourceLoader resourceLoader) {
        String config = loadInfinispanConfigFile(resourceLoader);
        ConfigurationBuilderHolder holder = new ParserRegistry().parse(config, MediaType.APPLICATION_XML);
        holder.getGlobalConfigurationBuilder().globalState().persistentLocation(getRootFolder()).enable();
        holder.newConfigurationBuilder("default").persistence().addSoftIndexFileStore().clustering().cacheMode(CacheMode.REPL_SYNC).hash().numSegments(numSegments);
        holder.getGlobalConfigurationBuilder().defaultCacheName("default");
        holder.getGlobalConfigurationBuilder().transport().stack("prod").distributedSyncTimeout(distributedSyncTimeout, TimeUnit.SECONDS);
        return holder;
    }

    private ConfigurationBuilder getDistributedCacheConfig() {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.encoding().mediaType(MediaType.APPLICATION_JBOSS_MARSHALLING_TYPE).persistence().addSoftIndexFileStore().clustering().cacheMode(CacheMode.REPL_SYNC).hash().numSegments(numSegments);
        return builder;
    }

    private ConfigurationBuilder getSimpleCacheConfig(long maxCount, Duration lifeSpan) {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.encoding().mediaType(MediaType.APPLICATION_JBOSS_MARSHALLING_TYPE).memory().storage(StorageType.OFF_HEAP).maxCount(maxCount).simpleCache(true).expiration().lifespan(lifeSpan.toSeconds(), TimeUnit.SECONDS);
        return builder;
    }

    @Bean(destroyMethod = "stop")
    LazyCacheManager cacheManager(ResourceLoader resourceLoader, ApplicationInfo applicationInfo) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private ClusteredLock lock(CacheContainer cacheManager) {
        return zoweInvalidatedTokenLock.updateAndGet(prev -> {
            if (prev != null) {
                return prev;
            }
            EmbeddedCacheManager cm = (cacheManager instanceof LazyCacheManager lazyCacheManager) ? lazyCacheManager.getOriginal() : (EmbeddedCacheManager) cacheManager;
            try {
                ClusteredLockManager clm = EmbeddedClusteredLockManagerFactory.from(cm);
                // it can throw AvailabilityException
                clm.defineLock(LOCK_ZOWE_INVALIDATED);
                return clm.get(LOCK_ZOWE_INVALIDATED);
            } catch (AvailabilityException | ClusteredLockException e) {
                log.debug("Cannot obtain lock", e);
                throw new StorageException(Messages.CACHE_NOT_AVAILABLE.getKey(), Messages.CACHE_NOT_AVAILABLE.getStatus(), e.getMessage());
            }
        });
    }

    @Bean
    public Storage storage(DefaultCacheManager cacheManager) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
