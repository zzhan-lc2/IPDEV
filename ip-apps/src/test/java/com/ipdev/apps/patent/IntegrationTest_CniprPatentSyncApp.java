package com.ipdev.apps.patent;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ipdev.apps.IntegrationTestAppCase;

@Test(groups = { "integration" })
public class IntegrationTest_CniprPatentSyncApp extends IntegrationTestAppCase {

    CniprPatentSyncApp app;

    @BeforeMethod
    public void init() {
        app = this.getBean(CniprPatentSyncApp.class);
    }

    public void test_beanInit() {
        Assert.assertNotNull(app.syncManager);
    }

}
