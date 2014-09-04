package com.ipdev.db.dao.patent;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.google.common.base.Preconditions;
import com.ipdev.common.dao.patent.PatentSearchDao;
import com.ipdev.common.dao.patent.PatentStorageDao;
import com.ipdev.common.entity.patent.Patent;
import com.ipdev.common.query.Query;
import com.ipdev.db.support.AbstractHibernateDao;

@SuppressWarnings("unchecked")
public class PatentDbHibernateDao extends AbstractHibernateDao<Patent> implements PatentStorageDao, PatentSearchDao {

    public void save(Patent patent) {
        Preconditions.checkNotNull(patent, "patent cannot be null");

        super.save(sanitize(patent));
    }

    public List<Patent> findPatentsByQuery(Query query, Set<String> sourceDbs) {
        Preconditions.checkNotNull(query, "query cannot be null");
        // TODO
        return null;
    }

    public Patent findPatentById(String id) {
        return this.findById(Patent.class, id);
    }

    public List<Patent> findPatentsByApplicant(String applicantName, Set<String> sourceDbs) {
        Preconditions.checkNotNull(applicantName, "applicantName cannot be null");

        Criteria query = this.createCriteria(Patent.class)
            .add(Restrictions.like("applicantNames", "%" + applicantName + "%"));
        if (CollectionUtils.isNotEmpty(sourceDbs)) {
            query.add(Restrictions.in("sourceDb", sourceDbs));
        }

        return query.list();
    }

    Patent sanitize(Patent entity) {
        if (entity.getCreationDate() == null) {
            entity.setCreationDate(new Date());
        }
        if (entity.getLastUpdatedDate() == null) {
            entity.setLastUpdatedDate(new Date());
        }
        return entity;
    }

}
