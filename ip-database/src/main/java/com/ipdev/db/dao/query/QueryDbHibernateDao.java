package com.ipdev.db.dao.query;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.ipdev.common.dao.query.QueryDao;
import com.ipdev.common.query.Query;
import com.ipdev.db.support.AbstractHibernateDao;

@Transactional
public class QueryDbHibernateDao extends AbstractHibernateDao<Query> implements QueryDao {

    public void save(Query query) {
        Preconditions.checkNotNull(query, "query cannot be null");

        super.save(sanitize(query));
    }

    public Query findById(String queryId) {
        return this.findById(Query.class, queryId);
    }

    @SuppressWarnings("unchecked")
    public List<Query> findByCreator(String creator) {
        Preconditions.checkNotNull(creator, "ctreator cannot be null");

        Criteria query = this.createCriteria(Query.class)
            .add(Restrictions.eq("creator", creator));

        return query.list();
    }

    Query sanitize(Query query) {
        if (query.getCreationDate() == null) {
            query.setCreationDate(new Date());
        }
        if (query.getLastUpdatedDate() == null) {
            query.setLastUpdatedDate(new Date());
        }
        return query;
    }

}
