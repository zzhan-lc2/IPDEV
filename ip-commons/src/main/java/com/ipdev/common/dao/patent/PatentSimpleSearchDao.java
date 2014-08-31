package com.ipdev.common.dao.patent;

import com.ipdev.common.entity.patent.Patent;

public interface PatentSimpleSearchDao {
    /**
     * Retrieve patent object based on patent id
     * 
     * @param pid
     *            the patent id
     * @return the Patent object
     */
    Patent findPatentById(String pid);

}
