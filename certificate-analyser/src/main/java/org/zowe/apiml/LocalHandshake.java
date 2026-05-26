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

import org.zowe.apiml.server.SocketServer;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLServerSocket;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStoreException;

//ignoring the System.out System.err warinings
@SuppressWarnings("squid:S106")
public class LocalHandshake implements Verifier {

    private SSLContextHolder sslContextHolder;

    private HttpClient client;

    public LocalHandshake(SSLContextHolder sslContextHolder, HttpClient client) {
        this.sslContextHolder = sslContextHolder;
        this.client = client;
    }

    @Override
    public boolean verify() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
