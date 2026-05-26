/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zaas.security.webfinger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StaticWebFingerProvider implements WebFingerProvider {

    @Value("${apiml.security.webfinger.fileLocation:}")
    private String webfingerDefinition;

    private static final YAMLFactory YAML_FACTORY = new YAMLFactory();

    @Override
    public WebFingerResponse getWebFingerConfig(String clientId) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isEnabled() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
