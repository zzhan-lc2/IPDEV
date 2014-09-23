package com.ipdev.common.manager;

import com.ipdev.common.dao.patent.RequestControlParams;
import com.ipdev.common.query.Query;

public interface PatentSyncManager {

    int getTotalPatentsCountByQuery(Query searchQuery, RequestControlParams controlParams);

    int syncPatentsByQuery(Query searchQuery, RequestControlParams controlParams);

}
