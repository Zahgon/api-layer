/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.caching.service.vsam;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.zowe.apiml.caching.service.vsam.config.VsamConfig;
import org.zowe.apiml.message.log.ApimlLogger;
import org.zowe.apiml.zfile.ZFile;
import org.zowe.apiml.zfile.ZFileConstants;
import org.zowe.apiml.zfile.ZFileException;
import java.io.Closeable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * ZFile wrapper providing convenience methods and implementing Closeable interface
 * This class is intended for serialized access to VSAM file.
 * Concurrency is to be handled by retrying.
 * Creates a proxy of com.ibm.jzos.ZFileException and provides high level methods for CRUD operations
 */
@Slf4j
public class VsamFile implements Closeable {

    @Getter
    private final ZFile zfile;

    private final VsamConfig vsamConfig;

    private final ZFileProducer zFileProducer;

    private final ApimlLogger apimlLog;

    public static final String VSAM_RECORD_ERROR_MESSAGE = "VsamRecordException occurred: {}";

    public static final String RECORD_FOUND_MESSAGE = "Record found: {}";

    public static final String RECORD_CANNOT_BE_NULL_MESSAGE = "Record cannot be null";

    public static final String UNSUPPORTED_ENCODING_MESSAGE = "Unsupported encoding: {}";

    private static final String ERROR_INITIALIZING_STORAGE_MESSAGE_KEY = "org.zowe.apiml.cache.errorInitializingStorage";

    private static final String STORAGE_TYPE = "VSAM";

    private static final Pattern REGEX_CORRECT_FILENAME = Pattern.compile("^//'.*'");

    public VsamFile(VsamConfig config, VsamConfig.VsamOptions options, ApimlLogger apimlLogger) {
        this(config, options, false, apimlLogger);
    }

    public VsamFile(VsamConfig config, VsamConfig.VsamOptions options, boolean initialCreation, ApimlLogger apimlLogger) {
        this(config, options, initialCreation, new ZFileProducer(config, options, apimlLogger), new VsamInitializer(), apimlLogger);
    }

