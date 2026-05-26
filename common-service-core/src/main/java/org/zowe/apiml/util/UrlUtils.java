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
import java.net.*;
import java.security.SecureRandom;
import java.util.Arrays;

@UtilityClass
public class UrlUtils {

    /**
     * Remove slashes from input string parameter
     *
     * @param string input parameter
     * @return input without removed trailing slashes.
     */
    public String trimSlashes(String string) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Substitute '\\W' with '-' in the input string and return the result
     *
     * @param url - input url to be encoded by the hard coded character substitution
     * @return An url string with any non alpha-numeric characters substituted by '-'
     */
    public String getEncodedUrl(String url) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Removes leading and trailing slashes ("/") from input string
     *
     * @param uri an URI string to trim slashes from
     * @return the trimmed URI string
     */
    public String removeFirstAndLastSlash(String uri) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Prepends a slash ("/") to input string
     *
     * @param uri An URI to prepend a '/' to.
     * @return the modified URI string
     */
    public String addFirstSlash(String uri) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Removes last slash ("/") from input string
     * @param uri an URI string to trim last slash from
     * @return the modified URI
     */
    public String removeLastSlash(String uri) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds IP address hostname provided by fqdn string.
     *
     * @param fqdn a Fully Qualified Domain Name to resolve as IP address
     * @return the resolved IP address or 'null'
     */
    public String getHostIPAddress(String fqdn) throws UnknownHostException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @param urlString is a string representing a URL
     * @return IP address of the host domain name provided by FQDN
     * @throws MalformedURLException if urlString parameter is not valid URL
     * @throws UnknownHostException if host name part of the URL is not resolvable
     */
    public String getIpAddressFromUrl(String urlString) throws MalformedURLException, UnknownHostException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @param urlString is a string representing a URL
     * @return true if provided string is actually valid URL format. False otherwise
     */
    public boolean isValidUrl(String urlString) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
