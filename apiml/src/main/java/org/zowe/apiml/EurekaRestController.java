/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
/*
 * Copyright 2013-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.zowe.apiml;

import com.netflix.appinfo.EurekaAccept;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.eureka.cluster.PeerEurekaNode;
import com.netflix.eureka.cluster.protocol.ReplicationList;
import com.netflix.eureka.resources.ASGResource;
import com.netflix.eureka.resources.ApplicationsResource;
import com.netflix.eureka.resources.InstancesResource;
import com.netflix.eureka.resources.PeerReplicationResource;
import com.netflix.eureka.resources.SecureVIPResource;
import com.netflix.eureka.resources.ServerInfoResource;
import com.netflix.eureka.resources.VIPResource;
import jakarta.annotation.Nullable;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.HttpHeaders.ACCEPT_ENCODING;
import static org.zowe.apiml.EurekaConfiguration.JACKSON_JSON;
import static reactor.core.publisher.Mono.just;

// Generic type wildcard needed due to legacy code usage
@SuppressWarnings("java:S1452")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/eureka", produces = { "application/xml", "application/json" })
@DependsOn("modulithConfig")
@Slf4j
public class EurekaRestController {

    private static final String EUREKA_VERSION = "v2";

    private final ApplicationsResource applicationsResource;

    private final VIPResource vipResource;

    private final ServerInfoResource serverInfoResource;

    private final SecureVIPResource secureVIPResource;

    private final InstancesResource instancesResource;

    private final ASGResource asgResource;

    private final PeerReplicationResource peerReplicationResource;

    private UriInfo getUriInfo(ServerWebExchange serverWebExchange) {
        return new UriInfoAdapter(serverWebExchange.getRequest());
    }

    private ResponseEntity<?> convertResponse(Response response) {
        return ResponseEntity.status(response.getStatus()).headers(headers -> response.getHeaders().entrySet().forEach(newHeader -> headers.addAll(newHeader.getKey(), newHeader.getValue().stream().map(String::valueOf).toList()))).body(response.getEntity());
    }

    @GetMapping(value = { "/apps", "/apps/" }, produces = { "application/xml", "application/json" })
    public Mono<ResponseEntity<?>> getContainers(ServerWebExchange serverWebExchange, @Nullable @RequestHeader(ACCEPT) String acceptHeader, @Nullable @RequestHeader(ACCEPT_ENCODING) String acceptEncoding, @Nullable @RequestHeader(EurekaAccept.HTTP_X_EUREKA_ACCEPT) String eurekaAccept, @Nullable @RequestParam("regions") String regionsStr) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @GetMapping("/apps/delta")
    public Mono<ResponseEntity<?>> getContainerDifferential(ServerWebExchange serverWebExchange, @Nullable @RequestHeader(ACCEPT) String acceptHeader, @Nullable @RequestHeader(ACCEPT_ENCODING) String acceptEncoding, @Nullable @RequestHeader(EurekaAccept.HTTP_X_EUREKA_ACCEPT) String eurekaAccept, @Nullable @RequestParam("regions") String regionsStr) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @GetMapping("/apps/{appId}")
    public Mono<ResponseEntity<?>> getApplicationResource(@Nullable @RequestHeader(ACCEPT) String acceptHeader, @Nullable @RequestHeader(EurekaAccept.HTTP_X_EUREKA_ACCEPT) String eurekaAccept, @PathVariable String appId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @PostMapping("/apps/{appId}")
    public Mono<ResponseEntity<?>> addInstance(@Nullable @RequestHeader(PeerEurekaNode.HEADER_REPLICATION) String isReplication, @RequestBody String instanceInfoString, @PathVariable String appId) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @GetMapping("/apps/{appId}/{instanceId}")
    public Mono<ResponseEntity<?>> getInstanceInfo(@PathVariable String appId, @PathVariable String instanceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @PutMapping("/apps/{appId}/{instanceId}")
    public Mono<ResponseEntity<?>> renewLease(@Nullable @RequestHeader(PeerEurekaNode.HEADER_REPLICATION) String isReplication, @Nullable @RequestParam("overriddenstatus") String overriddenStatus, @Nullable @RequestParam String status, @Nullable @RequestParam String lastDirtyTimestamp, @PathVariable String appId, @PathVariable String instanceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @PutMapping("/apps/{appId}/{instanceId}/status")
    public Mono<ResponseEntity<?>> statusUpdate(@Nullable @RequestHeader(PeerEurekaNode.HEADER_REPLICATION) String isReplication, @Nullable @RequestParam("value") String newStatus, @Nullable @RequestParam String lastDirtyTimestamp, @PathVariable String appId, @PathVariable String instanceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @DeleteMapping("/apps/{appId}/{instanceId}/status")
    public Mono<ResponseEntity<?>> deleteStatusUpdate(@Nullable @RequestHeader(PeerEurekaNode.HEADER_REPLICATION) String isReplication, @Nullable @RequestParam("value") String newStatusValue, @Nullable @RequestParam String lastDirtyTimestamp, @PathVariable String appId, @PathVariable String instanceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @PutMapping("/apps/{appId}/{instanceId}/metadata")
    public Mono<ResponseEntity<?>> updateMetadata(ServerWebExchange serverWebExchange, @PathVariable String appId, @PathVariable String instanceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @DeleteMapping("/apps/{appId}/{instanceId}")
    public Mono<ResponseEntity<?>> cancelLease(@Nullable @RequestHeader(PeerEurekaNode.HEADER_REPLICATION) String isReplication, @PathVariable String appId, @PathVariable String instanceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @GetMapping("/instances/{id}")
    public Mono<ResponseEntity<?>> getById(@PathVariable String id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @GetMapping("/svips/{svipAddress}")
    public Mono<ResponseEntity<?>> secureVipStatusUpdate(@Nullable @RequestHeader(ACCEPT) String acceptHeader, @Nullable @RequestHeader(EurekaAccept.HTTP_X_EUREKA_ACCEPT) String eurekaAccept, @PathVariable String svipAddress) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @GetMapping("/vips/{vipAddress}")
    public Mono<ResponseEntity<?>> vipStatusUpdate(@Nullable @RequestHeader(ACCEPT) String acceptHeader, @Nullable @RequestHeader(EurekaAccept.HTTP_X_EUREKA_ACCEPT) String eurekaAccept, @PathVariable String vipAddress) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @GetMapping("/serverinfo/statusoverrides")
    public Mono<ResponseEntity<?>> getOverrides() throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @PutMapping("/asg/{asgName}/status")
    public Mono<ResponseEntity<?>> asgStatusUpdate(@Nullable @RequestHeader(PeerEurekaNode.HEADER_REPLICATION) String isReplication, @Nullable @RequestParam("value") String newStatus, @PathVariable String asgName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @PostMapping({ "/peerreplication/batch/", "/peerreplication/batch" })
    public Mono<ResponseEntity<?>> batchReplication(@RequestBody String replicationListString) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @RequiredArgsConstructor
    static class UriInfoAdapter implements UriInfo {

        private final ServerHttpRequest request;

        @Override
        public String getPath() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String getPath(boolean decode) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public List<PathSegment> getPathSegments() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public List<PathSegment> getPathSegments(boolean decode) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public URI getRequestUri() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public UriBuilder getRequestUriBuilder() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public URI getAbsolutePath() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public UriBuilder getAbsolutePathBuilder() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public URI getBaseUri() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public UriBuilder getBaseUriBuilder() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public MultivaluedMap<String, String> getPathParameters() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public MultivaluedMap<String, String> getPathParameters(boolean decode) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public MultivaluedMap<String, String> getQueryParameters() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public MultivaluedMap<String, String> getQueryParameters(boolean decode) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public List<String> getMatchedURIs() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public List<String> getMatchedURIs(boolean decode) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public List<Object> getMatchedResources() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public URI resolve(URI uri) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public URI relativize(URI uri) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
