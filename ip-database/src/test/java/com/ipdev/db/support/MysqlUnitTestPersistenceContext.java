package com.ipdev.db.support;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MysqlUnitTestPersistenceContext extends AbstractPersistenceContext {
    @Override
    protected SessionFactory buildSessionFactory() {
        Configuration configuration = getBaselineConfiguration()
            .addProperties(IPDEV_DEVO_CONNECTION_STRING)
            .addProperties(SINGLE_CONNECTION_POOLING_SUPPORT)
            .addProperties(THREAD_SESSION_CONTEXT_SUPPORT)
            .addProperties(SQL_LOGGING_SUPPORT)
            .addProperties(DISABLE_SECOND_LEVEL_CACHE_SUPPORT);

        return configuration.buildSessionFactory();
    }
}
