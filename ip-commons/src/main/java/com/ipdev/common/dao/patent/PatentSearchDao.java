package com.ipdev.common.dao.patent;

import java.util.List;
import java.util.Set;

import com.ipdev.common.entity.patent.Patent;
import com.ipdev.common.query.Query;

public interface PatentSearchDao extends PatentSimpleSearchDao {

    /**
     * Find the list of patents by applicant name and the list of source dbs
     * 
     * @param applicantName
     *            the patent applicant name
     * @param controlParams
     *            the RequestControlParams
     * @return the list of Patents
     */
    List<Patent> findPatentsByApplicant(String applicantName, RequestControlParams controlParams);

    /**
     * Find the list of patents by our "sync" query with control parameter maxReturns (<=0 means no maximumrestriction)
     * 
     * @param query
     *            the Query
     * @param controlParams
     *            the RequestControlParams
     * @return the list of Patents
     */
    List<Patent> findPatentsByQuery(Query query, RequestControlParams controlParams);

    int getTotalPatentsByQuery(Query query, Set<String> sourceDbs);
}
