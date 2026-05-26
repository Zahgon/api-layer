/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.caching.service.infinispan;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.jgroups.Address;
import org.jgroups.protocols.SSL_KEY_EXCHANGE;
import org.jgroups.stack.IpAddress;
import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ApimlSslKeyExchange extends SSL_KEY_EXCHANGE {

    private static final ThreadLocal<List<Throwable>> EXCEPTIONS = new ThreadLocal<>();

    private static void addException(Exception e) {
        var exceptionList = EXCEPTIONS.get();
        if (exceptionList == null) {
            exceptionList = new ArrayList<>();
        }
        exceptionList.add(e);
        EXCEPTIONS.set(exceptionList);
    }

    String toString(Throwable t) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void printError(String message, List<Throwable> exceptionList) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void printError(String message) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void decorate(Exception e) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected SSLServerSocket createServerSocket() throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected SSLSocket createSocketTo(Address target) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected SSLSocket createSocketTo(IpAddress dest, SSLSocketFactory sslSocketFactory) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private SSLContext update(SSLContext context) {
        return new SSLContextWrapper(new SSLContextSpiWrapper(null, new SSLSocketFactoryWrapper(context.getSocketFactory()), new SSLServerSocketFactoryWrapper(context.getServerSocketFactory())), context);
    }

    @Override
    public void init() throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public SSL_KEY_EXCHANGE setClientSSLContext(SSLContext clientSslCtx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public SSL_KEY_EXCHANGE setServerSSLContext(SSLContext serverSslCtx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @RequiredArgsConstructor
    static class SSLSocketFactoryWrapper extends SSLSocketFactory {

        @Delegate
        private final SSLSocketFactory original;

        @Override
        public String[] getDefaultCipherSuites() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String[] getSupportedCipherSuites() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Socket createSocket(Socket s, String host, int port, boolean autoClose) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Socket createSocket(String host, int port) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    @RequiredArgsConstructor
    static class SSLServerSocketFactoryWrapper extends SSLServerSocketFactory {

        @Delegate
        private final SSLServerSocketFactory original;

        @Override
        public String[] getDefaultCipherSuites() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String[] getSupportedCipherSuites() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public ServerSocket createServerSocket(int port) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public ServerSocket createServerSocket(int port, int backlog) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public ServerSocket createServerSocket(int port, int backlog, InetAddress ifAddress) throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    @RequiredArgsConstructor
    static class SSLContextSpiWrapper extends SSLContextSpi {

        @Delegate
        private final SSLContextSpi original;

        private final SSLSocketFactory sslSocketFactory;

        private final SSLServerSocketFactory sslServerSocketFactory;

        @Override
        protected void engineInit(KeyManager[] km, TrustManager[] tm, SecureRandom sr) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected SSLSocketFactory engineGetSocketFactory() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected SSLServerSocketFactory engineGetServerSocketFactory() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected SSLEngine engineCreateSSLEngine() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected SSLEngine engineCreateSSLEngine(String host, int port) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected SSLSessionContext engineGetServerSessionContext() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected SSLSessionContext engineGetClientSessionContext() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    static class SSLContextWrapper extends SSLContext {

        SSLContextWrapper(SSLContextSpi contextSpi, SSLContext original) {
            super(contextSpi, original.getProvider(), original.getProtocol());
        }
    }
}
