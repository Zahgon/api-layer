/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.zowe.apiml.constants.ApimlConstants;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public class ErrorHeaders implements ClientResponse.Headers {

    private final HttpHeaders httpHeaders = new HttpHeaders();

    public ErrorHeaders() {
    }

    public ErrorHeaders(String message) {
        httpHeaders.add(ApimlConstants.AUTH_FAIL_HEADER, message);
    }

    @Override
    public OptionalLong contentLength() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Optional<MediaType> contentType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public List<String> header(String headerName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public HttpHeaders asHttpHeaders() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private OptionalLong toOptionalLong(long value) {
        return (value != -1 ? OptionalLong.of(value) : OptionalLong.empty());
    }
}
