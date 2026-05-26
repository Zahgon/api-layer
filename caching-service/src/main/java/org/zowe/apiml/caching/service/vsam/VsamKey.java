/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.caching.service.vsam;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.zowe.apiml.caching.model.KeyValue;
import org.zowe.apiml.caching.service.vsam.config.VsamConfig;
import java.io.UnsupportedEncodingException;

/**
 * Key for VSAM record
 * Composed of ServiceId hash : Record key hash
 *
 * Takes configuration from {@link VsamConfig}
 */
public class VsamKey {

    private final VsamConfig config;

    @Getter
    int keyLength;

    public VsamKey(VsamConfig config) {
        if (config.getKeyLength() < 23) {
            throw new IllegalArgumentException("VsamKey cannot have length smaller than 23 characters");
        }
        this.config = config;
        this.keyLength = config.getKeyLength();
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getKey(String serviceId, String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getKey(String serviceId, KeyValue keyValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] getKeyBytes(String serviceId, String key) throws UnsupportedEncodingException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] getKeyBytes(String serviceId, KeyValue keyValue) throws UnsupportedEncodingException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getKeySidOnly(String serviceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] getKeyBytesSidOnly(String serviceId) throws UnsupportedEncodingException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
