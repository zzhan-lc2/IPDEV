package com.ipdev.common.recorder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;
import com.ipdev.common.metrics.ReportMetrics;
import com.ipdev.common.utility.json.GsonJsonHelper;
import com.ipdev.common.utility.json.JsonHelper;

public class JsonFileDataRecorder implements PatentDataRecorder {
    static final Log LOG = LogFactory.getLog(JsonFileDataRecorder.class);

    static String DEFAULT_CHARSET = "UTF-8";

    JsonHelper jsonHelper;

    public JsonFileDataRecorder() {
        // setup a default JsonHelper (by following CNIPR style)
        jsonHelper = new GsonJsonHelper()
            .setDateFormat("yyyy.MM.dd")
            .setEnableHtmlEsacping(false)
            .setSerializeNulls(true);
    }

    public void setJsonHelper(JsonHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
    }

    public JsonHelper getJsonHelper() {
        return this.jsonHelper;
    }

    @ReportMetrics
    public void record(URI storageUri, Object ObjectToRecord, boolean outputNulls) {
        Preconditions.checkNotNull(storageUri, "storageUri cannot be null");
        Preconditions.checkNotNull(ObjectToRecord, "ObjectToRecord cannot be null");

        Path outputPath = Paths.get(storageUri);
        // let's make sure all necessary directories leading to the file are created
        try {
            FileUtils.forceMkdir(outputPath.toFile().getParentFile());
        } catch (IOException e) {
            throw new DataRecordException("Caught IOException when creating file:" + outputPath.toString(), e);
        }

        try (BufferedWriter output = Files.newBufferedWriter(
            outputPath,
            Charset.forName(DEFAULT_CHARSET),
            StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            record(output, ObjectToRecord, outputNulls);
        } catch (DataRecordException e) {
            throw e; // re-throw
        } catch (IOException e) {
            throw new DataRecordException("Caught IOException:", e);
        }

    }

    public void record(BufferedWriter outputWriter, Object ObjectToRecord, boolean outputNulls) {
        Preconditions.checkNotNull(outputWriter, "outputWriter cannot be null");
        Preconditions.checkNotNull(ObjectToRecord, "ObjectToRecord cannot be null");

        try {
            String jsonOut = jsonHelper.toJsonString(ObjectToRecord);
            outputWriter.write(jsonOut);
            outputWriter.flush();
        } catch (IOException e) {
            // LOG.error("Caught IOException: ", e);
            throw new DataRecordException("Caught IOException:", e);
        }

    }

}
