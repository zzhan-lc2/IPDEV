package com.ipdev.apps.patent;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.ipdev.apps.IpAppBase;
import com.ipdev.cnipr.dao.patent.PatentSearchByCNIPRDao;
import com.ipdev.common.dao.patent.PatentSimpleSearchDao;
import com.ipdev.common.dao.patent.PatentStorageDao;
import com.ipdev.common.dao.query.QueryDao;
import com.ipdev.common.entity.patent.Patent;
import com.ipdev.common.query.Query;

public class CniprPatentSyncApp extends IpAppBase {
    PatentSearchByCNIPRDao cniprDao;
    PatentSimpleSearchDao localSearchDao;
    PatentStorageDao storageDao;

    QueryDao queryDao;
    Set<String> sourceDbs = Sets.newHashSet();

    public void setCniprDao(PatentSearchByCNIPRDao cniprDao) {
        this.cniprDao = cniprDao;
    }

    public void setLocalSearchDao(PatentSimpleSearchDao localSearchDao) {
        this.localSearchDao = localSearchDao;
    }

    public void setQueryDao(QueryDao queryDao) {
        this.queryDao = queryDao;
    }

    public void setStorageDao(PatentStorageDao storageDao) {
        this.storageDao = storageDao;
    }

    public void addSourceDb(String sourceDb) {
        Preconditions.checkNotNull(sourceDb, "sourceDb cannot be null");

        this.sourceDbs.add(sourceDb.toUpperCase());
    }

    public int syncPatentsByUser(String user) {
        Preconditions.checkNotNull(user, "user cannot be null");

        LOG.info("Starting to sync patents from CNIPR for user: " + user);

        int numbChanged = 0;

        // 1. find number of queries belonged to this user
        List<Query> queries = queryDao.findByCreator(user);
        if (CollectionUtils.isEmpty(queries)) {
            LOG.info("No query found for this user");
            return 0;
        }

        // 2. for each query, sync the db with CNIPR
        for (Query query : queries) {
            numbChanged += this.syncPatentsByQuery(query);
        }

        LOG.info("Total patents sync from CNIPR to local storage = {}", numbChanged);
        return numbChanged;
    }

    int syncPatentsByQuery(Query query) {
        int numbChanged = 0;

        LOG.info("Sync patents from CNIPR for query=[{}]", query);

        List<Patent> patents = cniprDao.findPatentsByQuery(query, sourceDbs, null);
        if (CollectionUtils.isEmpty(patents)) {
            LOG.info("No patent found from CNIPR");
            return 0;
        }

        // for each patent id, check if our DB already has it (TODO: we might still need to update it if there's status
        // change in Patent detail)
        for (Patent patent : patents) {
            Patent db = this.localSearchDao.findPatentById(patent.getPid());
            if (db != null) {
                // TODO: we might still need to update it if there's status change in Patent detail
                continue;
            }

            patent = cniprDao.findPatentById(patent.getPid());
            this.storageDao.save(patent);
            numbChanged++;
        }
        LOG.info("Number of patents sync from CNIPR to local storage = {}", numbChanged);

        return numbChanged;
    }

    public static void main(String[] args) {
        IpAppBase.configure(args, "CniprPatentSync");

        CniprPatentSyncApp app = IpAppBase.getApplication(CniprPatentSyncApp.class);

        // TODO
    }
}
