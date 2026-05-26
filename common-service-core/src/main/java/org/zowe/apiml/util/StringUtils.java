/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.util;

import lombok.experimental.UtilityClass;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class StringUtils {

    @SuppressWarnings("squid:S4784")
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("\\$\\{([^}]*)\\}");

    /**
     * Remove from parameter 'input' the first and the last occurrence of parameter 'str'
     * @param input
     * @param str
     * @return the input string without the initial and final occurrence of str
     */
    public static String removeFirstAndLastOccurrence(String input, String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Prepend parameter 'subStr' to parameter 'input'
     *
     * @param uri
     * @param subStr
     * @return
     */
    public static String prependSubstring(String uri, String subStr) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * If 'input' is not already prefixed with 'subStr', prepend parameter 'subStr' to parameter 'input'
     *
     * @param uri
     * @param subStr
     * @param checkAlreadyPrepended
     * @return result string prepended with subStr
     */
    public static String prependSubstring(String uri, String subStr, boolean checkAlreadyPrepended) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * If 'input' is not already prefixed with 'subStr', prepend parameter 'subStr' to parameter 'input'.
     * If 'input' has leading or trailing whitespaces, trim them first if 'shouldTrimWhitespaceFirst' is true
     *
     * @param uri
     * @param subStr
     * @param checkAlreadyPrepended
     * @param shouldTrimWhitespaceFirst
     * @return
     */
    public static String prependSubstring(String uri, String subStr, boolean checkAlreadyPrepended, boolean shouldTrimWhitespaceFirst) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * If 'input' ends with 'subStr', remove this occurrence of 'subStr' only
     *
     * @param input
     * @param subStr
     * @return
     */
    public static String removeLastOccurrence(String input, String subStr) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Substitutes properties values if corresponding properties keys are found in the expression.
     *
     * @param expression
     * @param properties
     * @return
     */
    public static String resolveExpressions(String expression, Map<String, String> properties) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
