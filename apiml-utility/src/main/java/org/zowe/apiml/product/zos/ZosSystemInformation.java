/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.product.zos;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.zowe.apiml.util.ClassOrDefaultProxyUtils;
import java.util.Map;
import java.util.Optional;

@Component
public class ZosSystemInformation {

    public static final String ZOS_JOB_ID = "zos.jobid";

    public static final String ZOS_JOB_NAME = "zos.jobname";

    public static final String ZOS_USER_ID = "zos.userid";

    public static final String ZOS_PID = "zos.pid";

    public static final String ZOS_SYSNAME = "zos.sysname";

    public static final String ZOS_SYSCLONE = "zos.sysclone";

    public static final String ZOS_SYSPLEX = "zos.sysplex";

    public static final String ZOS_SMF_ID = "zos.smfid";

    public static final String ZOS_ENVIRON = "zos.environ";

    public static final String OS_NAME = "os.name";

    private ZUtil zUtil;

    public static boolean isRunningOnZos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Map<String, Object> get() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private String getSystemSymbol(String systemSymbol) {
        if (systemSymbol == null) {
            return "";
        }
        var symbol = zUtil.substituteSystemSymbols(systemSymbol);
        return Optional.ofNullable(symbol).filter(s -> !systemSymbol.equalsIgnoreCase(symbol)).orElse("");
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
