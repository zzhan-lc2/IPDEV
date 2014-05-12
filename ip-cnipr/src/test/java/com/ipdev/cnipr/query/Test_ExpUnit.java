package com.ipdev.cnipr.query;

import java.text.ParseException;

import org.apache.commons.lang3.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ipdev.common.datatype.DateRange;

@Test(groups = { "unit" })
public class Test_ExpUnit {
    static final String DATE_PATTERN = "yyyy-MM-dd";

    public void test_toExpression() throws ParseException {
        DateRange dateRange = new DateRange()
            .setStartDate(DateUtils.parseDate("2014-04-01", DATE_PATTERN))
            .setEndDate(DateUtils.parseDate("2014-05-01", DATE_PATTERN));

        ExpUnit exp = ExpUnit.withAttribute(AttrField.ADDRESS)
            .setValue(
                new ValueField("浙江省").setNext(LogicToken.OR, new ValueField("福建省")))
            .setNext(LogicToken.AND,
                ExpUnit.withAttribute(AttrField.PUB_DATE)
                    .setValue(new ValueField(dateRange))
            );
        String expected = "地址=(浙江省 or 福建省) and 公开（公告）日=(20140401 to 20140501)";

        Assert.assertEquals(exp.toExpression(), expected);
    }
}
