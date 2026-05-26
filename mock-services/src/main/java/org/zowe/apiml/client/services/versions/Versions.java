/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.client.services.versions;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zowe.apiml.client.services.MockZosmfException;
import org.zowe.apiml.client.services.apars.Apar;
import org.zowe.apiml.client.services.apars.PHBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ToString
public class Versions {

    private final AvailableApars availableApars;

    private final Map<String, List<Apar>> aparsAppliedForVersion = new HashMap<>();

    @Autowired
    public Versions(@Value("${zosmf.username}") List<String> usernames, @Value("${zosmf.password}") List<String> passwords, @Value("${zosmf.jwtKeyStorePath}") String jwtKeyStorePath, @Value("${zosmf.timeout}") Integer timeout) {
        this.availableApars = new AvailableApars(usernames, passwords, jwtKeyStorePath, timeout);
        ArrayList<Apar> baseApars = new ArrayList<>();
        baseApars.add(new PHBase(usernames, passwords));
        aparsAppliedForVersion.put("2.3", baseApars);
        aparsAppliedForVersion.put("2.4", baseApars);
    }

    public List<Apar> baselineForVersion(String version) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<Apar> fullSetOfApplied(String baseVersion, List<String> appliedApars) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
