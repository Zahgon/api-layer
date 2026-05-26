/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.security.common.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.zowe.apiml.security.common.auth.saf.SafResourceAccessSaf;
import org.zowe.apiml.security.common.auth.saf.SafResourceAccessVerifying;
import org.zowe.apiml.util.ClassOrDefaultProxyUtils;
import jakarta.annotation.PostConstruct;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Proxy;

/**
 * RauditxService offer issuing SMF record #83 via RauditX macro. Those records should be used to audit user action
 * that does not call on the end ESM macro to verify credentials (they are audited in the ESM). It means this audit
 * records should be generated for example when a token allows to generate another token. In this case ESM is not
 * called.
 *
 * To use this feature the calling userid must have READ authority to the IRR.RAUDITX profile in the FACILITY class.
 * On the initialization of this bean the privileges are checked and could write a warning message in the console log.
 *
 * In case this feature is not available (not enough credentials, the service runs off z/OS) it will not throw any
 * exception. The code should not check the possibility to issue SMF record neither.
 *
 * Example:
 *
 * <pre>
 * &#64;Service
 * &#64;RequiredArgsConstructor
 * class AuditedClass {
 *
 *  private final RauditxService rauditxService;
 *
 *  void doSomething() {
 *      ...
 *      rauditxService.builder().
 *          .userId("userId")
 *          .messageSegment("An attempt to generate PAT")
 *          .alwaysLogSuccesses()
 *          .alwaysLogFailures()
 *          .issue();
 *      ...
 *  }
 *
 * }
 * </pre>
 */
@Slf4j
@Service
public class RauditxService {

    // documented types at https://www.ibm.com/docs/en/zos/2.2.0?topic=records-smf-record-type-83-subtype-2
    private static final int RELOCATED_RECORD_TYPE_BIND_USER = 103;

    private static final int RELOCATED_RECORD_TYPE_BIND_SOURCE_USER = 107;

    @Value("${rauditx.fmid:AZWE001}")
    private String fmid;

    @Value("${rauditx.component:ZOWE}")
    private String component;

    // Description of subtypes at https://www.ibm.com/docs/en/zos/2.5.0?topic=records-record-type-83-security-events
    @Value("${rauditx.subtype:2}")
    private int subtype;

    // Events and qualifiers documentation at https://www.ibm.com/docs/en/zos/2.5.0?topic=descriptions-event-codes-event-code-qualifiers
    @Value("${rauditx.event:2}")
    private int event;

    @Value("${rauditx.qualifier.success:0}")
    private int qualifierSuccess;

    @Value("${rauditx.qualifier.failed:1}")
    private int qualifierFailed;

    String getCurrentUser() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void logNoPrivileges(String userId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    SafResourceAccessVerifying getNativeSafResourceAccessVerifying() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @PostConstruct
    public void verifyPrivileges() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void setDefault(RauditxBuilder builder) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected Rauditx createMock() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the builder to define attributes or the record. The builder sets a couple of default values:
     *  - subtype
     *    - configurable by configuration property `rauditx.subtype`
     *    - the default value is `2`
     *  - event
     *    - configurable by configuration property `rauditx.event`
     *    - the default value is `2`
     *  - component
     *    - configurable by configuration property `rauditx.component`
     *    - the default value is `ZOWE`
     *  - FMID
     *    - configurable by configuration property `rauditx.fmid`
     *    - the default value is `AZWE001`
     *
     * All values above could be overridden via the builder.
     *
     * @return The builder of Rauditx record
     */
    public RauditxBuilder builder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @RequiredArgsConstructor
    public class RauditxBuilder {

        final Rauditx rauditx;

        /**
         * Mark the audit record as successful. It also set qualifier to value set via property
         * `rauditx.qualifier.success`, as default `0`.
         * @return builder to next action
         */
        public RauditxBuilder success() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Mark the audit record as failed. It also set qualifier to value set via property
         * `rauditx.qualifier.failed`, as default `1`.
         * @return builder to next action
         */
        public RauditxBuilder failure() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Set the reason of audit record.
         * @return builder to next action
         */
        public RauditxBuilder authentication() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Set the reason of audit record.
         * @return builder to next action
         */
        public RauditxBuilder authorization() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Set the callable service to always log successes.
         * @return builder to next action
         */
        public RauditxBuilder alwaysLogSuccesses() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Set the callable service to never log successes.
         * @return builder to next action
         */
        public RauditxBuilder neverLogSuccesses() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Set the callable service to always log failures.
         * @return builder to next action
         */
        public RauditxBuilder alwaysLogFailures() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Set the callable service to never log failures.
         * @return builder to next action
         */
        public RauditxBuilder neverLogFailures() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Set the callable service check warning mode.
         * @return builder to next action
         */
        public RauditxBuilder checkWarningMode() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Sets a flag to not throw an exception when the R_auditx callable service is successful, but no audit record
         * is logged.
         * @param ignoreSuccessWithNoAuditLogRecord set `true` to ignore
         * @return builder to next action
         */
        public RauditxBuilder ignoreSuccessWithNoAuditLogRecord(boolean ignoreSuccessWithNoAuditLogRecord) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Sets the log string - character data to be written with the audit information.
         * @param logString a String between 1 and 255 characters.
         * @return builder to next action
         */
        public RauditxBuilder logString(String logString) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Add a message to be written to the console on Event Failure. The first message segment added should begin
         * with a component message identifier of 15 characters or less.
         * @param messageSegment a String between 1 and 70 characters
         * @return builder to next action
         */
        public RauditxBuilder messageSegment(String messageSegment) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Set binded userId.
         * @param userId binded userId to be audited
         * @return builder to next action
         */
        public RauditxBuilder userId(String userId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Set source userId.
         * @param userId source userId to be audited
         * @return builder to next action
         */
        public RauditxBuilder sourceUserId(String userId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Set the event code (https://www.ibm.com/docs/en/zos/2.5.0?topic=descriptions-event-codes-event-code-qualifiers).
         * As default set to `2` or value configured by property `rauditx.event`.
         * @param event the event code int between 1 and 255
         * @return builder to next action
         */
        public RauditxBuilder event(int event) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Set the event code (https://www.ibm.com/docs/en/zos/2.5.0?topic=descriptions-event-codes-event-code-qualifiers).
         * This value could be set by methods {@link #success} and {@link #failure()}. Be aware the this method is called
         * after or without them, otherwise the value will be overriden.
         * @param qualifier the event code qualifier int between 0 and 255
         * @return builder to next action
         */
        public RauditxBuilder qualifier(int qualifier) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Sets the SMF type 83 record subtype assigned to the component (https://www.ibm.com/docs/en/zos/2.5.0?topic=records-record-type-83-security-events).
         * As default set to `2` or value configured by property `rauditx.subtype`.
         * @param subtype an int between 2 and 32767
         * @return builder to next action
         */
        public RauditxBuilder subtype(int subtype) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Issue the call to the R_auditx callable service. The method does not throw any exception. The error could
         * be written in the console log (level debug).
         */
        public void issue() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
