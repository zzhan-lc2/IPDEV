package com.ipdev.cnipr.architect;

import org.testng.annotations.BeforeClass;

import com.ipdev.architect.AppConfig;
import com.ipdev.architect.AppConfig.Domain;
import com.ipdev.architect.IntegrationTestCase;
import com.ipdev.common.recorder.JsonFileDataRecorder;

public class IntegrationTestCniprCase extends IntegrationTestCase {
    static String[] cniprSpringConfigResources = {
        "ipdev-cnipr.spring.xml"
    };

    protected JsonFileDataRecorder recorder;

    public IntegrationTestCniprCase() {
        super(cniprSpringConfigResources);
    }

    @BeforeClass
    public void setupClass() {
        AppConfig.setDomain(Domain.TEST);

        recorder = new JsonFileDataRecorder();
    }

}
