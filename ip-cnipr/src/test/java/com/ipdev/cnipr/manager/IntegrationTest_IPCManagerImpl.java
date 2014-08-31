package com.ipdev.cnipr.manager;

import java.io.BufferedWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ipdev.cnipr.architect.IntegrationTestCniprCase;
import com.ipdev.cnipr.entity.patent.IpcInfoNode;
import com.ipdev.cnipr.utility.JsonCniprHelper;
import com.ipdev.common.utility.json.JsonHelper;

@Test(groups = { "integration" })
public class IntegrationTest_IPCManagerImpl extends IntegrationTestCniprCase {

    IPCManagerImpl manager;
    JsonHelper helper;

    @BeforeMethod
    public void init() throws Exception {
        manager = this.getBean(IPCManagerImpl.class);

        helper = this.getBean(JsonCniprHelper.class);
        recorder.setJsonHelper(helper);
    }

    public void test_getIpcTreeByKey() {
        BufferedWriter outputWriter = null;
        try {
            outputWriter = createTempOutputFile("getIpcTreeByKey.json");

            int maxNodes = 2000;

            List<IpcInfoNode> actual = manager.getIpcTreeByKey("", maxNodes);
            Assert.assertTrue(actual.size() > 0);

            String jsonOut = helper.toJsonString(actual);
            outputWriter.write(jsonOut);
            outputWriter.flush();

        } catch (Exception e) {
            System.err.println("caught exception: " + e);
        } finally {
            IOUtils.closeQuietly(outputWriter);
        }

    }
}
