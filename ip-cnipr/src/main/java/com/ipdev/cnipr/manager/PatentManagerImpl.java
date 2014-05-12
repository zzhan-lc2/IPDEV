package com.ipdev.cnipr.manager;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.ipdev.cnipr.dao.patent.PatentSearchByCNIPRDao;
import com.ipdev.cnipr.entity.method.Sf1Request;
import com.ipdev.cnipr.entity.method.Sf1Response;
import com.ipdev.cnipr.entity.patent.CNIPRDBS;
import com.ipdev.cnipr.query.AttrField;
import com.ipdev.cnipr.query.ExpUnit;
import com.ipdev.cnipr.query.ValueField;
import com.ipdev.common.datatype.DateRange;
import com.ipdev.common.entity.patent.Patent;

public class PatentManagerImpl implements PatentManager {
    private static final Log LOG = LogFactory.getLog(PatentManagerImpl.class);

    PatentSearchByCNIPRDao cniprDao;

    public void setCniprDao(PatentSearchByCNIPRDao cniprDao) {
        this.cniprDao = cniprDao;
    }


    @Override
    public Integer getPatentCountsByDbPubDate(CNIPRDBS db, DateRange pubDateRange) {
        Preconditions.checkNotNull(db, "db cannot be null");
        Preconditions.checkNotNull(pubDateRange, "pubDateRange cannot be null");

        try {
            ExpUnit exp = ExpUnit.withAttribute(AttrField.PUB_DATE)
                .setValue(new ValueField(pubDateRange));
            Sf1Request request = new Sf1Request();
            request.setExpression(exp.toExpression());
            request.addDb(db.toString());

            request.setFrom(0);
            request.setTo(1);
            Sf1Response response = cniprDao.absSearchByExpression(request);
            if (response == null) {
                return null;
            }
            return response.getTotal();
        } catch (Exception e) {
            LOG.error("Caught exception:" + e);
            return null;
        }
    }

    @Override
    public Integer getPatentCounts(List<CNIPRDBS> dbList, ExpUnit searchExpressions) {
        Preconditions.checkNotNull(dbList, "dbList cannot be null");
        Preconditions.checkNotNull(searchExpressions, "searchExpressions cannot be null");

        try {
            Sf1Request request = new Sf1Request();
            request.setExpression(searchExpressions.toExpression());
            for (CNIPRDBS db : dbList) {
                request.addDb(db.toString());
            }

            request.setFrom(0);
            request.setTo(1);
            Sf1Response response = cniprDao.absSearchByExpression(request);
            if (response == null) {
                return null;
            }
            return response.getTotal();
        } catch (Exception e) {
            LOG.error("Caught exception:" + e);
            return null;
        }
    }

    @Override
    public List<Patent> findPatents(List<CNIPRDBS> dbList, ExpUnit searchExpressions, AttrField orderByField) {
        Preconditions.checkNotNull(dbList, "dbList cannot be null");
        Preconditions.checkNotNull(searchExpressions, "searchExpressions cannot be null");

        return this.findPatentsPaged(dbList, searchExpressions, orderByField, 0, -1);
    }

    @Override
    public List<Patent> findPatentsPaged(List<CNIPRDBS> dbList, ExpUnit searchExpressions, AttrField orderByField,
        int startIndex, int maxResults) {
        Preconditions.checkNotNull(dbList, "dbList cannot be null");
        Preconditions.checkNotNull(searchExpressions, "searchExpressions cannot be null");

        List<Patent> results = Lists.newArrayList();

        try {
            Sf1Request request = new Sf1Request();
            request.setExpression(searchExpressions.toExpression());
            for (CNIPRDBS db : dbList) {
                request.addDb(db.toString());
            }
            if (null != orderByField) {
                request.setOrderBy(orderByField.getName(), false);
            }

            int start = startIndex;
            boolean done = false;
            while (!done) {
                int step = Math.min(Sf1Request.MAX_SEARCH_RESULTS, maxResults - results.size());
                request.setFrom(start);
                request.setTo(start + step);
                Sf1Response response = cniprDao.absSearchByExpression(request);
                if (response.getResults() == null || response.getResults().isEmpty()) {
                    break;
                }
                if (response != null) {
                    results.addAll(response.getResults());
                }
                if (maxResults > 0) {
                    if (results.size() >= maxResults) {
                        done = true;
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Caught exception in findPatentsPaged():" + e);
        }
        return results;
    }

}
