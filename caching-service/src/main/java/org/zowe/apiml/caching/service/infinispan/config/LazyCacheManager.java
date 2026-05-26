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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.commons.compiler.util.Producer;
import org.infinispan.Cache;
import org.infinispan.commons.CacheConfigurationException;
import org.infinispan.commons.api.CacheContainerAdmin;
import org.infinispan.commons.configuration.ClassAllowList;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.parsing.ConfigurationBuilderHolder;
import org.infinispan.health.Health;
import org.infinispan.lifecycle.ComponentStatus;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.CacheManagerInfo;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.remoting.transport.Address;
import org.infinispan.stats.CacheContainerStats;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import javax.security.auth.Subject;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class LazyCacheManager extends DefaultCacheManager {

    // how many minutes wait thread to finish initialization by other threads
    private static final int INIT_TIMEOUT_MINS = 1;

    private final AtomicReference<Producer<DefaultCacheManager>> cacheManager;

    private final CacheInitializer cacheInitializer;

    public LazyCacheManager(ConfigurationBuilderHolder cacheManagerConfig, Map<String, ConfigurationBuilder> caches) {
        super(cacheManagerConfig, false);
        cacheInitializer = new CacheInitializer(cacheManagerConfig, caches);
        cacheManager = new AtomicReference<>(cacheInitializer::getDefaultCacheManager);
    }

    private DefaultCacheManager getCacheManager() {
        var container = cacheManager.get().produce();
        if (container == null) {
            throw new IllegalStateException("Cache container is not initialized yet");
        }
        return container;
    }

    public boolean isInitialized() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Configuration defineConfiguration(String cacheName, Configuration configuration) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Configuration defineConfiguration(String cacheName, String templateCacheName, Configuration configurationOverride) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void undefineConfiguration(String configurationName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getClusterName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public List<Address> getMembers() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Address getAddress() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Address getCoordinator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isCoordinator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public ComponentStatus getStatus() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public GlobalConfiguration getCacheManagerConfiguration() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Configuration getCacheConfiguration(String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Configuration getDefaultCacheConfiguration() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Set<String> getAccessibleCacheNames() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isRunning(String cacheName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isDefaultRunning() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean cacheExists(String cacheName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean cacheConfigurationExists(String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public <K, V> Cache<K, V> getCache() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public <K, V> Cache<K, V> createCache(String name, Configuration configuration) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public <K, V> Cache<K, V> getCache(String cacheName, boolean createIfAbsent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public EmbeddedCacheManager startCaches(String... cacheNames) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void addCacheDependency(String from, String to) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CacheContainerStats getStats() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Health getHealth() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CacheManagerInfo getCacheManagerInfo() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public ClassAllowList getClassAllowList() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Subject getSubject() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public EmbeddedCacheManager withSubject(Subject subject) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Set<String> getCacheNames() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void start() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public <T extends CacheContainer> T getOriginal() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void close() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<Void> addListenerAsync(Object listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<Void> removeListenerAsync(Object listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationStart() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @RequiredArgsConstructor
    class CacheInitializer {

        private DefaultCacheManager underInit;

        private final ConfigurationBuilderHolder cacheManagerConfig;

        private final Map<String, ConfigurationBuilder> caches;

        private final Phaser threadCounter = new Phaser();

        private DefaultCacheManager startDefaultCacheManager() {
            var defaultCacheManager = new DefaultCacheManager(cacheManagerConfig, false);
            try {
                defaultCacheManager.start();
                return defaultCacheManager;
            } catch (RuntimeException reStart) {
                log.warn("Cannot start caching manager", reStart);
                try {
                    defaultCacheManager.stop();
                } catch (RuntimeException reStop) {
                    log.debug("Cannot stop failing caching manager", reStop);
                }
                throw reStart;
            }
        }

        /**
         * This method is responsible for initializing of CacheManager. The method could be called by
         * multiple threads. The aim is to split work in this case and then return the fully initialized
         * cache manager. In case of failure the original instance of lazy manager or partially initialized
         * bean could be returned. The next invocation should initiate it.
         * @return partially or fully initialized cache manager
         */
        public DefaultCacheManager getDefaultCacheManager() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private boolean createCache(String cacheName, ConfigurationBuilder cacheBuilder) {
            var cacheConfig = cacheBuilder.build();
            log.debug("Initializing cache {} with config {}", cacheName, cacheConfig);
            try {
                underInit.administration().withFlags(CacheContainerAdmin.AdminFlag.VOLATILE).getOrCreateCache(cacheName, cacheConfig);
                return true;
            } catch (CacheConfigurationException cce) {
                log.warn("Error during initialization of cache {}", cacheName, cce);
                try {
                    underInit.defineConfiguration(cacheName, cacheConfig);
                } catch (Exception e) {
                    log.warn("Configuration for cache {} already exists", cacheName, e);
                }
            } catch (Exception e) {
                log.warn("Error during initialization of cache {}", cacheName, e);
            }
            return false;
        }

        public boolean isInitialized() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void onApplicationStart() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
