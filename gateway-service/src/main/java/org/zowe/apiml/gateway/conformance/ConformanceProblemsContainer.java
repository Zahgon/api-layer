/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.conformance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.text.StrSubstitutor;
import org.zowe.apiml.message.api.ApiMessage;
import java.util.*;

/**
 * Java class that is used to keep track of found conformance issues
 */
@EqualsAndHashCode(callSuper = true)
public class ConformanceProblemsContainer extends HashMap<String, Set<String>> {

    private final String serviceId;

    private static final String RESPONSE_MESSAGE_TEMPLATE = """
            {
                "messageAction": "${messageAction}",
                "messageContent": {
                    "The service ${serviceId} is not conformant": ${messageContent}
                },
                "messageKey": "${messageKey}",
                "messageNumber": "${messageNumber}",
                "messageReason": "${messageReason}",
                "messageType": "${messageType}"
            }
        """;

    ConformanceProblemsContainer(String serviceId) {
        super();
        this.serviceId = serviceId;
    }

    public void add(String key, List<String> values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void add(String key, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String createBadRequestAPIResponseBody(String key, ApiMessage correspondingAPIMessage) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
