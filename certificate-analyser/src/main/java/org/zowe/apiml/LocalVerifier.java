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

import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//ignoring the System.out System.err warinings
@SuppressWarnings("squid:S106")
public class LocalVerifier implements Verifier {

    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss. SSSZ");

    private static final String OID_SERVER_AUTH = "1.3.6.1.5.5.7.3.1";

    private static final String OID_CLIENT_AUTH = "1.3.6.1.5.5.7.3.2";

    private static final String OID_SHA256_RSA = "1.2.840.113549.1.1.11";

    private final Stores stores;

    private final String[] requiredHostnames;

    public LocalVerifier(Stores stores, String[] requiredHostnames) {
        this.stores = stores;
        this.requiredHostnames = requiredHostnames;
    }

    public boolean verify() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean verifyExpiration(X509Certificate serverCert) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean isMatching(String hostname, String cn, List<String> alternativeNames) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean verifyHostnames(X509Certificate serverCert) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean verifyServer(List<String> extendedKeyUsage) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean verifyX509(List<String> extendedKeyUsage) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean verifyJwt(X509Certificate serverCert) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean verifyCertificate(String keyAlias) throws KeyStoreException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
