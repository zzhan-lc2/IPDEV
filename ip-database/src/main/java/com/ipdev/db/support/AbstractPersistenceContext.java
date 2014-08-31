package com.ipdev.db.support;

import java.io.IOException;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@SuppressWarnings("serial")
public abstract class AbstractPersistenceContext implements PersistenceContext {
    private SessionFactory sessionFactory;

    String resourcesURI = "/com/ipdev/db/support/mapping/*.hbm.xml";

    abstract protected SessionFactory buildSessionFactory();

    public void setResourcesURI(String resourcesURI) {
        this.resourcesURI = resourcesURI;
    }

    public final synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }

    protected final Configuration getBaselineConfiguration() {
        Configuration configuration = new Configuration();

        PathMatchingResourcePatternResolver resourceResolver =
            new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());
        try {
            Resource[] resources = resourceResolver.getResources(resourcesURI);
            for (Resource resource : resources) {
                configuration.addInputStream(resource.getInputStream());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return configuration;
    }

    static Properties IPDEV_DEVO_CONNECTION_STRING = new Properties() {
        {
            setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            setProperty("hibernate.connection.url", "localhost:8806/ipdev");
            setProperty("hibernate.connection.username", "ipdev");
            setProperty("hibernate.connection.password", "yuyangip");
        }
    };

    static Properties SQL_LOGGING_SUPPORT = new Properties() {
        {
            setProperty("hibernate.show_sql", "true");
            setProperty("hibernate.format_sql", "true");
            setProperty("hibernate.use_sql_comments", "true");
        }
    };

    static Properties C3PO_POOLING_SUPPORT = new Properties() {
        {
            setProperty("hibernate.c3p0.acquire_increment", "1");
            setProperty("hibernate.c3p0.idle_test_period", "100");
            setProperty("hibernate.c3p0.min_size", "5");
            setProperty("hibernate.c3p0.max_size", "20");
            setProperty("hibernate.c3p0.timeout", "100");
            setProperty("hibernate.c3p0.max_statements", "0");
        }
    };

    static Properties SINGLE_CONNECTION_POOLING_SUPPORT = new Properties() {
        {
            setProperty("hibernate.connection.pool_size", "1");
        }
    };

    static Properties DISABLE_SECOND_LEVEL_CACHE_SUPPORT = new Properties() {
        {
            setProperty("hibernate.cache.use_second_level_cache", "false");
            setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        }
    };

    static Properties THREAD_SESSION_CONTEXT_SUPPORT = new Properties() {
        {
            setProperty("hibernate.current_session_context_class", "thread");
        }
    };

}
