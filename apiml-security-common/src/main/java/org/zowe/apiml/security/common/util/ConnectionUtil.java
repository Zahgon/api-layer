/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.security.common.util;

import com.google.common.annotations.VisibleForTesting;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.zowe.apiml.product.web.HttpConfig;
import org.zowe.apiml.security.SecurityUtils;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientSecurityUtils;
import reactor.netty.tcp.SslProvider;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@UtilityClass
@Slf4j
public class ConnectionUtil {

    /**
     * @return io.netty.handler.ssl.SslContext for http client.
     */
    public SslContext getSslContext(HttpConfig config, boolean setKeystore) throws CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public HttpClient getHttpClient(HttpConfig config, HttpClient httpClient, boolean useClientCert) throws UnrecoverableKeyException, CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean isHostnameVerificationEnabled(HttpConfig config) {
        return config.isVerifySslCertificatesOfServices() && !config.isNonStrictVerifySslCertificatesOfServices();
    }

    @VisibleForTesting
    public X509KeyManager x509KeyManagerSelectedAlias(HttpConfig config, KeyManagerFactory keyManagerFactory) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static class X509KeyManagerSelectedAlias implements X509KeyManager {

        private final X509KeyManager originalKm;

        private final String keyAlias;

        public X509KeyManagerSelectedAlias(KeyManagerFactory keyManagerFactory, String keyAlias) {
            this.originalKm = (X509KeyManager) keyManagerFactory.getKeyManagers()[0];
            this.keyAlias = keyAlias;
        }

        @Override
        public String[] getClientAliases(String keyType, Principal[] issuers) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String[] getServerAliases(String keyType, Principal[] issuers) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public X509Certificate[] getCertificateChain(String alias) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public PrivateKey getPrivateKey(String alias) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
