/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.util;

import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.message.BasicHeader;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is meant for wrapping HttpRequest and exposing convenience mehtods
 * for working with headers and cookies
 *
 * Support for duplicate headers with same name is not present and can be added in future
 */
public class RequestUtils {

    private final HttpRequest request;

    private RequestUtils(HttpRequest request) {
        this.request = request;
    }

    public static RequestUtils of(HttpRequest request) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<Header> getHeaders() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private List<Header> getAllHeaders() {
        return new ArrayList<>(Arrays.asList(request.getHeaders()));
    }

    public List<Header> getHeader(String needle) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setHeader(Header newHeader) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void removeHeader(String needle) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<HttpCookie> getAllCookies() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private List<HttpCookie> getAllCookiesFromHeader(Header header) {
        if (!header.getName().equalsIgnoreCase(HttpHeaders.COOKIE)) {
            throw new IllegalArgumentException("argument is not a cookie header");
        }
        String headerValue = header.getValue();
        if (headerValue == null || headerValue.isEmpty()) {
            return new ArrayList<>();
        } else {
            List<HttpCookie> cookieList = new ArrayList<>();
            List<String> cookieStringList = Arrays.asList(header.getValue().split(";"));
            cookieStringList.forEach(s -> cookieList.addAll(HttpCookie.parse(s)));
            return cookieList;
        }
    }

    public List<HttpCookie> getCookie(String needle) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setCookie(HttpCookie cookie) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Header getCookieHeader(List<HttpCookie> cookieList) {
        return new BasicHeader(HttpHeaders.COOKIE, cookieList.stream().map(HttpCookie::toString).collect(Collectors.joining(";")));
    }

    public void removeCookie(String cookie) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
