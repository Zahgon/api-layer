/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Optional;

public class Main {

    // Replace with your API URL
    private static final String API_URL = Optional.ofNullable(System.getenv("API_URL")).orElse("https://localhost:8080") + "/gateway/api/v1/auth/login";

    // Replace with your client cert path
    private static final String CLIENT_CERT_PATH = Optional.ofNullable(System.getenv("CLIENT_CERT_PATH")).orElse("client-cert.p12");

    // Replace with your cert password
    private static final String CLIENT_CERT_PASSWORD = Optional.ofNullable(System.getenv("CLIENT_CERT_PASSWORD")).orElse("password");

    // Replace with your signed client cert alias
    private static final String CLIENT_CERT_ALIAS = Optional.ofNullable(System.getenv("CLIENT_CERT_ALIAS")).orElse("apimtst");

    // Replace with your private key alias
    private static final String PRIVATE_KEY_ALIAS = Optional.ofNullable(System.getenv("PRIVATE_KEY_ALIAS")).orElse("apimtst");

    public static void main(String[] args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
