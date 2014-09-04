package com.ipdev.common.utility.file;

import java.net.URI;
import java.net.URISyntaxException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ipdev.common.entity.patent.Patent;

@Test(groups = { "unit" })
public class Test_PatentFileUtility {

    public void test_getPatentFileURI() throws URISyntaxException {
        // setup
        Patent patent = new Patent();
        patent.setPid("FMZL@CN103730938A");

        URI baseLocation = new URI("file:///tmp");
        String fileExtension = ".json";
        String expected = "/tmp/CN/FMZL/1037/3093/8A00/CN103730938A.json";

        URI actual = PatentFileUtility.getPatentFileURI(patent, baseLocation, fileExtension);
        Assert.assertNotNull(actual);
        Assert.assertEquals(actual.getPath(), expected);
    }
}
