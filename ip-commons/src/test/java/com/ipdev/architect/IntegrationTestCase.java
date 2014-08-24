package com.ipdev.architect;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class IntegrationTestCase {
    static String[] springConfigResources = {
        "ipdev-common.spring.xml"
    };

    ApplicationContext context;
    String testFolder = System.getProperty("java.io.tmpdir");

    protected IntegrationTestCase() {
        this(springConfigResources);
    }

    protected IntegrationTestCase(String[] springResources) {
        context = new ClassPathXmlApplicationContext(
            springResources);
    }

    protected void setTestFolder(String testFolder) {
        this.testFolder = testFolder;
    }

    protected String createTempFolderForTest(String subFolder) {
        if (StringUtils.isEmpty(subFolder)) {
            subFolder = "ipdev-";
        }

        try {
            Path tmpPath = Files.createTempDirectory(subFolder);
            return tmpPath.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return testFolder;
        }

    }
}
