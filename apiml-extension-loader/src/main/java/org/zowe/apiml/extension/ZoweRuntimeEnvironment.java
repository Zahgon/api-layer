/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.extension;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static java.util.Collections.emptyList;

@NoArgsConstructor
public class ZoweRuntimeEnvironment {

    static final String INSTALLED_EXTENSIONS_ENV = "ZWE_INSTALLED_COMPONENTS";

    static final String ENABLED_EXTENSIONS_ENV = "ZWE_ENABLED_COMPONENTS";

    static final String WORKSPACE_DIR_ENV = "ZWE_zowe_workspaceDirectory";

    static final String COMPONENTS_APP_SERVER_DIR_ENV = "ZWE_components_app_server_pluginsDir";

    static final String PLUGINS_DIR_ENV = "ZWED_pluginsDir";

    static final String EXTENSION_DIR_ENV = "ZWE_zowe_extensionDirectory";

    static final String PRIVATE_WORKSPACE_ENV = "ZWE_PRIVATE_WORKSPACE_ENV_DIR";

    public static ZoweRuntimeEnvironment defaultEnv() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Optional<String> getPluginsDir() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private List<String> getComponents(String env) {
        return Optional.ofNullable(env).map(installed -> installed.split(",")).map(Arrays::asList).orElse(emptyList());
    }

    List<String> getInstalledComponents() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    List<String> getEnabledComponents() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String getExtensionDirectory() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String getWorkspaceDirectory() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
