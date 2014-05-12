package com.ipdev.cnipr.manager;

import java.io.BufferedWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ipdev.cnipr.architect.IntegrationTestCniprCase;
import com.ipdev.cnipr.dao.patent.PatentSearchByCNIPRDao;
import com.ipdev.cnipr.entity.patent.IpcInfoNode;

@Test(groups = { "integration" })
public class IntegrationTest_IPCManagerImpl extends IntegrationTestCniprCase {

    PatentSearchByCNIPRDao dao;

    IPCManagerImpl manager;

    @BeforeMethod
    public void init() throws Exception {
        dao = new PatentSearchByCNIPRDao(false);
        dao.setTokenManager(tokenManager);

        recorder.setJsonHelper(dao.getJsonHelper());

        manager = new IPCManagerImpl();
        manager.setCniprDao(dao);
    }

    public void test_getIpcTreeByKey() {
        BufferedWriter outputWriter = null;
        try {
            outputWriter = createTempOutputFile("getIpcTreeByKey.json");

            int maxNodes = 2000;

            List<IpcInfoNode> actual = manager.getIpcTreeByKey("", maxNodes);
            Assert.assertTrue(actual.size() > 0);

            String jsonOut = dao.getJsonHelper().toJsonString(actual);
            outputWriter.write(jsonOut);
            outputWriter.flush();

        } catch (Exception e) {
            System.err.println("caught exception: " + e);
        } finally {
            IOUtils.closeQuietly(outputWriter);
        }

    }
}
