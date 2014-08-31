package com.ipdev.cnipr.architect;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.testng.annotations.BeforeClass;

import com.ipdev.architect.IntegrationTestCase;
import com.ipdev.cnipr.dao.patent.AuthTokenManager;
import com.ipdev.common.recorder.JsonFileDataRecorder;

public class IntegrationTestCniprCase extends IntegrationTestCase {
    static String[] cniprSpringConfigResources = {
        "ipdev-cnipr.spring.xml"
    };

    protected AuthTokenManager tokenManager;
    protected JsonFileDataRecorder recorder;

    public IntegrationTestCniprCase() {
        super(cniprSpringConfigResources);
    }

    @BeforeClass
    public void setupClass() {
        tokenManager = new AuthTokenManager();

        recorder = new JsonFileDataRecorder();
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
