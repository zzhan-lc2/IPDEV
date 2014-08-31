package com.ipdev.common.utility.file;

import java.net.URI;

import com.google.common.base.Preconditions;
import com.ipdev.common.entity.patent.Patent;

public class PatentFileUtility {
    public static final String FOLDER_SEP = "/";

    public static URI getPatentFileURI(Patent patent, URI baseLocation) {
        Preconditions.checkNotNull(patent, "patent cannot be null");
        Preconditions.checkNotNull(baseLocation, "baseLocation cannot be null");

        // get sub-folders presentation of patent id
        StringBuilder sb = new StringBuilder()
            .append(patent.getSourceDb()).append(FOLDER_SEP);
        // TODO
        return null;
    }
}
