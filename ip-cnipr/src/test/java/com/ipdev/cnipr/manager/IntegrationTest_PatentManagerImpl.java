package com.ipdev.cnipr.manager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.ipdev.cnipr.architect.IntegrationTestCniprCase;
import com.ipdev.cnipr.dao.patent.PatentSearchByCNIPRDao;
import com.ipdev.cnipr.entity.patent.CNIPRDBS;
import com.ipdev.common.datatype.DateRange;
import com.ipdev.common.statistics.Counter;

@Test(groups = { "integration" })
public class IntegrationTest_PatentManagerImpl extends IntegrationTestCniprCase {

    PatentSearchByCNIPRDao dao;

    PatentManagerImpl manager;

    @BeforeMethod
    public void init() throws Exception {
        dao = new PatentSearchByCNIPRDao(false);
        dao.setTokenManager(tokenManager);

        recorder.setJsonHelper(dao.getJsonHelper());

        manager = new PatentManagerImpl();
        manager.setCniprDao(dao);
    }

    public void test_pullPatentsByPubYear() throws ParseException {
        BufferedWriter outputWriter = null;
        List<CNIPRDBS> cnDbs = CNIPRDBS.getAllCN();
        List<Counter> patentCounters = Lists.newArrayList();
        int yearStatr = 1984;
        int yearEnd = 2015;
        try {
            outputWriter = createTempOutputFile("patents-by-db-year.txt");
            for (CNIPRDBS db : cnDbs) {
                for (int year = yearStatr; year < yearEnd; year++) {
                    DateRange pubDateRange = new DateRange();
                    pubDateRange.setStartDate(DateUtils.parseDate(Integer.toString(year), "yyyy"));
                    pubDateRange.setEndDate(DateUtils.parseDate(Integer.toString(year + 1), "yyyy"));
                    Integer count = manager.getPatentCountsByDbPubDate(db, pubDateRange);

                    Counter counter = new Counter();
                    counter.setCountryCode("CN");
                    counter.setDateRange(pubDateRange);
                    counter.setDb(db.toString());
                    counter.setCounts(count);
                    patentCounters.add(counter);

                    outputWriter.write(counter2CsvString(counter));
                    outputWriter.flush();
                }
            }
            /*
             * String jsonOut = dao.getJsonHelper().toJsonString(patentCounters); outputWriter.write(jsonOut);
             * outputWriter.flush();
             */
        } catch (IOException e) {
            System.err.println("caught exception: " + e);
        } finally {
            IOUtils.closeQuietly(outputWriter);
        }
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
