package com.ipdev.db.support;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public abstract class HibernateDaoTestCase {
    private Log log = LogFactory.getLog(HibernateDaoTestCase.class);

    protected SessionFactory sessionFactory;
    protected Session session;
    protected Transaction transaction;

    // ~~ Public Hooks ~~~

    /**
     * Override to setup the SessionFactory this test case will use.
     * 
     * This method will be called exactly once per test class. A single session will be opened against the factory.
     */
    abstract protected SessionFactory setupSessionFactory();

    /**
     * Override to setup the class under test and any dependent data.
     * 
     * This method will be called exactly once per test class. Any DAOs and dependent data should be initialized here.
     * 
     * @throws Exception
     *             the overriding code may freely throw any Exception.
     */
    protected void setupClass() throws Exception {
        // assume no setup class required
        ;
    }

    /**
     * Mimic a commit.
     * 
     * Any pending SQL will be flushed to the database and executed, and the Hibernate session cache cleared. No actual
     * commit is made to the database. Any changes will be rolled back when the test case finishes.
     */
    protected void executeTestTransaction() {
        log.info("Executing test transaction...");
        flushAndClear();
    }

    /**
     * Optionally override to clean up any side-effects of the tests.
     * 
     * Typically this is unnecessary. So long as nothing is actually committed to the database, it will be rolled back
     * automatically when the test classes session is closed.
     * 
     * @throws Exception
     *             the overriding code may freely throw any Exception.
     */
    protected void teardownClass() throws Exception {
        // assume no teardown required
        ;
    }

    // ~~ Internal Hooks ~~~

    @BeforeClass(groups = { "support" })
    protected final void beforeClass() throws Exception {
        log.debug("Initializing persistence context and session for test class " + this.getClass().getSimpleName());
        sessionFactory = setupSessionFactory();

        session = sessionFactory.getCurrentSession();
        transaction = session.getTransaction();
        transaction.setTimeout(180);
        transaction.begin();

        initDaoFields();
        setupClass();
        flushAndClear();
    }

    @AfterClass(groups = { "support" })
    protected final void afterClass() throws Exception {
        log.debug("Closing persistence context and session for test class " + this.getClass().getSimpleName());
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        transaction = null;
        if (session != null && session.isOpen()) {
            session.close();
        }
        session = null;
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
        sessionFactory = null;

        teardownClass();
    }

    @BeforeMethod(groups = { "support" })
    protected void initializeTestTransactionBeforeMethod() {
        flushAndClear();
    }

    @AfterMethod(groups = { "support" })
    protected void cleanupTestTransactionAfterMethod() {
        flushAndClear();
    }

    protected void flushAndClear() {
        if (session != null && session.isOpen()) {
            session.flush();
            session.clear();
        }
    }

    protected <T extends AbstractHibernateDao<?>> T createDao(Class<T> daoClass) {
        try {
            T dao = daoClass.newInstance();
            dao.setSessionFactory(sessionFactory);
            return dao;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initDaoFields() {
        try {
            List<Field> fields = new ArrayList<Field>();
            Class<?> cl = this.getClass();
            while (cl != null) {
                Field[] declaredFields = cl.getDeclaredFields();
                fields.addAll(Arrays.asList(declaredFields));
                cl = cl.getSuperclass();
            }

            for (Field field : fields) {
                CreateDao annotation = field.getAnnotation(CreateDao.class);
                if (annotation != null) {
                    Class<? extends AbstractHibernateDao<?>> daoClass = annotation.value();
                    Object daoInstance = createDao(daoClass);
                    field.setAccessible(true);
                    field.set(this, daoInstance);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
