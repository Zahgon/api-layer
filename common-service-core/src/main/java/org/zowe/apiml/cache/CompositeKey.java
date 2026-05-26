/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.cache;

import org.apache.commons.lang3.StringUtils;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * CompositeKey replace default class using in cache org.springframework.cache.interceptor.SimpleKey. Original
 * implementation doesn't allow to get a part of key. It disallow to filter keys in cache etc.
 *
 * In additional, this implementation support make key for null values.
 */
public class CompositeKey implements Serializable {

    private static final long serialVersionUID = -5241946774009988317L;

    public static final CompositeKey EMPTY = new CompositeKey();

    private Serializable[] values;

    private int hashCode;

    public CompositeKey(Object... values) {
        if (values == null)
            values = new Object[0];
        this.values = Arrays.copyOf(values, values.length, Serializable[].class);
        this.hashCode = Arrays.deepHashCode(this.values);
    }

    public Object get(int i) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean equals(int i, Object o) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int size() {
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

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
