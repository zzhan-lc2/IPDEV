package com.ipdev.cnipr.manager;

import java.util.List;

import com.ipdev.cnipr.entity.patent.CNIPRDBS;
import com.ipdev.cnipr.query.AttrField;
import com.ipdev.cnipr.query.ExpUnit;
import com.ipdev.common.datatype.DateRange;
import com.ipdev.common.entity.patent.Patent;

public interface PatentManager {
    
    Integer getPatentCountsByDbDateRange(CNIPRDBS db, AttrField dateField, DateRange dateRange);

    Integer getPatentCounts(List<CNIPRDBS> dbList, ExpUnit searchExpressions);

    List<Patent> findPatents(List<CNIPRDBS> dbList, ExpUnit searchExpressions, AttrField orderByField);

    List<Patent> findPatentsPaged(List<CNIPRDBS> dbList, ExpUnit searchExpressions, AttrField orderByField,
        int startIndex, int maxResults);
}
