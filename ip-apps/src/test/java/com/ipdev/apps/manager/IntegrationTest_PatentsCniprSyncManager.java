package com.ipdev.apps.manager;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ipdev.apps.IntegrationTestAppCase;
import com.ipdev.cnipr.query.AttrField;
import com.ipdev.common.dao.patent.RequestControlParams;
import com.ipdev.common.query.Query;
import com.ipdev.common.query.QueryExp;

@Test(groups = { "integration" })
public class IntegrationTest_PatentsCniprSyncManager extends IntegrationTestAppCase {
    PatentsCniprSyncManager manager;
    RequestControlParams params = new RequestControlParams();

    @BeforeMethod
    public void init() {
        manager = this.getBean(PatentsCniprSyncManager.class);

    }

    public void test_syncPatentsByQuery_1() {
        Query query = new Query();
        QueryExp exp = new QueryExp(AttrField.APPLICANT.getName(), "无锡市雷华科技有限公司");
        query.addExpression(exp);

        int actual = manager.syncPatentsByQuery(query, params);
        Assert.assertTrue(actual > 1);
    }

    public void test_syncPatentsByQuery_2() { // 衢州港诚机电产品制造有限公司
        params.withForceUpdate(true);
        Query query = new Query();
        QueryExp exp = new QueryExp(AttrField.APPLICANT.getName(), "衢州港诚机电产品制造有限公司");
        query.addExpression(exp);

        int actual = manager.syncPatentsByQuery(query, params);
        Assert.assertTrue(actual > 0);
    }

    public void test_syncPatentsByQuery_3() { // 华为
        // params.withMaxResults(200);

        int pageSize = 200;

        Query query = new Query();
        QueryExp exp = new QueryExp(AttrField.APPLICANT.getName(), "华为技术有限公司");
        query.addExpression(exp);

        int start = 8000;
        params.withFromIndex(start);
        for (int i = 0; i < 10; i++) {
            params.withFromIndex(start + i * pageSize)
                .withToIndex(start + (i + 1) * pageSize);
            try {
                int actual = manager.syncPatentsByQuery(query, params);
                Assert.assertTrue(actual > 1);
            } catch (Exception e) {
                System.err.println("Caught exception:" + e);
            }
        }
    }
}
