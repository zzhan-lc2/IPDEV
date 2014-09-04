package com.ipdev.common.utility.file;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.ipdev.common.entity.patent.Patent;

public class PatentFileUtility {
    public static final String FOLDER_SEP = "/";
    public static final int PATENT_ID_FULL_LENGTH = 12;

    public static URI getPatentFileURI(Patent patent, URI baseLocation, String fileExtension) throws URISyntaxException {
        Preconditions.checkNotNull(patent, "patent cannot be null");
        Preconditions.checkNotNull(baseLocation, "baseLocation cannot be null");

        String pid = patent.getPid();

        String path = patentPidToFolderPath(pid, patent.getSourceDb());

        int index = StringUtils.indexOf(pid, "@");
        if (index > 0) {
            pid = StringUtils.substring(pid, index + 1);
        }

        StringBuilder sb = new StringBuilder()
            .append(baseLocation.getPath()).append(FOLDER_SEP)
            .append(path)
            .append(pid).append(fileExtension);

        return new URI(sb.toString());
    }

    public static String patentPidToFolderPath(String pid, String sourceDb) {
        // get sub-folders presentation of patent id
        int index = StringUtils.indexOf(pid, "@");
        if (index > 0) {
            if (StringUtils.isBlank(sourceDb)) {
                sourceDb = StringUtils.substring(pid, 0, index);
            }
            pid = StringUtils.substring(pid, index + 1);
        }
        // get the country code
        String country = StringUtils.substring(pid, 0, 2);
        String pidMod = StringUtils.substring(pid, 2);

        // right padded to 12 characters
        pidMod = StringUtils.rightPad(pidMod, PATENT_ID_FULL_LENGTH, "0");

        String[] subs = pidMod.split("(?<=\\G.{4})");

        StringBuilder sb = new StringBuilder()
            .append(country).append(FOLDER_SEP)
            .append(sourceDb).append(FOLDER_SEP);
        for (String sub : subs) {
            sb.append(sub).append(FOLDER_SEP);
        }
        return sb.toString();
    }
}
