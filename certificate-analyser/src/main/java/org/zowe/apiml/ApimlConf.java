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

import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(version = { "Versioned Command 1.0", "JVM: ${java.version} (${java.vendor} ${java.vm.name} ${java.vm.version})", "OS: ${os.name} ${os.version} ${os.arch}" })
public class ApimlConf implements Config {

    @Option(names = { "-k", "--keystore" }, description = "Path to keystore file or keyring. When using keyring, pass -Djava.protocol.handler.pkgs=com.ibm.crypto.provider in command line.")
    private String keyStore;

    @Option(names = { "-t", "--truststore" }, description = "Path to truststore file or keyring")
    private String trustStore;

    @Option(names = { "-tp", "--trustpasswd" }, arity = "0..1", interactive = true, description = "Truststore password")
    private String trustPasswd;

    @Option(names = { "-kp", "--keypasswd" }, arity = "0..1", interactive = true, description = "Keystore password")
    private String keyPasswd;

    @Option(names = { "-tt", "--truststoretype" }, description = "Truststore type, default is PKCS12")
    private String trustStoreType;

    @Option(names = { "-kt", "--keystoretype" }, description = "Keystore type, default is PKCS12")
    private String keyStoreType = "PKCS12";

    @Option(names = { "-a", "--keyalias" }, description = "Alias under which this key is stored")
    private String keyAlias;

    @Option(names = { "-r", "--remoteurl" }, description = "URL of service to be verified")
    private String remoteUrl;

    @Option(names = { "-l", "--local" }, description = "Do SSL handshake on localhost")
    private boolean doLocalHandshake;

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "Display a help message")
    private boolean helpRequested = false;

    @Option(names = { "-c", "--clientcert" }, description = "Add client certificate to HTTPS request")
    private boolean clientCertAuth;

    @Option(names = { "-d", "--hostnames" }, split = ",", description = "All hostnames that should match with the server certificate separated by comma")
    private String[] requiredHostNames;

    @Option(names = { "--tlsversion" }, description = "TLS version, default is TLSv1.2")
    private String tlsVersion = "TLSv1.2";

    public String getKeyStore() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getTrustStore() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getTrustPasswd() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getKeyPasswd() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getTrustStoreType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getKeyStoreType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getKeyAlias() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getRemoteUrl() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isHelpRequested() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isDoLocalHandshake() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isClientCertAuth() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private String defaultValue(String value, String defaultVal) {
        return value != null ? value : defaultVal;
    }

    public String[] getRequiredHostNames() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getTlsVersion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
