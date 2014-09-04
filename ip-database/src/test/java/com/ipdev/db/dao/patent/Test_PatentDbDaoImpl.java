package com.ipdev.db.dao.patent;

import static com.ipdev.db.support.DateTestUtils.parseDate;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ipdev.common.entity.patent.Patent;
import com.ipdev.db.support.CreateDao;
import com.ipdev.db.support.HibernateDaoUnitTestCase;

@Test(groups = { "unit", "dao" }, singleThreaded = true)
public class Test_PatentDbDaoImpl extends HibernateDaoUnitTestCase {

    @CreateDao(PatentDbHibernateDao.class)
    private PatentDbHibernateDao dao;

    Patent patent;
    Patent patent2;

    @BeforeClass
    public void init() {
        patent = new Patent();
        patent.setPid("PATENT01234");
        patent.setSourceDb("SRCDB1");
        patent.setTitle("Title");
        patent.setIpc(Lists.newArrayList("IPC1", "IPC2"));
        patent.setAppDate(parseDate("2014-01-01"));
        patent.setPubDate(parseDate("2014-01-01"));
        patent.setAgentName(Lists.newArrayList("Agent1", "Agent2"));
        patent.setApplicantName(Lists.newArrayList("Applicant1", "Applicant2"));
        patent.setAbs("Abs");

        patent.setCreationDate(parseDate("2014-01-03"));

        patent2 = new Patent();
        patent2.setPid("PATENT56789");
        patent2.setSourceDb("SRCDB2");
        patent2.setTitle("Title2");
        patent2.setIpc(Lists.newArrayList("IPC1", "IPC2"));
        patent2.setAppDate(parseDate("2014-03-01"));
        patent2.setPubDate(parseDate("2014-03-01"));
        patent2.setAgentName(Lists.newArrayList("Agent1", "Agent2"));
        patent2.setApplicantName(Lists.newArrayList("Applicant3"));
        patent2.setAbs("Abs");

        patent2.setCreationDate(parseDate("2014-03-03"));
    }

    public void test_save() {
        dao.save(patent);
        dao.save(patent2);
    }

    @Test(dependsOnMethods = "test_save")
    public void test_findPatentById() {
        Patent actual = dao.findPatentById(patent.getPid());
        Assert.assertEquals(actual.getPid(), patent.getPid());

        // also make sure all the list types are return correctly
        Assert.assertEquals(actual.getIpc().size(), patent.getIpc().size());
        Assert.assertEquals(actual.getAgentName().size(), patent.getAgentName().size());

    }

    @Test(dependsOnMethods = "test_save")
    public void test_findPatentsByApplicant() {
        String applicantName = "Applicant2";
        List<Patent> actual = dao.findPatentsByApplicant(applicantName, /* sourceDbs= */null);
        Assert.assertEquals(actual.size(), 1);

        actual = dao.findPatentsByApplicant(applicantName, /* sourceDbs= */Sets.newHashSet("SRCDB2"));
        Assert.assertEquals(actual.size(), 0);

        applicantName = "Applicant3";
        actual = dao.findPatentsByApplicant(applicantName, /* sourceDbs= */null);
        Assert.assertEquals(actual.size(), 1);
        Assert.assertEquals(actual.get(0), patent2);
    }

}
