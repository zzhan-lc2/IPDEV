package com.ipdev.db.support;

import org.hibernate.SessionFactory;

public class HibernateDaoUnitTestCase extends HibernateDaoTestCase {
    private static boolean isInMemoryDatabaseEnabled = true;

    static final String IPDEV_DB_SCHEMA = "ipdev";

    @Override
    protected SessionFactory setupSessionFactory() {
        return isInMemoryDatabaseEnabled
            ? new InMemoryPersistenceContext().setSchemaName(IPDEV_DB_SCHEMA).getSessionFactory()
            : new MysqlUnitTestPersistenceContext().getSessionFactory();
    }

    public static void setIsInMemoryDatabaseEnabled(boolean value) {
        isInMemoryDatabaseEnabled = value;
    }
}
