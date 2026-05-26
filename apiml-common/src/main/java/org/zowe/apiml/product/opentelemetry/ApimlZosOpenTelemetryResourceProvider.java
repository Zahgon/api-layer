/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.product.opentelemetry;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zowe.apiml.product.zos.ZosSystemInformation;
import javax.annotation.Nonnull;
import java.util.Optional;
import static org.zowe.apiml.product.zos.ZosSystemInformation.ZOS_ENVIRON;
import static org.zowe.apiml.product.zos.ZosSystemInformation.ZOS_JOB_NAME;
import static org.zowe.apiml.product.zos.ZosSystemInformation.ZOS_SMF_ID;
import static org.zowe.apiml.product.zos.ZosSystemInformation.ZOS_SYSNAME;
import static org.zowe.apiml.product.zos.ZosSystemInformation.ZOS_SYSPLEX;
import static org.zowe.apiml.product.zos.ZosSystemInformation.ZOS_USER_ID;

@Component
@RequiredArgsConstructor
@Profile("zos")
@Slf4j
public class ApimlZosOpenTelemetryResourceProvider extends ApimlOpenTelemetryResourceProvider {

    private final ZosSystemInformation zosSystemInformation;

    @Value("${otel.resource.attributes.deployment.environment.name:#{null}}")
    private String environmentName;

    @Value("${otel.resource.attributes.zos.sysplex.name:#{null}}")
    private String sysplexName;

    @Value("${otel.resource.attributes.mainframe.lpar.name:#{null}}")
    private String lparName;

    @Value("${otel.resource.attributes.zos.smf.id:#{null}}")
    private String smfId;

    @PostConstruct
    void afterPropertiesSet() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void attribute(AttributesBuilder attributesBuilder, @Nonnull String openTelemetryAttribute, String zosAttribute) {
        var zosAttributes = zosSystemInformation.get();
        var zosValue = zosAttributes.get(zosAttribute);
        if (zosValue != null && StringUtils.isNotBlank(zosValue.toString())) {
            log.debug(openTelemetryAttribute + " not provided in configuration, using z/OS obtained {}", zosValue);
            attributesBuilder.put(openTelemetryAttribute, zosValue.toString());
        } else {
            log.debug(openTelemetryAttribute + " not provided in configuration. Could not determine it from system");
        }
    }

    @SuppressWarnings("null")
    @Override
    @Nonnull
    protected Attributes internalCalculateAttributes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected String generateServiceName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
