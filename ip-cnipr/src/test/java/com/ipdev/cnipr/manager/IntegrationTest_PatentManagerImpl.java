package com.ipdev.cnipr.manager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.ipdev.cnipr.architect.IntegrationTestCniprCase;
import com.ipdev.cnipr.entity.patent.CNIPRDBS;
import com.ipdev.cnipr.query.AttrField;
import com.ipdev.cnipr.utility.JsonCniprHelper;
import com.ipdev.common.datatype.DateRange;
import com.ipdev.common.statistics.Counter;

@Test(groups = { "integration" })
public class IntegrationTest_PatentManagerImpl extends IntegrationTestCniprCase {

    PatentManagerImpl manager;

    @BeforeMethod
    public void init() throws Exception {
        manager = this.getBean(PatentManagerImpl.class);

        recorder.setJsonHelper(this.getBean(JsonCniprHelper.class));
    }

    public void test_pullPatentsByPubYear_CN() throws ParseException {
        int actual = pullPatentsByDbDate("CN", AttrField.APP_DATE, 1984, 2015, "patents-by-db-pub-year.txt");
        Assert.assertTrue(actual > 0);
    }

    public void test_pullPatentsByAppYear_CN() throws ParseException {
        int actual = pullPatentsByDbDate("CN", AttrField.APP_DATE, 1984, 2015, "patents-by-db-app-year.txt");
        Assert.assertTrue(actual > 0);
    }

    public void test_pullPatentsByPubYear_US() throws ParseException {
        int actual = pullPatentsByDbDate("US", AttrField.APP_DATE, 1970, 2015, "us-patents-by-db-pub-year.txt");
        Assert.assertTrue(actual > 0);
    }

    int pullPatentsByDbDate(String countryCode, AttrField field, int yearStart, int yearEnd, String outputFile)
        throws ParseException {
        int total = 0;
        BufferedWriter outputWriter = null;
        List<CNIPRDBS> dbs = getDbsByCountry(countryCode);
        List<Counter> patentCounters = Lists.newArrayList();

        try {
            outputWriter = createTempOutputFile(outputFile);
            for (CNIPRDBS db : dbs) {
                for (int year = yearStart; year < yearEnd; year++) {
                    DateRange dateRange = new DateRange();
                    dateRange.setStartDate(DateUtils.parseDate(Integer.toString(year), "yyyy"));
                    dateRange.setEndDate(DateUtils.parseDate(Integer.toString(year + 1), "yyyy"));
                    Integer count = manager.getPatentCountsByDbDateRange(db, field, dateRange);

                    Counter counter = new Counter();
                    counter.setCountryCode(countryCode);
                    counter.setDateRange(dateRange);
                    counter.setDb(db.toString());
                    counter.setCounts(count);
                    patentCounters.add(counter);

                    outputWriter.write(counter2CsvString(counter));
                    outputWriter.flush();

                    total += count == null ? 0 : count;
                }
            }
            System.out.println("Total patent counts = " + total);
            /*
             * String jsonOut = dao.getJsonHelper().toJsonString(patentCounters); outputWriter.write(jsonOut);
             * outputWriter.flush();
             */
        } catch (IOException e) {
            System.err.println("caught exception: " + e);
        } finally {
            IOUtils.closeQuietly(outputWriter);
        }
        return total;
    }

    static List<CNIPRDBS> getDbsByCountry(String countryCode) {
        List<CNIPRDBS> results = Lists.newArrayList();
        for (CNIPRDBS db : CNIPRDBS.values()) {
            if (db.getCountryCode().equalsIgnoreCase(countryCode)) {
                results.add(db);
            }
        }
        return results;
    }

    static final String SEP = ",";

    static String counter2CsvString(Counter counter) {
        StringBuilder sb = new StringBuilder()
            .append(counter.getCountryCode()).append(SEP)
            .append(StringUtils.isEmpty(counter.getState()) ? "ALL" : counter.getState()).append(SEP)
            .append(counter.getDb()).append(SEP)
            .append(counter.getDateRange().getStartDate().getYear() + 1900).append(SEP)
            .append(counter.getCounts())
            .append("\n");
        return sb.toString();
    }
}
