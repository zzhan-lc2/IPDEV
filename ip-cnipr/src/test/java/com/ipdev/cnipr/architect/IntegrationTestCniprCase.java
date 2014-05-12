package com.ipdev.cnipr.architect;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.testng.annotations.BeforeClass;

import com.ipdev.architect.IntegrationTestCase;
import com.ipdev.cnipr.dao.patent.AuthTokenManager;
import com.ipdev.common.auth.AuthToken;
import com.ipdev.common.recorder.JsonFileDataRecorder;

public class IntegrationTestCniprCase extends IntegrationTestCase {
	static String[] cniprSpringConfigResources = {
		"ipdev-common.spring.xml",
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
        AuthToken token = tokenManager.getAuthToken();
        // TODO: remove this hard-code style
        token.setAccessToken("d9eea4e8ad1119ed30b4e8134aa37b47");
        token.setOpenId("ab6f1509925e39836a32f7dd22224a15");
        token.setOpenKey("8896dbdd8a9323c1e78a9d8c87f5f4c8");
        try {
            Date acquireDate = DateUtils.parseDate("2014-04-12", "yyyy-MM-dd");
            token.setTokenExpireTime(new Date(acquireDate.getTime() + 2592000));
        } catch (Exception e) {
            System.err.println("Caught exception while trying to get acquireDate for token");
        }

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
