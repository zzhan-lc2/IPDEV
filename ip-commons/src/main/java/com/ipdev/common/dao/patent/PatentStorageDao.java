package com.ipdev.common.dao.patent;

import com.ipdev.common.entity.patent.Patent;

public interface PatentStorageDao {

    /**
     * Save the patent object
     * 
     * @param patent
     *            the patent
     */
    void save(Patent patent);

}
