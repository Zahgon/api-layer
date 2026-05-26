/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.client.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zowe.apiml.client.model.Registered;
import org.zowe.apiml.client.service.ApiMediationClientService;
import org.zowe.apiml.exception.ServiceDefinitionException;

@RestController
@RequestMapping("/api/v1/apiMediationClient")
@Tag(description = "/api/v1/apiMediationClient", name = "API Mediation Client test call")
public class ApiMediationClientTestController {

    private final ApiMediationClientService apiMediationClientService;

    public ApiMediationClientTestController(ApiMediationClientService apiMediationClientService) {
        this.apiMediationClientService = apiMediationClientService;
    }

    @PostMapping
    @Operation(summary = "Forward registration to discovery service via API mediation client")
    public ResponseEntity<String> forwardRegistration() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @DeleteMapping
    @Operation(summary = "Forward un-registration to discovery service via API mediation client")
    public ResponseEntity<String> forwardUnRegistration() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @GetMapping
    @Operation(summary = "Indicate if registration with discovery service via API mediation client was successful")
    public Registered isRegistered() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
