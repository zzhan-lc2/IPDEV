package com.ipdev.db.dao.patent;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.ipdev.common.dao.patent.PatentSearchDao;
import com.ipdev.common.dao.patent.PatentStorageDao;
import com.ipdev.common.dao.patent.RequestControlParams;
import com.ipdev.common.entity.patent.Patent;
import com.ipdev.common.query.OrderExp;
import com.ipdev.common.query.OrderExp.Direction;
import com.ipdev.common.query.Query;
import com.ipdev.db.support.AbstractHibernateDao;

@SuppressWarnings("unchecked")
@Transactional
public class PatentDbHibernateDao extends AbstractHibernateDao<Patent> implements PatentStorageDao, PatentSearchDao {

    public void save(Patent patent) {
        Preconditions.checkNotNull(patent, "patent cannot be null");

        super.save(sanitize(patent));
    }

    public List<Patent> findPatentsByQuery(Query query, RequestControlParams controlParams) {
        Preconditions.checkNotNull(query, "query cannot be null");
        // TODO
        return null;
    }

    public Patent findPatentById(String id) {
        return this.findById(Patent.class, id);
    }

    public List<Patent> findPatentsByApplicant(String applicantName, RequestControlParams controlParams) {
        Preconditions.checkNotNull(applicantName, "applicantName cannot be null");

        Criteria query = this.createCriteria(Patent.class)
            .add(Restrictions.like("applicantNames", "%" + applicantName + "%"));
        if (CollectionUtils.isNotEmpty(controlParams.getSourceDbs())) {
            query.add(Restrictions.in("sourceDb", controlParams.getSourceDbs()));
        }

        Order orderBy = Order.desc("appDate"); // default
        OrderExp orderExp = controlParams.getOrderExp();
        if (orderExp != null) {
            // set order by orderExp, make sure we get expression<--> column name mapping correct!!
            String column = orderExpToColumn(orderExp.getOrderBy());
            if (orderExp.getDirection().equals(Direction.ASC)) {
                orderBy = Order.asc(column);
            } else {
                orderBy = Order.desc(column);
            }
        }
        query.addOrder(orderBy);

        int maxResults = controlParams.getToIndex() - controlParams.getFromIndex();
        if (maxResults > 0) {
            query.setFirstResult(controlParams.getFromIndex());
            query.setMaxResults(maxResults);
        }

        return query.list();
    }

    String orderExpToColumn(String orderBy) {
        // TODO
        return "appDate";
    }

    Patent sanitize(Patent entity) {
        if (entity.getCreationDate() == null) {
            entity.setCreationDate(new Date());
        }
        entity.setLastUpdatedDate(new Date());
        return entity;
    }

    public int getTotalPatentsByQuery(Query query, Set<String> sourceDbs) {
        // TODO Auto-generated method stub
        return 0;
    }

}
