package com.ipdev.cnipr.utility;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.api.client.repackaged.com.google.common.base.Preconditions;
import com.ipdev.common.entity.patent.Patent;

public class PatentUtility {

    public static String getSourceDbFromPid(String pid) {
        Preconditions.checkNotNull(pid, "pid cannot be null");

        int index = StringUtils.indexOf(pid, "@");
        if (index > 0) {
            return StringUtils.substring(pid, 0, index);
        }
        return null;
    }

    /**
     * Cleanup some information for Patent object based on CNIPR-specific data model
     * 
     * @param patent
     *            the patent to be cleanup
     * @return the patent
     */
    public static Patent cleanup(Patent patent) {
        Preconditions.checkNotNull(patent, "patent cannot be null");

        if (StringUtils.isBlank(patent.getSourceDb())) {
            patent.setSourceDb(getSourceDbFromPid(patent.getPid()));
        }
        // TODO: other stuff need to cleanup?

        return patent;
    }

    public static List<Patent> cleanup(List<Patent> patents) {
        if (patents == null)
            return null;

        for (Patent patent : patents) {
            patent = cleanup(patent);
        }
        return patents;
    }
}
