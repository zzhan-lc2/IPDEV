package com.ipdev.db.dao.query;

import static com.ipdev.db.support.DateTestUtils.parseDate;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ipdev.common.query.Operator;
import com.ipdev.common.query.Query;
import com.ipdev.common.query.QueryExp;
import com.ipdev.db.support.CreateDao;
import com.ipdev.db.support.HibernateDaoUnitTestCase;

@Test(groups = { "unit", "dao" }, singleThreaded = true)
public class Test_QueryDbHibernateDao extends HibernateDaoUnitTestCase {

    @CreateDao(QueryDbHibernateDao.class)
    private QueryDbHibernateDao dao;

    Query query;

    @BeforeClass
    public void init() {
        query = new Query();
        query.setCreator("creator");
        query.setIsDeprecated(false);
        query.setCreationDate(parseDate("2014-08-01"));
        query.setLastUpdatedDate(parseDate("2014-09-01"));

        QueryExp exp = new QueryExp();
        exp.setExpKey("key1");
        exp.setExpValue("value1");
        exp.setOperator(Operator.AND);
        query.addExpression(exp);
        exp = new QueryExp();
        exp.setExpKey("key2");
        exp.setExpValue("value2");
        exp.setOperator(Operator.OR);
        query.addExpression(exp);
        exp = new QueryExp();
        exp.setExpKey("key3");
        exp.setExpValue("value3");
        query.addExpression(exp);
    }

    public void test_save() {
        dao.save(query);

        Assert.assertTrue(StringUtils.isNotBlank(query.getId()));
    }

    @Test(dependsOnMethods = "test_save")
    public void test_findById() {
        Query actual = dao.findById(query.getId());

        Assert.assertEquals(actual.getId(), query.getId());
        Assert.assertEquals(actual.getExpressions().get(0).getExpKey(), "key1");
        Assert.assertEquals(actual.getExpressions().get(1).getExpKey(), "key2");
        Assert.assertEquals(actual.getExpressions().get(2).getExpKey(), "key3");

    }

    @Test(dependsOnMethods = "test_save")
    public void test_findByCreator() {
        List<Query> actual = dao.findByCreator("creator");
        Assert.assertEquals(actual.size(), 1);
        Assert.assertEquals(actual.get(0), query);

        actual = dao.findByCreator("invalid_creator");
        Assert.assertEquals(actual.size(), 0);
    }
}
