/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.product.opentelemetry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.server.ServerWebExchange;
import org.zowe.apiml.auth.AuthenticationScheme;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public final class OtelRequestContext {

    public static final String OTEL_CONTEXT = "otel-context";

    private static final String OK = "OK";

    private static final String ERROR = "ERROR";

    public static final String BASIC_AUTH_TYPE = "BASIC";

    private static final String OTEL_ATTRIBUTE_METHOD = "http.request.method";

    private static final String OTEL_ATTRIBUTE_SCHEME = "url.scheme";

    private static final String OTEL_ATTRIBUTE_PATH = "url.path";

    private static final String OTEL_ATTRIBUTE_RESPONSE_CODE = "service.response_code";

    private static final String OTEL_ATTRIBUTE_SERVICE_ID = "service.id";

    private static final String OTEL_ATTRIBUTE_INSTANCE_ID = "service.instance.id";

    private static final String OTEL_ATTRIBUTE_AUTH_METHOD = "auth.service.auth.method";

    private static final String OTEL_ATTRIBUTE_AUTH_SOURCE_TYPE = "auth.method";

    private static final String OTEL_ATTRIBUTE_AUTH_STATUS = "auth.status";

    private static final String OTEL_ATTRIBUTE_AUTH_ERROR_TYPE = "auth.error.type";

    private static final String OTEL_ATTRIBUTE_AUTH_ERROR_MESSAGE = "auth.error.message";

    private static final String OTEL_ATTRIBUTE_USER_ID = "user.id";

    private static final String OTEL_ATTRIBUTE_DISTRIBUTED_USER_ID = "user.distributed.id";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private AttributesBuilder attributesBuilder = Attributes.builder();

    // this mart is for other codes to mark a specific call of creating. By marking a specific version could be selected
    private AtomicBoolean marked = new AtomicBoolean(false);

    private OtelRequestContext() {
    }

    public static OtelRequestContext of(ServerWebExchange exchange) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean mark() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OtelRequestContext put(final String key, final String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OtelRequestContext method(HttpMethod httpMethod) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OtelRequestContext scheme(String scheme) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OtelRequestContext path(String path) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OtelRequestContext responseCode(int status) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OtelRequestContext serviceId(String serviceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OtelRequestContext instanceId(String instanceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OtelRequestContext authMethod(AuthenticationScheme authenticationScheme) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OtelRequestContext authMethod(String authenticationScheme) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OtelRequestContext authenticationFailed() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OtelRequestContext authErrorType(String authErrorType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OtelRequestContext authErrorMessage(String authErrorMessage) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OtelRequestContext authenticationSuccess() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OtelRequestContext userId(String userId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OtelRequestContext distributedIds(List<String> distributedIds) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OtelRequestContext authSourceType(String authSourceType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @VisibleForTesting
    ObjectMapper getObjectMapper() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected Logger getOtelLogger() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void issue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
