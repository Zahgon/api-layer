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

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import java.net.HttpCookie;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A utility class for Cookies administration
 */
@Slf4j
@UtilityClass
public final class CookieUtil {

    public static class CookieHeaderBuilder {

        private final String name;

        private final String value;

        private String comment;

        private String path = "/";

        private String sameSite = "Strict";

        private Integer maxAge = null;

        private boolean isHttpOnly = false;

        private boolean isSecure = false;

        public CookieHeaderBuilder(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public CookieHeaderBuilder comment(String comment) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public CookieHeaderBuilder path(String comment) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public CookieHeaderBuilder sameSite(String sameSite) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public CookieHeaderBuilder maxAge(Integer maxAge) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public CookieHeaderBuilder httpOnly(boolean isHttpOnly) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public CookieHeaderBuilder secure(boolean isSecure) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public String build() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * It replaces or add cookie into header string value (see header with name "Cookie").
     *
     * @param cookieHeader original header string value
     * @param name         name of cookie to add or replace
     * @param value        value of cookie to add or replace
     * @return new header string, which contains a new cookie
     */
    public static String setCookie(String cookieHeader, String name, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * It remove cookie from header value string (see header with name "Cookie") if exists. In case of missing
     * cookie, it return original header value string.
     *
     * @param cookieHeader original header string value
     * @param name         name of cookie to remove
     * @return new header string, without a specified cookie
     */
    public static String removeCookie(String cookieHeader, String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static List<HttpCookie> parseCookieSuppress(String cookie) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Read cookie from HTTP header and return it as stream, otherwise return empty list.
     * @param httpHeaders the HTTP header
     * @return stream of HttpCookie
     */
    public static Stream<HttpCookie> readCookies(HttpHeaders httpHeaders) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
