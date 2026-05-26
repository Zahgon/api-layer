/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.apicatalog.swagger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaServiceInstance;
import org.springframework.stereotype.Service;
import org.zowe.apiml.apicatalog.config.ApiLayerServices;
import org.zowe.apiml.apicatalog.model.APIContainer;
import org.zowe.apiml.apicatalog.model.APIService;
import org.zowe.apiml.apicatalog.model.CustomStyleConfig;
import org.zowe.apiml.auth.Authentication;
import org.zowe.apiml.auth.AuthenticationScheme;
import org.zowe.apiml.config.ApiInfo;
import org.zowe.apiml.eurekaservice.client.util.EurekaMetadataParser;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.product.logging.annotations.InjectApimlLogger;
import org.zowe.apiml.product.routing.RoutedServices;
import org.zowe.apiml.product.routing.ServiceType;
import org.zowe.apiml.product.routing.transform.TransformService;
import org.zowe.apiml.product.routing.transform.URLTransformationException;
import org.zowe.apiml.util.EurekaUtils;
import java.util.*;
import java.util.stream.Collectors;
import static com.netflix.appinfo.InstanceInfo.InstanceStatus.UP;
import static org.zowe.apiml.constants.EurekaMetadataDefinition.*;
import static org.zowe.apiml.product.constants.CoreService.GATEWAY;

