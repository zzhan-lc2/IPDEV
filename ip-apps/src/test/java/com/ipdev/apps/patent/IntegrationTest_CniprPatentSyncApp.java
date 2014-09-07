package com.ipdev.apps.patent;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ipdev.apps.IntegrationTestAppCase;
import com.ipdev.cnipr.query.AttrField;
import com.ipdev.common.query.Query;
import com.ipdev.common.query.QueryExp;

@Test(groups = { "integration" })
public class IntegrationTest_CniprPatentSyncApp extends IntegrationTestAppCase {

    CniprPatentSyncApp app;

    @BeforeMethod
    public void init() {
        app = this.getBean(CniprPatentSyncApp.class);
    }

    public void test_syncPatentsByQuery() {
        Query query = new Query();
        QueryExp exp = new QueryExp(AttrField.APPLICANT.getName(), "无锡市雷华科技有限公司");
        query.addExpression(exp);

        int actual = app.syncPatentsByQuery(query);
        Assert.assertTrue(actual > 1);
    }
}
