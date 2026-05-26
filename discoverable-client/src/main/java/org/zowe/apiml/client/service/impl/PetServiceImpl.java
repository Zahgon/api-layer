/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.client.service.impl;

import org.zowe.apiml.client.exception.PetNotFoundException;
import org.zowe.apiml.client.model.Pet;
import org.zowe.apiml.client.service.PetService;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This is an example of basic implementation of {@link PetService}.
 * It uses {@link ArrayList} as storage for {@link Pet} objects.
 */
@Service("petService")
public class PetServiceImpl implements PetService {

    private final List<Pet> pets;

    private final AtomicLong counter;

    public PetServiceImpl() {
        this.pets = new ArrayList<>();
        counter = new AtomicLong(0);
    }

    /**
     * Initial setup of pet list for the integration tests
     */
    @PostConstruct
    public void init() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Adds pet to the list
     * @param pet Pet with set ID
     * @return Pet
     */
    @Override
    public Pet save(Pet pet) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds Pet by ID
     * @param id Pet ID
     * @return Pet
     */
    @Override
    public Pet getById(Long id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets all pets from the list
     * @return list of pets
     */
    @Override
    public List<Pet> getAll() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Updates Pet by ID
     * @param pet Pet found by ID
     * @return Pet or null if pet is not found
     */
    @Override
    public Pet update(Pet pet) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Deletes pet by ID
     * @param id Pet ID
     */
    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
