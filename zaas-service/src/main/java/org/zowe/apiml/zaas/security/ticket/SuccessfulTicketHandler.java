/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.zaas.security.ticket;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.zowe.apiml.message.api.ApiMessageView;
import org.zowe.apiml.message.core.MessageService;
import org.zowe.apiml.passticket.*;
import org.zowe.apiml.security.common.token.TokenAuthentication;
import org.zowe.apiml.ticket.TicketRequest;
import org.zowe.apiml.ticket.TicketResponse;
import java.io.IOException;

/**
 * Handles the successful /ticket request
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SuccessfulTicketHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper mapper;

    private final PassTicketService passTicketService;

    private final MessageService messageService;

    /**
     * Set the ticket response containing PassTicket
     *
     * @param request        the http request
     * @param response       the http response
     * @param authentication the successful authentication
     */
    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private TicketResponse getTicketResponse(HttpServletRequest request, Authentication authentication) throws PassTicketException {
        TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;
        String userId = tokenAuthentication.getPrincipal();
        String applicationName;
        try {
            applicationName = mapper.readValue(request.getInputStream(), TicketRequest.class).getApplicationName();
        } catch (IOException e) {
            throw new ApplicationNameNotProvidedException("Ticket object has wrong format.");
        }
        String ticket = passTicketService.generate(userId, applicationName);
        return TicketResponse.builder().token(tokenAuthentication.getCredentials()).userId(userId).applicationName(applicationName).ticket(ticket).build();
    }
}
