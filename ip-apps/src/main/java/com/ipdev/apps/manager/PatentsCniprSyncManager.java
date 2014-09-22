package com.ipdev.apps.manager;

import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.ipdev.cnipr.dao.patent.PatentSearchByCNIPRDao;
import com.ipdev.common.auth.AuthTokenExpireException;
import com.ipdev.common.dao.patent.PatentSimpleSearchDao;
import com.ipdev.common.dao.patent.PatentStorageDao;
import com.ipdev.common.dao.patent.PatentStorageFileDao;
import com.ipdev.common.dao.query.QueryDao;
import com.ipdev.common.entity.patent.Patent;
import com.ipdev.common.manager.PatentSyncManager;
import com.ipdev.common.manager.RequestControlParams;
import com.ipdev.common.query.Query;

public class PatentsCniprSyncManager implements PatentSyncManager {
    private static final Logger LOG = LoggerFactory.getLogger(PatentsCniprSyncManager.class);

    @Nonnull
    PatentSearchByCNIPRDao cniprDao;
    PatentSimpleSearchDao localSearchDao;
    PatentStorageFileDao fileStorageDao;
    PatentStorageDao dbStorageDao;
    QueryDao queryDao;

    public void setCniprDao(PatentSearchByCNIPRDao cniprDao) {
        this.cniprDao = cniprDao;
    }

    public void setLocalSearchDao(PatentSimpleSearchDao localSearchDao) {
        this.localSearchDao = localSearchDao;
    }

    public void setQueryDao(QueryDao queryDao) {
        this.queryDao = queryDao;
    }

    public void setFileStorageDao(PatentStorageFileDao storageDao) {
        this.fileStorageDao = storageDao;
    }

    public void setDbStorageDao(PatentStorageDao dbStorageDao) {
        this.dbStorageDao = dbStorageDao;
    }

    @Override
    public int syncPatentsByQuery(Query searchQuery, RequestControlParams controlParams) {
        Preconditions.checkNotNull(searchQuery, "searchQuery cannot be null");
        Preconditions.checkNotNull(controlParams, "controlParams cannot be null");

        if (controlParams.getEnableFileStorage() && StringUtils.isNotBlank(controlParams.getFileBaseDir())) {
            this.fileStorageDao.setBaseLocation(controlParams.getFileBaseDir());
        }

        int numbChanged = 0;

        LOG.info("Sync patents from CNIPR for query=[{}]", searchQuery);

        try {
            List<Patent> patents = cniprDao.findPatentsByQuery(searchQuery, controlParams.getSourceDbs(), null,
                controlParams.getMaxResults());
            if (CollectionUtils.isEmpty(patents)) {
                LOG.info("No patent found from CNIPR");
                return 0;
            }

            // for each patent id, check if our DB already has it and if the status changed when it does exist)
            for (Patent patent : patents) {
                if (!isPatentNeedToBeUpdated(patent, controlParams.getForceUpdate())) {
                    continue;
                }

                patent = cniprDao.findPatentById(patent.getPid());
                if (controlParams.getEnableFileStorage()) {
                    this.fileStorageDao.save(patent);
                }
                this.dbStorageDao.save(patent);
                numbChanged++;
            }
            LOG.info("Number of patents sync from CNIPR to local storage = {}", numbChanged);
        } catch (AuthTokenExpireException e) {
            LOG.warn("Caught AuthTokenExpireException", e);
            cniprDao.getTokenManager().handleTokenExpireException(e);
        }

        return numbChanged;
    }

    boolean isPatentNeedToBeUpdated(Patent patent, boolean forceUpdate) {
        if (forceUpdate)
            return true;

        Patent db = this.localSearchDao.findPatentById(patent.getPid());
        if (db != null) {
            // check if there's legal status change in Patent detail
            if (StringUtils.equals(db.getLprs(), patent.getLprs())) {
                return false; // no status change
            }
        }
        return true;
    }

    @Override
    public int syncPatentsByUser(String user, RequestControlParams controlParams) {
        Preconditions.checkNotNull(user, "user cannot be null");
        Preconditions.checkNotNull(controlParams, "controlParams cannot be null");

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
            numbChanged += this.syncPatentsByQuery(query, controlParams);
        }

        LOG.info("Total patents sync from CNIPR to local storage = {}", numbChanged);
        return numbChanged;
    }

}
