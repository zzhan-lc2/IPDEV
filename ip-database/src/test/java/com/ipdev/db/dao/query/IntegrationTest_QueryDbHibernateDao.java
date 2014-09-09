package com.ipdev.db.dao.query;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ipdev.common.query.Query;
import com.ipdev.common.query.QueryExp;
import com.ipdev.common.query.QueryType;
import com.ipdev.db.support.IntegrationTestDbCase;

@Test(groups = { "integration", "dao" })
public class IntegrationTest_QueryDbHibernateDao extends IntegrationTestDbCase {

    QueryDbHibernateDao dao;

    Query testQuery;

    @BeforeMethod
    public void init() {
        dao = this.getBean(QueryDbHibernateDao.class);

        testQuery = new Query();
        testQuery.setCreator("zzhan");
        testQuery.setQueryType(QueryType.SYNC_PATENTS);
        QueryExp exp = new QueryExp("申请（专利权）人", "无锡市雷华科技有限公司");
        testQuery.addExpression(exp);
    }

    public void test_save() {
        dao.save(testQuery);

        Assert.assertTrue(StringUtils.isNotEmpty(testQuery.getId()));
    }
}
