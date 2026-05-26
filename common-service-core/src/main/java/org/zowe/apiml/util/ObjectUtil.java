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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
@UtilityClass
public class ObjectUtil {

    /**
     * Check whether the specified object reference is not null and
     * throws a {@link IllegalArgumentException} if it is.
     *
     * @param param   the object reference to check for nullity
     * @param message detail message to be used in the event
     */
    public static void requireNotNull(Object param, String message) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check whether the specified object reference is not empty (null or empty string) and
     * throws a {@link IllegalArgumentException} if it is.
     *
     * @param param   the object reference to check for empty
     * @param message detail message to be used in the event
     */
    public static void requireNotEmpty(String param, String message) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Map<String, String> getThreadContextMap(ThreadLocal<Map<String, String>> threadConfigurationContext) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Merges two Maps using deep merge method. The properties in secondMap have higher priority over defaultConfigurationMap,
     * i.e they will replace the value of property from defaultConfigurationMap having the same key.
     *
     * @param firstMap
     * @param secondMap
     * @return
     */
    public Map<String, Object> mergeConfigurations(Map<String, Object> firstMap, Map<String, Object> secondMap) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *  Deep merge of two maps. Drills down recursively into Container values - Map and List
     */
    private static Map<String, Object> mergeMapsDeep(Map<String, Object> map1, Map<String, Object> map2) {
        for (Map.Entry<String, Object> entry : map2.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (map1.get(key) instanceof Map && value instanceof Map) {
                map1.put(key, mergeMapsDeep((Map) map1.get(key), (Map) value));
            } else if (map1.get(key) instanceof List && value instanceof List) {
                Collection<Object> originalChild = (Collection<Object>) map1.get(key);
                for (Object each : (Collection<?>) value) {
                    if (!originalChild.contains(each)) {
                        originalChild.add(each);
                    }
                }
            } else {
                map1.put(key, value);
            }
        }
        return map1;
    }

    /**
     * Construct string describes name of method like <methodNames>(<types of arguments join with comma>)
     * @param method Instance of method to make description
     * @return String describing method with name and arguments types
     */
    public static String getMethodIdentifier(Method method) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
