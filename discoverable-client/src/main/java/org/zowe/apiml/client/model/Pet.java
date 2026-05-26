/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.zowe.apiml.client.model.state.Existing;
import org.zowe.apiml.client.model.state.New;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.util.Objects;

/**
 * This is an example of a model class.
 */
@Schema(description = "A Pet Object")
public class Pet {

    @Null(groups = New.class, message = "Id should be null for pet creation")
    @NotNull(groups = Existing.class, message = "Id should be not null for pet update")
    @Schema(description = "The id is of the pet", example = "1")
    private Long id;

    @NotEmpty(groups = { New.class, Existing.class }, message = "Name should not be empty string")
    @Schema(description = "The name of the pet", example = "Falco")
    private String name;

    /**
     * Pet object
     *
     * @param id   Pet ID
     * @param name Pet name
     */
    public Pet(@JsonProperty("id") Long id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets Pet ID
     *
     * @return Pet ID
     */
    public Long getId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets Pet name
     *
     * @return Pet name
     */
    public String getName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Sets Pet ID
     *
     * @param id Pet ID
     */
    public void setId(Long id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Sets Pet name
     *
     * @param name Pet name
     */
    public void setName(String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
