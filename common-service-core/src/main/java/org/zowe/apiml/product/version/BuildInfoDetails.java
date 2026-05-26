/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.product.version;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class BuildInfoDetails {

    private static final String UNKNOWN = "Unknown";

    private final Properties build;

    private final Properties git;

    public BuildInfoDetails(Properties build, Properties git) {
        this.build = build;
        this.git = git;
    }

    public String getArtifact() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getVersion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getNumber() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Date getTime() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getMachine() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getCommitId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