/**
 * Initialize the API catalog with the running instances.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContainerService {

    private static final String DEFAULT_APIINFO_KEY = "default";

    private final EurekaMetadataParser metadataParser = new EurekaMetadataParser();

    private final DiscoveryClient discoveryClient;

    private final TransformService transformService;

    private final CustomStyleConfig customStyleConfig;

    @Value("${apiml.catalog.hide.serviceInfo:false}")
    private boolean hideServiceInfo;

    @Value("${server.attlsClient.enabled:false}")
    private boolean isClientAttlsEnabled;

    @InjectApimlLogger
    private final ApimlLogger apimlLog = ApimlLogger.empty();

    private Set<String> getProductIds() {
        return discoveryClient.getServices().stream().map(discoveryClient::getInstances).flatMap(List::stream).map(ServiceInstance::getMetadata).map(metadata -> metadata.get(CATALOG_ID)).collect(Collectors.toSet());
    }

    /**
     * Return all cached service instances
     *
     * @return instances
     */
    public Collection<APIContainer> getAllContainers() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean isSso(ServiceInstance serviceInstance) {
        Map<String, String> eurekaMetadata = serviceInstance.getMetadata();
        return Authentication.builder().scheme(AuthenticationScheme.fromString(eurekaMetadata.get(AUTHENTICATION_SCHEME))).supportsSso(BooleanUtils.toBooleanObject(eurekaMetadata.get(AUTHENTICATION_SSO))).build().supportsSso();
    }

    private String getHomePageUrl(ServiceInstance serviceInstance) {
        if (serviceInstance instanceof EurekaServiceInstance eurekaServiceInstance) {
            return eurekaServiceInstance.getInstanceInfo().getHomePageUrl();
        }
        return serviceInstance.getUri().toString();
    }

    private boolean isUp(ServiceInstance serviceInstance) {
        if (serviceInstance instanceof EurekaServiceInstance eurekaServiceInstance) {
            return eurekaServiceInstance.getInstanceInfo().getStatus() == UP;
        }
        return true;
    }

    private boolean hasHomePage(ServiceInstance serviceInstance) {
        String instanceHomePage = getHomePageUrl(serviceInstance);
        return instanceHomePage != null && !instanceHomePage.isEmpty();
    }

    /**
     * Try to transform the service homepage url and return it. If it fails,
     * return the original homepage url
     *
     * @param serviceInstance the service instance
     * @return the transformed homepage url
     */
    private String getInstanceHomePageUrl(ServiceInstance serviceInstance) {
        String serviceId = StringUtils.lowerCase(serviceInstance.getServiceId());
        String instanceHomePage = getHomePageUrl(serviceInstance);
        //Gateway homePage is used to hold DVIPA address and must not be modified
        if (hasHomePage(serviceInstance) && !GATEWAY.getServiceId().equals(serviceId)) {
            instanceHomePage = instanceHomePage.trim();
            RoutedServices routes = metadataParser.parseRoutes(serviceInstance.getMetadata());
            try {
                instanceHomePage = transformService.transformURL(ServiceType.UI, serviceId, instanceHomePage, routes, isClientAttlsEnabled);
            } catch (URLTransformationException | IllegalArgumentException e) {
                if (!ApiLayerServices.isApiLayerService(serviceId)) {
                    apimlLog.log("org.zowe.apiml.apicatalog.homePageTransformFailed", serviceId, e.getMessage());
                }
            }
        }
        log.debug("Homepage URL for {} service is: {}", serviceId, instanceHomePage);
        return instanceHomePage;
    }

    /**
     * Get the base path for the service.
     *
     * @param serviceInstance the service instance
     * @return the base URL
     */
    private String getApiBasePath(ServiceInstance serviceInstance) {
        if (hasHomePage(serviceInstance)) {
            try {
                String apiBasePath = serviceInstance.getMetadata().get("apiml.apiBasePath");
                if (apiBasePath != null) {
                    return apiBasePath;
                }
                RoutedServices routes = metadataParser.parseRoutes(serviceInstance.getMetadata());
                return transformService.retrieveApiBasePath(StringUtils.lowerCase(serviceInstance.getServiceId()), getHomePageUrl(serviceInstance), routes);
            } catch (URLTransformationException e) {
                if (!ApiLayerServices.isApiLayerService(serviceInstance.getServiceId())) {
                    apimlLog.log("org.zowe.apiml.apicatalog.getApiBasePathFailed", serviceInstance.getServiceId(), e.getMessage());
                }
            }
        }
        return "";
    }

    /**
     * Create a APIService object using the instances metadata
     *
     * @param serviceInstance the service instance
     * @return a APIService object
     */
    APIService createAPIServiceFromInstance(ServiceInstance serviceInstance) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a new container based on information in a new instance
     *
     * @param productFamilyId parent id
     * @param serviceInstances   all instances
     * @return a new container
     */
    private APIContainer createNewContainerFromService(String productFamilyId, ServiceInstance... serviceInstances) {
        if (serviceInstances.length == 0) {
            return null;
        }
        Map<String, String> instanceInfoMetadata = serviceInstances[0].getMetadata();
        String title = instanceInfoMetadata.get(CATALOG_TITLE);
        String description = instanceInfoMetadata.get(CATALOG_DESCRIPTION);
        String version = instanceInfoMetadata.get(CATALOG_VERSION);
        APIContainer container = new APIContainer();
        container.setStatus("UP");
        container.setId(productFamilyId);
        container.setDescription(description);
        container.setTitle(title);
        container.setVersion(version);
        log.debug("updated Container cache with product family: " + productFamilyId + ": " + title);
        // create API Service from instance and update container last changed date
        for (ServiceInstance serviceInstance : serviceInstances) {
            container.addService(createAPIServiceFromInstance(serviceInstance));
        }
        return container;
    }

    boolean update(APIService apiService) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void setStatus(APIContainer apiContainer, int servicesCount, int activeServicesCount) {
        apiContainer.setTotalServices(servicesCount);
        apiContainer.setActiveServices(activeServicesCount);
        if (activeServicesCount == 0) {
            apiContainer.setStatus("DOWN");
        } else if (activeServicesCount == servicesCount) {
            apiContainer.setStatus("UP");
        } else {
            apiContainer.setStatus("WARNING");
        }
    }

    /**
     * Map the configuration to customize the Catalog UI to the container
     *
     * @param apiContainer
     */
    private void setCustomUiConfig(APIContainer apiContainer) {
        apiContainer.setCustomStyleConfig(customStyleConfig);
    }

    /**
     * Update the summary totals, sso and API IDs info for a container based on it's running services
     *
     * @param apiContainer calculate totals for this container
     */
    public void calculateContainerServiceValues(APIContainer apiContainer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * return cached service instance by id
     *
     * @param id service identifier
     * @return {@link APIContainer}
     */
    public APIContainer getContainerById(String id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public APIService getService(String serviceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
