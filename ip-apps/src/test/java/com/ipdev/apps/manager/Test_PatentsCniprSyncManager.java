package com.ipdev.apps.manager;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ipdev.cnipr.dao.patent.PatentSearchByCNIPRDao;
import com.ipdev.common.dao.patent.PatentSimpleSearchDao;
import com.ipdev.common.dao.patent.PatentStorageDao;
import com.ipdev.common.dao.patent.PatentStorageFileDao;
import com.ipdev.common.entity.patent.Patent;

@Test(groups = { "integration" })
public class Test_PatentsCniprSyncManager {
    @InjectMocks
    PatentsCniprSyncManager manager;

    @Mock
    PatentSearchByCNIPRDao cniprDao;
    @Mock
    PatentSimpleSearchDao localSearchDao;
    @Mock
    PatentStorageFileDao fileStorageDao;
    @Mock
    PatentStorageDao dbStorageDao;

    @BeforeClass
    public void init() {
        manager = new PatentsCniprSyncManager();

        MockitoAnnotations.initMocks(this);
    }

    public void test_isPatentNeedToBeUpdated() {
        String pid = "pid";
        Patent newP = new Patent();
        newP.setPid(pid);
        newP.setLprs("lprs-new");

        Patent oldP = new Patent();
        oldP.setPid(pid);
        oldP.setLprs("lprs-old");

        {
            Mockito.when(localSearchDao.findPatentById(pid)).thenReturn(oldP);

            Assert.assertEquals(manager.isPatentNeedToBeUpdated(newP, false), /* expected= */true);
        }

        {
            oldP.setLprs(newP.getLprs());
            Mockito.when(localSearchDao.findPatentById(pid)).thenReturn(oldP);

            Assert.assertEquals(manager.isPatentNeedToBeUpdated(newP, false), /* expected= */false);
            Assert.assertEquals(manager.isPatentNeedToBeUpdated(newP, true), /* expected= */true);

        }

    }
}
