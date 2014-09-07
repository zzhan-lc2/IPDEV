package com.ipdev.cnipr.dao.patent;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ipdev.cnipr.architect.IntegrationTestCniprCase;

@Test(groups = { "integration" })
public class IntegrationTest_ImageCniprHandler extends IntegrationTestCniprCase {

    ImageCniprHandler handler;

    @BeforeMethod
    public void init() {
        handler = this.getBean(ImageCniprHandler.class);

    }

    public void test_downloadDraws() {
        String drawsPath = "XmlData/FM/20140416/201410026743.6";

        BufferedImage image = handler.downloadDraws(drawsPath);
        Assert.assertNotNull(image);

        // dump the image
        String path = System.getProperty("java.io.tmpdir");
        path = path + "/" + "saved.gif";
        File outputfile = new File(path);
        try {
            ImageIO.write(image, "gif", outputfile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void test_downloadTiffs() {
        // TODO
    }

    public void test_downloadThumbnails() {
        // TODO
    }

}
