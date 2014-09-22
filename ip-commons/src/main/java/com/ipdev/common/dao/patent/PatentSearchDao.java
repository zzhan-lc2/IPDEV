package com.ipdev.common.dao.patent;

import java.util.List;
import java.util.Set;

import com.ipdev.common.entity.patent.Patent;
import com.ipdev.common.query.OrderExp;
import com.ipdev.common.query.Query;

public interface PatentSearchDao extends PatentSimpleSearchDao {

    /**
     * Find the list of patents by applicant name and the list of source dbs
     * 
     * @param applicantName
     *            the patent applicant name
     * @param sourceDbs
     *            the set of source dbs
     * @param maxReturns
     *            the maximum return records (<=0 means no limit)
     * 
     * @return the list of Patents
     */
    List<Patent> findPatentsByApplicant(String applicantName, Set<String> sourceDbs, int maxReturns);

    /**
     * Find the list of patents by our "sync" query with control parameter maxReturns (<=0 means no maximumrestriction)
     * 
     * @param query
     *            the Query
     * @param sourceDbs
     *            the set of source dbs
     * @param orderExp
     *            the order-by expression
     * @param maxReturns
     *            the maximum return records (<=0 means no limit)
     * @return the list of Patents
     */
    List<Patent> findPatentsByQuery(Query query, Set<String> sourceDbs, OrderExp orderExp, int maxReturns);

}
