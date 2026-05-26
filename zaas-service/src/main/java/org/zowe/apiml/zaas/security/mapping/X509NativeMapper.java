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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.zowe.apiml.zaas.security.service.schema.source.AuthSource;
import org.zowe.apiml.zaas.security.service.schema.source.X509AuthSource;
import org.zowe.commons.usermap.CertificateResponse;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

@Slf4j
@RequiredArgsConstructor
@Component("x509Mapper")
@ConditionalOnProperty(value = "apiml.security.useInternalMapper", havingValue = "true")
public class X509NativeMapper implements AuthenticationMapper {

    private final NativeMapperWrapper nativeMapper;

    @Override
    public String mapToMainframeUserId(AuthSource authSource) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
