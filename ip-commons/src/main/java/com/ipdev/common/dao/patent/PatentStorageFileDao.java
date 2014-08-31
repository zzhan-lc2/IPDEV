package com.ipdev.common.dao.patent;

import com.ipdev.common.entity.patent.Patent;

/**
 * This file-based DAO is to support store and retrieve patent object in file system. The content of patent object will
 * be stored in JSON format.
 * 
 * @author ZZ
 * 
 */
public class PatentStorageFileDao implements PatentStorageDao, PatentSimpleSearchDao {

    @Override
    public void save(Patent patent) {
        // TODO Auto-generated method stub

    }

    @Override
    public Patent findPatentById(String pid) {
        // TODO Auto-generated method stub
        return null;
    }

}
