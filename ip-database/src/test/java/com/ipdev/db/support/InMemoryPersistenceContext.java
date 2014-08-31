package com.ipdev.db.support;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.function.StandardSQLFunction;

public class InMemoryPersistenceContext extends AbstractPersistenceContext {

    private Log log = LogFactory.getLog(InMemoryPersistenceContext.class);

    private static final AtomicInteger databaseId = new AtomicInteger(0);
    private static final boolean USE_JDBC_PROXY_SPY = false;

    private Configuration configuration = null;
    private SessionFactory sessionFactory = null;
    private String schemaName = null;

    public InMemoryPersistenceContext setSchemaName(String schemaName) {
        this.schemaName = schemaName;
        return this;
    }

    @Override
    protected SessionFactory buildSessionFactory() {
        log.info("Creating in-memory persistence context configuration...");
        createConfiguration();

        log.info("Creating in-memory persistence context session factory...");
        createSessionFactory();

        log.info("Registering addtional SQL functions...");
        createSqlFunctions();

        log.info("Creating schema...");
        createSchema();

        return sessionFactory;
    }

    @SuppressWarnings("serial")
    private void createConfiguration() {
        Properties connectionProperties = new Properties() {
            {
                setProperty("hibernate.connection.driver_class",
                    (USE_JDBC_PROXY_SPY) ? "com.p6spy.engine.spy.P6SpyDriver" : "org.h2.Driver");
                setProperty("hibernate.dialect", "com.ipdev.db.support.H2SQLDialect");
                setProperty("hibernate.connection.username", "ipdev");
                setProperty("hibernate.connection.password", "");
                setProperty("hibernate.connection.url",
                    "jdbc:h2:mem:privateDatabase" + databaseId.incrementAndGet());

                setProperty("hibernate.default_schema", "ipdev");
                setProperty("hibernate.hbm2ddl.auto", "create");
            }
        };

        configuration = getBaselineConfiguration()
            .addProperties(connectionProperties)
            .addProperties(SQL_LOGGING_SUPPORT)
            .addProperties(DISABLE_SECOND_LEVEL_CACHE_SUPPORT)
            .addProperties(THREAD_SESSION_CONTEXT_SUPPORT)
            .addProperties(SINGLE_CONNECTION_POOLING_SUPPORT);

        initConfigExtra(configuration);

        configuration.addSqlFunction("trunc", new StandardSQLFunction("trunc", Hibernate.DATE));
    }

    protected void initConfigExtra(Configuration configuration) {
    } // added for supporting the flexibility of other package to reuse this test code usage.

    private void createSessionFactory() {
        sessionFactory = configuration.buildSessionFactory();
    }

    private void createSchema() {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(sessionFactory, configuration);
        databaseInitializer.setCreateSchema(true);
        databaseInitializer.setSchemaName(schemaName);
        databaseInitializer.init();
    }

    private void createSqlFunctions() {
        /*
         * HibernateUtil.executeStatements(sessionFactory, "CREATE ALIAS IF NOT EXISTS TRUNC FOR \"" +
         * H2TruncateFunction.NAME + "\";" );
         */
    }
}
