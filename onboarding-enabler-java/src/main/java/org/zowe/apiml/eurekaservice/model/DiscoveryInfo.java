/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.eurekaservice.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "discoveryInfo")
public class DiscoveryInfo {

    private String hostName;

    private Boolean secure;

    private String serviceName;

    private Integer port;

    private String serviceType;

    private String serviceTitle;

    private Boolean enableApiDoc;

    private String description;

    @SuppressWarnings("squid:S00107")
    public DiscoveryInfo(String hostName, Boolean secure, String serviceName, Integer port, String serviceType, String serviceTitle, Boolean enableApiDoc, String description) {
        this.hostName = hostName;
        this.secure = secure;
        this.serviceName = serviceName;
        this.port = port;
        this.serviceType = serviceType;
        this.serviceTitle = serviceTitle;
        this.enableApiDoc = enableApiDoc;
        this.description = description;
    }

    public String getHostName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Boolean getSecure() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getServiceName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Integer getPort() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getServiceType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getServiceTitle() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Boolean getEnableApiDoc() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getDescription() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
