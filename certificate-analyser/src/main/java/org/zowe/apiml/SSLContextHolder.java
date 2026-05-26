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

import javax.net.ssl.*;
import java.io.IOException;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SSLContextHolder {

    private final Stores stores;

    private SSLContext sslContext;

    private SSLContext sslContextWithKeystore;

    private SSLContextHolder(Stores stores) {
        this.stores = stores;
    }

    public Stores getStores() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SSLContext getSslContext() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SSLContext getSslContextWithKeystore() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static SSLContextHolder initSSLContextWithKeystore(Stores stores) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static SSLContextHolder initSSLContextWithoutKeystore(Stores stores) throws CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