    public VsamFile(VsamConfig config, VsamConfig.VsamOptions options, boolean initialCreation, ZFileProducer zFileProducer, VsamInitializer vsamInitializer, ApimlLogger apimlLogger) {
        this.apimlLog = apimlLogger;
        if (config == null) {
            apimlLog.log(ERROR_INITIALIZING_STORAGE_MESSAGE_KEY, "vsam", "wrong Configuration", "No configuration provided");
            throw new IllegalArgumentException("Cannot create VsamFile with null configuration");
        }
        this.vsamConfig = config;
        log.info("VsamFile::new with parameters: {}, Vsam options: {}", this.vsamConfig, options);
        if (!REGEX_CORRECT_FILENAME.matcher(vsamConfig.getFileName()).find()) {
            String nonConformance = "VsamFile name does not conform to //'VSAM.DATASET.NAME' pattern  " + vsamConfig.getFileName();
            apimlLog.log(ERROR_INITIALIZING_STORAGE_MESSAGE_KEY, "vsam", "wrong vsam name: " + vsamConfig.getFileName(), nonConformance);
            throw new IllegalArgumentException(nonConformance);
        }
        this.zFileProducer = zFileProducer;
        try {
            this.zfile = openZfile();
            if (initialCreation) {
                log.info("Warming up VSAM file");
                vsamInitializer.warmUpVsamFile(zfile, vsamConfig);
            }
        } catch (Exception e) {
            String info = String.format("opening of %s in mode %s failed", vsamConfig, options);
            if (initialCreation) {
                apimlLog.log(ERROR_INITIALIZING_STORAGE_MESSAGE_KEY, STORAGE_TYPE, info, e);
            } else {
                log.info(info);
            }
            throw new IllegalStateException("Failed to open VsamFile");
        }
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Optional<VsamRecord> create(VsamRecord vsamRec) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Optional<VsamRecord> read(VsamRecord vsamRec) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Optional<VsamRecord> update(VsamRecord vsamRec) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Optional<VsamRecord> delete(VsamRecord vsamRec) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private byte[] readRecord() throws ZFileException {
        byte[] recBuf = new byte[vsamConfig.getRecordLength()];
        if (vsamConfig.getRecordLength() != zfile.read(recBuf)) {
            log.warn("Configured VSAM record length is different that the actual data read from {}", zfile.getActualFilename());
        }
        return recBuf;
    }

    private Optional<VsamRecord> recordOperation(VsamRecord vsamRec, RecordHandler recordHandler) {
        if (vsamRec == null) {
            throw new IllegalArgumentException(RECORD_CANNOT_BE_NULL_MESSAGE);
        }
        try {
            boolean found = zfile.locate(vsamRec.getKeyBytes(), ZFileConstants.LOCATE_KEY_EQ);
            if (found) {
                return recordHandler.handleRecordFound();
            } else {
                return recordHandler.handleNoRecordFound();
            }
        } catch (UnsupportedEncodingException e) {
            log.info(UNSUPPORTED_ENCODING_MESSAGE, ZFileConstants.DEFAULT_EBCDIC_CODE_PAGE);
        } catch (ZFileException e) {
            log.info(e.toString());
            throw new RetryableVsamException(e);
        } catch (VsamRecordException e) {
            log.info(VSAM_RECORD_ERROR_MESSAGE, e.toString());
            throw new RetryableVsamException(e);
        }
        return Optional.empty();
    }

    public List<VsamRecord> readForService(String serviceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void deleteForService(String serviceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void serviceWideOperation(String serviceId, ServiceWideOperation operation) {
        if (serviceId == null || serviceId.isEmpty()) {
            throw new IllegalArgumentException("serviceId cannot be null");
        }
        VsamKey key = new VsamKey(vsamConfig);
        try {
            byte[] recBuf = new byte[vsamConfig.getRecordLength()];
            String keyGe = key.getKeySidOnly(serviceId);
            log.info("Attempt to find key in KEY_GE mode: {}", keyGe);
            boolean found = zfile.locate(key.getKeyBytesSidOnly(serviceId), ZFileConstants.LOCATE_KEY_GE);
            log.info(RECORD_FOUND_MESSAGE, found);
            int overflowProtection = 10000;
            while (found) {
                int nread = zfile.read(recBuf);
                //NOSONAR
                log.trace("RecBuf: {}", recBuf);
                log.info("nread: {}", nread);
                String convertedStringValue = new String(recBuf, ZFileConstants.DEFAULT_EBCDIC_CODE_PAGE);
                VsamRecord vsamRec = new VsamRecord(vsamConfig, recBuf);
                log.info("Read record: {}", vsamRec);
                if (nread < 0) {
                    log.info("nread is < 0, stopping the retrieval");
                    found = false;
                    //NOSONAR
                    continue;
                }
                log.trace("convertedStringValue: >{}<", convertedStringValue);
                log.trace("keyGe: >{}<", keyGe);
                if (!convertedStringValue.trim().startsWith(keyGe.trim())) {
                    log.info("read record does not start with serviceId's keyGe, stopping the retrieval");
                    found = false;
                    //NOSONAR
                    continue;
                } else {
                    log.info("read record starts with serviceId's keyGe, retrieving this record");
                }
                operation.resolveValidRecord(zfile, vsamRec);
                overflowProtection--;
                if (overflowProtection <= 0) {
                    log.info("Maximum number of records retrieved, stopping the retrieval");
                    //NOSONAR
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            log.info(UNSUPPORTED_ENCODING_MESSAGE, ZFileConstants.DEFAULT_EBCDIC_CODE_PAGE);
        } catch (ZFileException e) {
            log.info(e.toString());
        } catch (VsamRecordException e) {
            log.info(VSAM_RECORD_ERROR_MESSAGE, e.toString());
        }
    }

    public Optional<byte[]> readBytes(byte[] arrayToStoreIn) throws ZFileException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Integer countAllRecords() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings({ "squid:S1130", "squid:S1192" })
    private ZFile openZfile() throws VsamRecordException {
        return zFileProducer.openZfile();
    }

    @FunctionalInterface
    private interface RecordHandler {

        Optional<VsamRecord> handleRecordFound() throws VsamRecordException, ZFileException, UnsupportedEncodingException;

        default Optional<VsamRecord> handleNoRecordFound() throws VsamRecordException, ZFileException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    @FunctionalInterface
    private interface ServiceWideOperation {

        void resolveValidRecord(ZFile zFile, VsamRecord vsamRec) throws ZFileException;
    }
}
