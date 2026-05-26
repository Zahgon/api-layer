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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stores {

    private static final Pattern KEYRING_PATTERN = Pattern.compile("^(safkeyring[^:]*):/{2,4}([^/]+)/([^/]+)$");

    private KeyStore keyStore;

    private KeyStore trustStore;

    private final Config conf;

    private Map<String, Certificate> caList;

    public Stores(Config conf) {
        this.conf = conf;
        init();
    }

    public static boolean isKeyring(String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String formatKeyringUrl(String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void init() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void initTruststore() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException {
        if (conf.getTrustStore() == null) {
            System.out.println("No keystore specified, will use empty.");
            try {
                this.trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            } catch (KeyStoreException e) {
                System.err.println(e.getMessage());
            }
            return;
        }
        try (InputStream trustStoreIStream = new FileInputStream(conf.getTrustStore())) {
            this.trustStore = readKeyStore(trustStoreIStream, conf.getTrustPasswd().toCharArray(), conf.getTrustStoreType());
        }
    }

    private void initKeystore() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException {
        if (conf.getKeyStore() == null) {
            System.out.println("No keystore specified, will use empty.");
            try {
                this.keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            } catch (KeyStoreException e) {
                System.err.println(e.getMessage());
            }
            return;
        }
        if (isKeyring(conf.getKeyStore())) {
            try (InputStream keyringIStream = keyRingUrl(conf.getKeyStore()).openStream()) {
                this.keyStore = readKeyStore(keyringIStream, conf.getKeyPasswd().toCharArray(), conf.getKeyStoreType());
                this.trustStore = this.keyStore;
            } catch (Exception e) {
                throw new StoresNotInitializeException(e.getMessage());
            }
        } else {
            try (InputStream keyStoreIStream = new FileInputStream(conf.getKeyStore())) {
                this.keyStore = readKeyStore(keyStoreIStream, conf.getKeyPasswd().toCharArray(), conf.getKeyStoreType());
            }
        }
    }

    public Map<String, Certificate> getListOfCertificates() throws KeyStoreException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public X509Certificate getX509Certificate(String alias) throws KeyStoreException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Certificate[] getServerCertificateChain(String alias) throws KeyStoreException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static KeyStore readKeyStore(InputStream is, char[] pass, String type) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public KeyStore getKeyStore() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public KeyStore getTrustStore() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Config getConf() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static URL keyRingUrl(String uri) throws MalformedURLException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
