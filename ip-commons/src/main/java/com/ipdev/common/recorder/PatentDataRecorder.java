package com.ipdev.common.recorder;

import java.net.URI;

public interface PatentDataRecorder {

    /**
     * Record patent query's request and response data to the specified storage path. Record the null field if the
     * boolean flag [outputNulls] is set to true.
     * 
     * @param storagePath
     *            the desired storage path
     * @param objectToRecord
     *            the object to be recorded in JSON format
     * @param outputNulls
     *            the flag for output null field or not
     */
    void record(URI storagePath, Object objectToRecord, boolean outputNulls);
}
