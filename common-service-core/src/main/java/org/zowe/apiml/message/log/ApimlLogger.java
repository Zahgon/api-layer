/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.message.log;

import org.zowe.apiml.message.core.Message;
import org.zowe.apiml.message.core.MessageService;
import org.zowe.apiml.message.core.MessageType;
import org.zowe.apiml.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Class which allows to control log messages through {@link MessageService}.
 * <b>Example:</b>
 * {@code
 * ApimlLogger logger = ApimlLogger.of(SampleClass.cass, messageService)
 * }
 */
public final class ApimlLogger {

    private final MessageService messageService;

    private final Logger logger;

    private static final Marker marker = MarkerFactory.getMarker("APIML-LOGGER");

    public ApimlLogger(Class<?> clazz, MessageService messageService) {
        this.messageService = messageService;
        this.logger = LoggerFactory.getLogger(clazz);
    }

    /**
     * Method which allows to create an ApimlLogger object.
     *
     * @param clazz          the class for which is logger used
     * @param messageService used to produce the message
     * @return {@link ApimlLogger}
     */
    public static ApimlLogger of(Class<?> clazz, MessageService messageService) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Method which returns ApimlLogger with null {@link MessageService}.
     * It is used for unit test environment.
     *
     * @return {@link ApimlLogger}
     */
    public static ApimlLogger empty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Method which allows to create a specific message with specific parameters and log it in its level type.
     *
     * @param key        of the message
     * @param parameters for message
     */
    public Message log(String key, Object... parameters) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Method which allows to log text in its level type, without passing message parameters.
     *
     * @param message the message
     */
    public void log(Message message) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Method which allows to log text in its level type.
     *
     * @param messageType type of the message
     * @param text        text for message
     * @param arguments   arguments for message text
     * @throws IllegalArgumentException when parameters are null
     */
    @SuppressWarnings("squid:S2629")
    public void log(MessageType messageType, String text, Object... arguments) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void logInvalidArguments(IllegalArgumentException e, Object... arguments) {
        if (logger.isDebugEnabled()) {
            logger.debug(marker, "Invalid log message cannot be logged: {}", arguments, e);
        } else {
            logger.warn(marker, "Invalid log message cannot be logged: {}, enable debug for stack trace: {}", arguments, e.getMessage());
        }
    }
}
