/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gzip;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

public class GZipResponseWrapper extends HttpServletResponseWrapper {

    private GZipServletOutputStream gzipOutputStream;

    private PrintWriter printWriter = null;

    private boolean disableFlushBuffer = false;

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response The response to be wrapped
     * @throws IllegalArgumentException if the response is null
     */
    public GZipResponseWrapper(HttpServletResponse response, GZIPOutputStream stream) {
        super(response);
        gzipOutputStream = new GZipServletOutputStream(stream);
    }

    public void close() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Flush OutputStream or PrintWriter
     *
     * @throws IOException
     */
    @Override
    public void flushBuffer() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Flushes all the streams for this response.
     */
    public void flush() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public ServletOutputStream getOutputStream() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setContentLength(int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set if the wrapped reponse's buffer flushing should be disabled.
     *
     * @param disableFlushBuffer true if the wrapped reponse's buffer flushing should be disabled
     */
    public void setDisableFlushBuffer(boolean disableFlushBuffer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
