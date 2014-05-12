package com.ipdev.cnipr.query;

import java.text.ParseException;

import org.apache.commons.lang3.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ipdev.common.datatype.DateRange;

@Test(groups = { "unit" })
public class Test_ValueStringHelper {
    static final String DATE_PATTERN = "yyyy-MM-dd";

    public void test_dateRangeToString() throws ParseException {
        DateRange dateRange = new DateRange()
            .setStartDate(DateUtils.parseDate("2014-04-01", DATE_PATTERN))
            .setEndDate(DateUtils.parseDate("2014-05-01", DATE_PATTERN));

        Assert.assertEquals(ValueStringHelper.dateRangeToString(dateRange), "20140401 to 20140501");

        dateRange = new DateRange()
            .setStartDate(DateUtils.parseDate("2014-02-01", DATE_PATTERN));

        Assert.assertEquals(ValueStringHelper.dateRangeToString(dateRange), "20140201");
    }
}
