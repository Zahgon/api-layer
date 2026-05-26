/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.product.zos;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

public class ZUtilDummy implements ZUtil {

    @Override
    public String[] environ() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String formatStackTrace(Throwable t) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getCodePageCurrentLocale() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long getCpuTimeMicros() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getCurrentJobId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getCurrentJobname() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getCurrentProcStepname() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getCurrentStepname() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long getCurrentTimeMicros() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getCurrentTsoPrefix() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getCurrentUser() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getDefaultPlatformEncoding() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getEnv(String varName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Properties getEnvironment() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getJavaVersionInfo() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getJzosDllVersion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getJzosJarVersion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getLoggingLevel() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getPid() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getPPid() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public byte[] getTodClock() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void getTodClock(byte[] buffer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public byte[] getTodClockExtended() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void getTodClockExtended(byte[] buffer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void logDiagnostic(int level, String msg) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public PrintStream newEncodedPrintStream(OutputStream os, boolean autoFlush) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public PrintStream newEncodedPrintStream(OutputStream os, boolean autoFlush, String encoding) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public PrintStream newEncodedPrintStream(OutputStream os, boolean autoFlush, String encoding, boolean enable) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void peekOSMemory(long address, byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void peekOSMemory(long address, byte[] bytes, int offset, int len) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long peekOSMemory(long address, int len) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void redirectStandardStreams() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean redirectStandardStreams(String requestedEncoding, boolean enableTranscoding) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setDefaultPlatformEncoding(String encoding) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setEnv(String varName, String varValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setLoggingLevel(int level) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void smfRecord(int type, int subtype, byte[] rec) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String substituteSystemSymbols(String pattern) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String substituteSystemSymbols(String pattern, boolean warn) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void touch() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
