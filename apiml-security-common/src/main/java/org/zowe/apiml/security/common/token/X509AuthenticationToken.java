/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.security.common.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Objects;

public class X509AuthenticationToken extends AbstractAuthenticationToken {

    private final X509Certificate[] x509Certificates;

    public X509AuthenticationToken(Collection<GrantedAuthority> authorities, X509Certificate[] x509Certificates) {
        super(authorities);
        this.x509Certificates = x509Certificates;
    }

    public X509AuthenticationToken(X509Certificate[] x509Certificates) {
        this(null, x509Certificates);
    }

    @Override
    public Object getCredentials() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Object getPrincipal() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
