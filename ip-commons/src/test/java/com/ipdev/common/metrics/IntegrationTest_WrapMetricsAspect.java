package com.ipdev.common.metrics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ipdev.architect.IntegrationTestCase;
import com.ipdev.common.recorder.JsonFileDataRecorder;

@Test(groups = { "integration", "skip" })
public class IntegrationTest_WrapMetricsAspect extends IntegrationTestCase {

    public IntegrationTest_WrapMetricsAspect() {
    }

    public void test_wrapMetrics() throws IOException {
        // set up
        WrapMetricsAspect fixture = getBean(WrapMetricsAspect.class);
        DummyMetricsHelperTestManager manager = new DummyMetricsHelperTestManager();
        fixture.setMetricsManager(manager);

        Path path = null;
        try {
            JsonFileDataRecorder recorder = getBean(JsonFileDataRecorder.class);
            String objectToRecord = "test string";
            path = this.createTempOutputFilePath("test-wrap-metrics.json");
            recorder.record(path.toUri(), objectToRecord, false);

            // verify
            Integer actual = manager.getCount("JsonFileDataRecorder.record", "Time");
            Assert.assertEquals(Integer.valueOf(1), actual);
        } finally {
            Files.deleteIfExists(path);
        }
    }
}
