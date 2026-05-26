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

import io.netty.channel.ChannelOption;
import io.netty.channel.ConnectTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.config.HttpClientProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.NettyRoutingFilter;
import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.web.server.ServerWebExchange;
import org.zowe.apiml.security.common.error.ServiceNotAccessibleException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import static org.zowe.apiml.constants.ApimlConstants.HTTP_CLIENT_USE_CLIENT_CERTIFICATE;

@Slf4j
public class NettyRoutingFilterApiml extends NettyRoutingFilter {

    private final HttpClient httpClientNoCert;

    private final HttpClient httpClientClientCert;

    @Value("${apiml.connection.timeout:60000}")
    private int requestTimeout;

    public NettyRoutingFilterApiml(HttpClient httpClientNoCert, HttpClient httpClientClientCert, ObjectProvider<List<HttpHeadersFilter>> headersFiltersProvider, HttpClientProperties properties) {
        super(null, headersFiltersProvider, properties);
        this.httpClientNoCert = httpClientNoCert;
        this.httpClientClientCert = httpClientClientCert;
    }

    static Integer getInteger(Object connectTimeoutAttr) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected HttpClient getHttpClient(Route route, ServerWebExchange exchange) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static boolean isServiceUnavailable(Throwable error) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
