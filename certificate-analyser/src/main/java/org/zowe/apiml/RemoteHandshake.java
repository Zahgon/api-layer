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

import javax.net.ssl.SSLHandshakeException;
import java.net.MalformedURLException;
import java.net.URL;

//ignoring the System.out System.err warinings
@SuppressWarnings("squid:S106")
public class RemoteHandshake implements Verifier {

    private SSLContextHolder sslContextHolder;

    private HttpClient httpClient;

    public RemoteHandshake(SSLContextHolder sslContextHolder, HttpClient httpClient) {
        this.sslContextHolder = sslContextHolder;
        this.httpClient = httpClient;
    }

    @Override
    public boolean verify() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
