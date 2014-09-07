package com.ipdev.cnipr.dao.patent;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ipdev.cnipr.query.AttrField;
import com.ipdev.common.query.Operator;
import com.ipdev.common.query.Query;
import com.ipdev.common.query.QueryExp;

@Test(groups = { "unit" })
public class Test_PatentSearchByCNIPRDao {

    public void test_queryToExpression_1() {
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
        String expected = "地址=(浙江省湖州 or 浙江省长兴) and 公开（公告）日=(20120901 to 20121002)";

        String actual = PatentSearchByCNIPRDao.queryToExpression(query);
        Assert.assertEquals(actual, expected);
    }

    public void test_queryToExpression_2() {
        Query query = new Query();
        QueryExp exp = new QueryExp();
        exp.setExpKey(AttrField.APPLICANT.getName());
        exp.setExpValue("ABCDE");
        exp.setOperator(Operator.OR);
        query.addExpression(exp);
        exp = new QueryExp();
        exp.setExpKey(AttrField.APP_DATE.getName());
        exp.setExpValue("20120901 to 20121002");
        query.addExpression(exp);
        String expected = "申请（专利权）人=(ABCDE) or 申请日=(20120901 to 20121002)";

        String actual = PatentSearchByCNIPRDao.queryToExpression(query);
        Assert.assertEquals(actual, expected);
    }
}
