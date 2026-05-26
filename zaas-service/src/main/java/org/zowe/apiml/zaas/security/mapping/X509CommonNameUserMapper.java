/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zaas.security.mapping;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.zowe.apiml.zaas.security.service.schema.source.AuthSource;
import org.zowe.apiml.zaas.security.service.schema.source.X509AuthSource;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import java.security.cert.X509Certificate;

/**
 * Certificate mapper that allows to return user id of the provided x509 certificate
 * This mapper will be executed when ZSS is not used
 */
@Component("x509Mapper")
@ConditionalOnExpression("T(org.apache.commons.lang3.StringUtils).isEmpty('${apiml.security.x509.externalMapperUrl:}') && '${apiml.security.useInternalMapper:false}' == 'false' && '${apiml.security.useDummyCNMapper:false}' == 'false'")
public class X509CommonNameUserMapper implements AuthenticationMapper {

    /**
     * Maps certificate to user id
     *
     * @param authSource X509 certificate as a source of authentication
     * @return the user
     */
    @Override
    public String mapToMainframeUserId(AuthSource authSource) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return the LDAP name from the given distinguished name
     *
     * @param dn distinguished name
     * @return LDAP name
     */
    public LdapName getLdapName(String dn) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
