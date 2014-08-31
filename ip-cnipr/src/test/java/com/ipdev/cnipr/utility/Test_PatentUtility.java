package com.ipdev.cnipr.utility;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "unit" })
public class Test_PatentUtility {

    public void test_getSourceDbFromPid() {
        String pid = "SYXX@CN203689291U";
        Assert.assertEquals(PatentUtility.getSourceDbFromPid(pid), "SYXX");

        pid = "CN203689291U";
        Assert.assertNull(PatentUtility.getSourceDbFromPid(pid));

    }
}
