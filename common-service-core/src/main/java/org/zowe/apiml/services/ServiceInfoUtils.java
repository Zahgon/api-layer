/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.services;

import com.fasterxml.jackson.core.Version;
import com.netflix.appinfo.InstanceInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.zowe.apiml.config.ApiInfo;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Utility class containing mapping functions for ServiceInfo formatting
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceInfoUtils {

    public static Map<String, ServiceInfo.Instances> getInstances(List<InstanceInfo> appInstances) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String getBasePath(ApiInfo apiInfo, InstanceInfo instanceInfo) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static String getHealthCheckUrl(InstanceInfo instanceInfo) {
        return instanceInfo.isPortEnabled(InstanceInfo.PortType.SECURE) ? instanceInfo.getSecureHealthCheckUrl() : instanceInfo.getHealthCheckUrl();
    }

    private static int getPort(InstanceInfo instanceInfo) {
        return instanceInfo.isPortEnabled(InstanceInfo.PortType.SECURE) ? instanceInfo.getSecurePort() : instanceInfo.getPort();
    }

    private static String getProtocol(InstanceInfo instanceInfo) {
        return instanceInfo.isPortEnabled(InstanceInfo.PortType.SECURE) ? "https" : "http";
    }

    public static int getMajorVersion(ServiceInfo.ApiInfoExtended apiInfo) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Map<String, String> getCustomMetadata(Map<String, String> metadata) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Version getVersion(String version) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static InstanceInfo.InstanceStatus getStatus(List<InstanceInfo> instances) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
