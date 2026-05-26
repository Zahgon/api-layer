/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.client.services.apars;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

@SuppressWarnings("squid:S1452")
public class PHBase extends FunctionalApar {

    public PHBase(List<String> usernames, List<String> passwords) {
        super(usernames, passwords);
    }

    @Override
    protected ResponseEntity<?> handleAuthenticationCreate(Map<String, String> headers, HttpServletResponse response) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected ResponseEntity<?> handleAuthenticationVerify(Map<String, String> headers, HttpServletResponse response) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected ResponseEntity<?> handleAuthenticationDefault(Map<String, String> headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected ResponseEntity<?> handleInformation(Map<String, String> headers, HttpServletResponse response) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected ResponseEntity<?> handleAuthenticationDelete(Map<String, String> headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected ResponseEntity<?> handleFiles(Map<String, String> headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("squid:S1192")
    private ResponseEntity<?> datasets() {
        return new ResponseEntity<>("{\n" + "  \"items\": [\n" + "    {\n" + "      \"dsname\": \"SYS1.PAGEDUMP.VMVD21M\"\n" + "    },\n" + "    {\n" + "      \"dsname\": \"SYS1.PAGEDUMP.VMVD22M\"\n" + "    },\n" + "    {\n" + "      \"dsname\": \"SYS1.PAGEDUMP.VMVD23M\"\n" + "    },\n" + "    {\n" + "      \"dsname\": \"SYS1.PAGEDUMP.VMVD24M\"\n" + "    },\n" + "    {\n" + "      \"dsname\": \"SYS1.PARMLIB\"\n" + "    },\n" + "    {\n" + "      \"dsname\": \"SYS1.PARMLIB.ARCHIVE\"\n" + "    },\n" + "    {\n" + "      \"dsname\": \"SYS1.PARMLIB.D200328\"\n" + "    },\n" + "    {\n" + "      \"dsname\": \"SYS1.PARMLIBN\"\n" + "    },\n" + "    {\n" + "      \"dsname\": \"SYS1.PDEFLIB\"\n" + "    },\n" + "    {\n" + "      \"dsname\": \"SYS1.PHELP\"\n" + "    },\n" + "    {\n" + "      \"dsname\": \"SYS1.PROCLIB\"\n" + "    },\n" + "    {\n" + "      \"dsname\": \"SYS1.PROCLIBX\"\n" + "    },\n" + "    {\n" + "      \"dsname\": \"SYS1.PSEGLIB\"\n" + "    }\n" + "  ],\n" + "  \"returnedRows\": 13,\n" + "  \"JSONversion\": 1\n" + "}", HttpStatus.OK);
    }

    private ResponseEntity<?> validInfo() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
