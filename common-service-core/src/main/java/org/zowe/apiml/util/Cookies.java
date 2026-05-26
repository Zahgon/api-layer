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
 * Expose convenient methods to work with Cookies.
 *
 * As of HTTP/2 it is possible to have Cookies in multiple headers with the same name.
 */
public final class Cookies {

    private final HttpRequest request;

    private Cookies(HttpRequest request) {
        this.request = request;
    }

    public static Cookies of(HttpRequest request) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<HttpCookie> getAll() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<HttpCookie> get(String needle) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void set(HttpCookie cookie) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void remove(String cookie) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private List<HttpCookie> getAllCookiesFromHeader(Header header) {
        String headerValue = header.getValue();
        if (headerValue == null || headerValue.isEmpty()) {
            return new ArrayList<>();
        }
        List<HttpCookie> cookieList = new ArrayList<>();
        List<String> cookieStringList = Arrays.asList(headerValue.split(";"));
        cookieStringList.forEach(s -> cookieList.addAll(HttpCookie.parse(s)));
        return cookieList;
    }

    private Header getCookieHeader(List<HttpCookie> cookieList) {
        return new BasicHeader(HttpHeaders.COOKIE, cookieList.stream().map(HttpCookie::toString).collect(Collectors.joining(";")));
    }

    private List<Header> getHeader(String needle) {
        return Arrays.asList(request.getHeaders(needle));
    }
}
