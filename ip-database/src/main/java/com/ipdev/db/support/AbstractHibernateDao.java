package com.ipdev.db.support;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unchecked")
public abstract class AbstractHibernateDao<T> {
    /** The session factory. */
    private SessionFactory sessionFactory;

    /**
     * Find all entities.
     * 
     * @param cls
     *            the entity class
     * @param orderBy
     *            the order by
     * @return the list of found objects
     */
    protected List<T> findAll(Class<T> cls, String orderBy) {
        String className = cls.getName();

        StringBuilder query = new StringBuilder();
        query.append("from ").append(className);

        if (StringUtils.isNotBlank(orderBy)) {
            query.append(" order by ").append(orderBy);
        }

        List<T> results = getCurrentSession().createQuery(query.toString()).setCacheable(true)
            .setCacheRegion(className + ".findAllQueryCache").list();

        return results;
    }

    /**
     * Find by id.
     * 
     * @param id
     *            the id
     * @param cls
     *            the class to find
     * @return the t
     */
    public T findById(Class<T> cls, Serializable id) {
        return (T) getCurrentSession().get(cls, id);
    }

    /**
     * Save entity.
     * 
     * @param entity
     *            the entity
     */
    public void save(T entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    /**
     * Merge entity
     * 
     * @param entity
     */
    public void merge(T entity) {
        getCurrentSession().merge(entity);
    }

    /**
     * Gets the current session.
     * 
     * @return the current session
     */
    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected Criteria createCriteria(Class<T> persistentClass) {
        return getCurrentSession().createCriteria(persistentClass);
    }

    public void flushAndEvict(Collection<T> entities) {
        Session session = getCurrentSession();
        session.flush();

        for (T entity : entities) {
            session.evict(entity);
        }

    }

    /**
     * Gets the session factory.
     * 
     * @return the session factory
     */
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Sets the session factory.
     * 
     * @param sessionFactory
     *            the new session factory
     */
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
