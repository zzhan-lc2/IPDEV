package com.ipdev.cnipr.dao.patent;

import java.util.List;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ipdev.cnipr.architect.IntegrationTestCniprCase;
import com.ipdev.cnipr.query.AttrField;
import com.ipdev.cnipr.utility.JsonCniprHelper;
import com.ipdev.common.entity.patent.Patent;
import com.ipdev.common.query.Operator;
import com.ipdev.common.query.Query;
import com.ipdev.common.query.QueryExp;

@Test(groups = { "integration" })
public class IntegrationTest_PatentSearchByCNIPRDao extends IntegrationTestCniprCase {

    PatentSearchByCNIPRDao dao;

    @BeforeMethod
    public void init() throws Exception {
        dao = this.getBean(PatentSearchByCNIPRDao.class);

        recorder.setJsonHelper(this.getBean(JsonCniprHelper.class));
    }

    public void test_findPatentById() {
        String pid = "FMZL@CN103730938A";

        Patent actual = dao.findPatentById(pid);
        Assert.assertNotNull(actual);
        Assert.assertEquals(actual.getPid(), pid);
    }

    public void test_findPatentsByQuery() {
        Set<String> sourceDbs = null;

        Query query = new Query();
        QueryExp exp = new QueryExp();
        exp.setExpKey(AttrField.ADDRESS.getName());
        exp.setExpValue("浙江省湖州 or 浙江省长兴");
        exp.setOperator(Operator.AND);
        query.addExpression(exp);
        exp = new QueryExp();
        exp.setExpKey(AttrField.PUB_DATE.getName());
        exp.setExpValue("20120901 to 20121002");
        query.addExpression(exp);

        List<Patent> actual = dao.findPatentsByQuery(query, sourceDbs, null);
        Assert.assertTrue(actual.size() > 1);
    }
}
