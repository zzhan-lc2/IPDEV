package com.ipdev.common.recorder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;
import com.ipdev.common.entity.method.RequestResponse;
import com.ipdev.common.utility.json.JsonHelper;

public class JsonFileDataRecorder implements PatentDataRecorder {
    static final Log LOG = LogFactory.getLog(JsonFileDataRecorder.class);

    static String DEFAULT_CHARSET = "UTF-8";

    JsonHelper jsonHelper;

    public void setJsonHelper(JsonHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
    }

    public void record(URI storageUri, RequestResponse requestResponse, boolean outputNulls) {
        Preconditions.checkNotNull(storageUri, "storageUri cannot be null");
        Preconditions.checkNotNull(requestResponse, "requestResponse cannot be null");

        String path = storageUri.getPath();
        Path outputPath = Paths.get(path);

        try (BufferedWriter output = Files.newBufferedWriter(
            outputPath,
            Charset.forName(DEFAULT_CHARSET),
            StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            record(output, requestResponse, outputNulls);
        } catch (DataRecordException e) {
            throw e; // re-throw
        } catch (IOException e) {
            throw new DataRecordException("Caught IOException:", e);
        }

    }

    public void record(BufferedWriter outputWriter, RequestResponse requestResponse, boolean outputNulls) {
        Preconditions.checkNotNull(outputWriter, "outputWriter cannot be null");
        Preconditions.checkNotNull(requestResponse, "requestResponse cannot be null");

        try {
            String jsonOut = jsonHelper.toJsonString(requestResponse);
            outputWriter.write(jsonOut);
            outputWriter.flush();
        } catch (IOException e) {
            // LOG.error("Caught IOException: ", e);
            throw new DataRecordException("Caught IOException:", e);
        }

    }

}
