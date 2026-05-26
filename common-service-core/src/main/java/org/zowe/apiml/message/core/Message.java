/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.message.core;

import lombok.ToString;
import org.zowe.apiml.message.api.ApiMessage;
import org.zowe.apiml.message.api.ApiMessageView;
import org.zowe.apiml.message.template.MessageTemplate;
import org.zowe.apiml.util.ObjectUtil;
import org.apache.commons.text.StringEscapeUtils;
import java.util.Collections;
import java.util.IllegalFormatConversionException;
import java.util.MissingFormatArgumentException;

/**
 * Message creator immutable class
 */
@ToString
public final class Message {

    public static final String INVALID_KEY_MESSAGE = "org.zowe.apiml.common.invalidMessageKey";

    public static final String INVALID_KEY_MESSAGE_TEXT = "Internal error: Invalid message key '%s' provided. " + "No default message found. Please contact support of further assistance.";

    public static final String INVALID_MESSAGE_TEXT_FORMAT = "org.zowe.apiml.common.invalidMessageTextFormat";

    private final String requestedKey;

    private final MessageTemplate messageTemplate;

    private final Object[] messageParameters;

    private Message(String requestedKey, MessageTemplate messageTemplate, Object[] messageParameters) {
        this.requestedKey = requestedKey;
        this.messageTemplate = messageTemplate;
        this.messageParameters = messageParameters;
    }

    /**
     * Returns a {@link Message} object for the specified key after text and parameters validation.
     *
     * @param requestedKey      the message key.
     * @param messageTemplate   the messageTemplate.
     * @param messageParameters the object containing the message parameters.
     * @return {@link Message}
     */
    public static Message of(String requestedKey, MessageTemplate messageTemplate, Object[] messageParameters) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an {@link MessageType#ERROR} {@link Message} object indicating that the specified key is invalid.
     * The {@link MessageTemplate} for this message has key {@link #INVALID_KEY_MESSAGE}.
     *
     * @param requestedKey the invalid key.
     * @return {@link Message}
     */
    public static Message invalidKeyMessage(String requestedKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validate the message text and parameters returning them as formatted String.
     *
     * @param messageText       the message text.
     * @param messageParameters the object containing the message parameters.
     * @return a formatted String
     * @throws MissingFormatArgumentException   when the amount of parameters is less than required.
     * @throws IllegalFormatConversionException when format is not valid.
     */
    private static String validateMessageTextFormat(String messageText, Object[] messageParameters) {
        return String.format(messageText, messageParameters);
    }

    /**
     * Checks whether messageKey matches Message.INVALID_KEY_MESSAGE
     *
     * @param messageKey   the message key.
     * @param requestedKey the message key.
     * @param parameters   the object containing the message parameters.
     * @return the requested key as an array if it  matches Message.INVALID_KEY_MESSAGE. Otherwise, it returns parameters
     */
    private static Object[] validateParameters(String messageKey, String requestedKey, Object... parameters) {
        if (messageKey.equals(Message.INVALID_KEY_MESSAGE)) {
            return new Object[] { requestedKey };
        } else {
            return parameters;
        }
    }

    /**
     * Converts the text with {@link MessageTemplate} and Object[] message parameters.
     *
     * @return escaped characters in the converted text using HTML entities
     */
    public String getConvertedText() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @return a message in the format that can be printed to console as a single line or displayed to the user
     */
    public String mapToReadableText() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns UI model as a list of API Message. It can be used for REST APIs error messages
     *
     * @return {@link ApiMessageView}
     */
    public ApiMessageView mapToView() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns UI model as a single {@link ApiMessage}.
     *
     * @return {@link ApiMessage}
     */
    public ApiMessage mapToApiMessage() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns log message as a text.
     *
     * @return a String
     */
    public String mapToLogMessage() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @return a message template
     */
    public MessageTemplate getMessageTemplate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
