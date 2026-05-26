/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.discovery.staticdef;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.InstanceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zowe.apiml.discovery.ApimlInstanceRegistry;
import org.zowe.apiml.discovery.EurekaRegistryAvailableListener;
import org.zowe.apiml.discovery.metadata.MetadataDefaultsService;
import org.zowe.apiml.message.core.Message;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.product.discovery.ServiceOverrideData;
import org.zowe.apiml.product.discovery.StaticRegistrationResult;
import org.zowe.apiml.product.discovery.StaticServicesRegistration;
import org.zowe.apiml.product.logging.annotations.InjectApimlLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Responsible for registration of statically defined APIs into Eureka and updating their status.
 *
 * The service called by {@link EurekaRegistryAvailableListener} that calls method {@link #registerServices()}.
 */
@Slf4j
@Component
public class StaticServicesRegistrationService implements StaticServicesRegistration {

    @Value("${apiml.discovery.staticApiDefinitionsDirectories:#{null}}")
    private String staticApiDefinitionsDirectories;

    @InjectApimlLogger
    private ApimlLogger apimlLog = ApimlLogger.empty();

    private final ServiceDefinitionProcessor serviceDefinitionProcessor;

    private final MetadataDefaultsService metadataDefaultsService;

    private final List<InstanceInfo> staticInstances = new CopyOnWriteArrayList<>();

    public StaticServicesRegistrationService(ServiceDefinitionProcessor serviceDefinitionProcessor, MetadataDefaultsService metadataDefaultsService) {
        this.serviceDefinitionProcessor = serviceDefinitionProcessor;
        this.metadataDefaultsService = metadataDefaultsService;
    }

    /**
     * Lists information about registered static service instances.
     */
    public List<InstanceInfo> getStaticInstances() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Registers all statically defined APIs in locations specified by configuration.
     */
    public void registerServices() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Reloads all statically defined APIs in locations specified by configuration
     * by reading the definitions again.
     */
    public synchronized StaticRegistrationResult reloadServices() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void register(StaticRegistrationResult result, InstanceInfo instanceInfo) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Registers all statically defined APIs in a directory.
     */
    StaticRegistrationResult registerServices(String staticApiDefinitionsDirectories) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private ApimlInstanceRegistry getRegistry() {
        return (ApimlInstanceRegistry) getServerContext().getRegistry();
    }

    private EurekaServerContext getServerContext() {
        return EurekaServerContextHolder.getInstance().getServerContext();
    }
}
