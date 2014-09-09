package com.ipdev.db.support;

import com.ipdev.architect.IntegrationTestCase;

public class IntegrationTestDbCase extends IntegrationTestCase {
    static String[] cniprSpringConfigResources = {
        "ipdev-db.spring.xml"
    };

    public IntegrationTestDbCase() {
        super(cniprSpringConfigResources);
    }
}
