/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.product.logging;

import ch.qos.logback.core.boolex.PropertyConditionBase;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PropertyContainsCondition extends PropertyConditionBase {

    String key;

    String value;

    @Override
    public void start() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean evaluate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
