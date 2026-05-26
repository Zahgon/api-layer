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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.zowe.apiml.client.exception.PetIdMismatchException;
import org.zowe.apiml.client.exception.PetNotFoundException;
import org.zowe.apiml.client.model.Pet;
import org.zowe.apiml.client.model.state.Existing;
import org.zowe.apiml.client.model.state.New;
import org.zowe.apiml.client.service.PetService;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an example of the REST API controller to implement GET, POST, PUT and DELETE methods.
 * It uses the {@link Pet} object as a model and {@link PetService} as a service.
 */
@RestController
@RequestMapping("/api/v1")
@Tag(description = "/api/v1/pets", name = "The pet API")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    /**
     * The getAllPets method lists all existing pets
     *
     * @return A list of all existing pets
     */
    @GetMapping(value = "/pets", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List all existing pets", description = "Returns information about all existing pets")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of pets") })
    public List<Pet> getAllPets() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The addPet method creates a new pet
     *
     * @param pet A pet object without an ID
     * @return A new pet object with an ID
     */
    @PostMapping(value = "/pets", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new pet", description = "Creates a new pet", security = { @SecurityRequirement(name = "ESM token") })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "New created pet"), @ApiResponse(responseCode = "401", description = "Authentication is required"), @ApiResponse(responseCode = "400", description = "Request object is not valid") })
    public Pet addPet(@Parameter(description = "Pet object that needs to be added", required = true) @Validated(value = { New.class }) @RequestBody Pet pet) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The gePetByID method finds a pet using an ID
     *
     * @param id The ID of an existing pet
     * @return A pet object
     */
    @GetMapping(value = "/pets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find pet by id", description = "Returns a single pet", security = { @SecurityRequirement(name = "ESM token") })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "401", description = "Authentication is required"), @ApiResponse(responseCode = "404", description = "The pet with id is not found.") })
    public Pet getPetById(@Parameter(description = "Pet id to return", required = true, example = "1") @PathVariable("id") Long id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The updatePetById method updates an existing pet
     *
     * @param id  The ID of an existing pet
     * @param pet The object with updated fields
     * @return The updated pet object
     */
    @PutMapping(value = "/pets/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing pet", description = "Change information for an existing pet", security = { @SecurityRequirement(name = "ESM token") })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Pet updated"), @ApiResponse(responseCode = "401", description = "Authentication is required"), @ApiResponse(responseCode = "404", description = "Pet not found") })
    public Pet updatePetById(@Parameter(description = "Pet id to update", required = true, example = "1") @PathVariable("id") Long id, @Parameter(description = "Pet object that needs to be updated", required = true) @Validated(value = { Existing.class }) @RequestBody Pet pet) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The deletePetById method deletes an existing pet
     *
     * @param id The ID of the existing pet to delete
     */
    @DeleteMapping(value = "/pets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a pet", description = "Removes an existing pet", security = { @SecurityRequirement(name = "ESM token") })
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Pet updated"), @ApiResponse(responseCode = "401", description = "Authentication is required"), @ApiResponse(responseCode = "403", description = "Forbidden"), @ApiResponse(responseCode = "404", description = "Pet not found") })
    public void deletePetById(@Parameter(description = "Pet id to delete", required = true, example = "1") @PathVariable("id") Long id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
