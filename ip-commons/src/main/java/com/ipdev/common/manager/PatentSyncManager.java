package com.ipdev.common.manager;

import com.ipdev.common.query.Query;

public interface PatentSyncManager {

    int syncPatentsByQuery(Query searchQuery, RequestControlParams controlParams);

    int syncPatentsByUser(String user, RequestControlParams controlParams);
}
