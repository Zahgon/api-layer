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

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class MaskingLogPatternLayout extends PatternLayout {

    private static final String MASK_VALUE = "***";

    private static final Pattern maskPatterns = new MaskPatternBuilder().addJsonValue("password").addJsonValue("newPassword").build();

    @Override
    public String doLayout(ILoggingEvent event) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected String maskMessage(String message) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static class MaskPatternBuilder {

        private final List<String> maskPatterns = new ArrayList<>();

        public MaskPatternBuilder add(String prefix, String capture) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public MaskPatternBuilder add(String prefix, String capture, String postfix) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public MaskPatternBuilder addJsonValue(String jsonKey, String... keys) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Pattern build() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
