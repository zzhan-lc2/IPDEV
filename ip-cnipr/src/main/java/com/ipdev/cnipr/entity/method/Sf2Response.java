package com.ipdev.cnipr.entity.method;

import java.util.List;

import com.ipdev.cnipr.utility.PatentUtility;
import com.ipdev.common.entity.patent.Patent;

public class Sf2Response extends CniprResponse<Patent> {
    private static final long serialVersionUID = 1L;

    @Override
    public List<Patent> getResults() {
        List<Patent> results = super.getResults();
        return PatentUtility.cleanup(results);
    }
}
