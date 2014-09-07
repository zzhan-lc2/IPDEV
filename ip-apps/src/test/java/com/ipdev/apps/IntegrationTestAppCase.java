package com.ipdev.apps;

import org.testng.annotations.BeforeClass;

import com.ipdev.architect.AppConfig;
import com.ipdev.architect.AppConfig.Domain;
import com.ipdev.architect.IntegrationTestCase;
import com.ipdev.common.recorder.JsonFileDataRecorder;

public class IntegrationTestAppCase extends IntegrationTestCase {
    static String[] appSpringConfigResources = {
        "ipdev-apps-common.spring.xml"
    };

    protected JsonFileDataRecorder recorder;

    public IntegrationTestAppCase() {
        super(appSpringConfigResources);
    }

    @BeforeClass
    public void setupClass() {
        AppConfig.setDomain(Domain.TEST);

        recorder = new JsonFileDataRecorder();
    }

}
