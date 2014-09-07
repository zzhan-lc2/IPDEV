package com.ipdev.architect;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ipdev.architect.AppConfig.Domain;

public abstract class IntegrationTestCase {
    static String[] springConfigResources = {
        "ipdev-common.spring.xml"
    };

    public Domain domain = Domain.TEST;
    ApplicationContext context;
    String testFolder = System.getProperty("java.io.tmpdir");

    protected IntegrationTestCase() {
        this(springConfigResources);
    }

    protected IntegrationTestCase(String[] springResources) {
        AppConfig.setDomain(domain);
        context = new ClassPathXmlApplicationContext(
            springResources);
        SpringContextTestSupport.initialize("ipdev-cnipr-test", springResources);
    }

    protected void setDomain(Domain domain) {
        this.domain = domain;
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

    protected <T> T getBean(Class<T> clazz) {
        return SpringContextTestSupport.getBean(clazz);
    }

    protected <T> T getBean(String beanName, Class<T> clazz) {
        return SpringContextTestSupport.getBean(beanName, clazz);
    }

    protected BufferedWriter createTempOutputFile(String fileName) throws IOException {
        String path = System.getProperty("java.io.tmpdir");
        path = path + "/" + fileName;
        System.out.println("Creating file: " + path);
        Path outputPath = Paths.get(path);

        BufferedWriter output = Files.newBufferedWriter(
            outputPath,
            Charset.forName("UTF-8"),
            StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        return output;
    }
}
