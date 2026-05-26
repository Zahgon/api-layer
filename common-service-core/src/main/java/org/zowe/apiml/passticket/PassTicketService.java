/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.passticket;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.zowe.apiml.util.ClassOrDefaultProxyUtils;
import org.zowe.apiml.util.ObjectUtil;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class allows to get a PassTicket from SAF.
 */
@Slf4j
public class PassTicketService {

    private final IRRPassTicket irrPassTicket;

    @SuppressWarnings("unchecked")
    public PassTicketService() {
        this.irrPassTicket = ClassOrDefaultProxyUtils.createProxy(IRRPassTicket.class, "com.ibm.eserver.zos.racf.IRRPassTicket", DefaultPassTicketImpl::new, new ClassOrDefaultProxyUtils.ByMethodName<>("com.ibm.eserver.zos.racf.IRRPassTicketEvaluationException", IRRPassTicketEvaluationException.class, "getSafRc", "getRacfRc", "getRacfRsn"), new ClassOrDefaultProxyUtils.ByMethodName<>("com.ibm.eserver.zos.racf.IRRPassTicketGenerationException", IRRPassTicketGenerationException.class, "getSafRc", "getRacfRc", "getRacfRsn"));
    }

    // IRRPassTicket is not thread-safe, must be synchronized
    public synchronized void evaluate(String userId, String applId, String passTicket) throws PassTicketException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // IRRPassTicket is not thread-safe, must be synchronized
    public synchronized String generate(String userId, String applId) throws PassTicketException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isUsingSafImplementation() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void validateUserIdAndApplId(String userId, String applId) throws ApplicationNameNotProvidedException, UsernameNotProvidedException {
        if (StringUtils.isBlank(applId)) {
            throw new ApplicationNameNotProvidedException();
        }
        if (StringUtils.isBlank(userId)) {
            throw new UsernameNotProvidedException();
        }
    }

    public static class DefaultPassTicketImpl implements IRRPassTicket {

        private static int id = 0;

        public static final String ZOWE_DUMMY_USERID = "USER";

        public static final String ZOWE_DUMMY_PASS_TICKET_PREFIX = "ZOWE_DUMMY_PASS_TICKET";

        public static final String DUMMY_USER = "USER";

        public static final String UNKNOWN_USER = "UNKNOWN_USER";

        public static final String UNKNOWN_APPLID = "XBADAPPL";

        private final Map<UserApp, Set<String>> userAppToPasstickets = new HashMap<>();

        @Override
        public void evaluate(String userId, String applId, String passTicket) throws IRRPassTicketEvaluationException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String generate(String userId, String applId) throws IRRPassTicketGenerationException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @AllArgsConstructor
        @Value
        private static class UserApp {

            String userId;

            String applId;
        }
    }
}
