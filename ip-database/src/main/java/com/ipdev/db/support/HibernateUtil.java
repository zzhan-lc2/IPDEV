package com.ipdev.db.support;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.ipdev.common.UnrecoverablePersistenceException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class HibernateUtil {
    public static <T> T optionalUniqueResult(List<T> results) {
        if (results == null || results.isEmpty()) {
            return null;
        }

        if (results.size() > 1) {
            throw new HibernateException("Unique result violation.  At most one result was expected, but "
                + results.size() + " were returned: " + results);
        } else {
            return results.get(0);
        }
    }

    public static long generateNextSequenceValue(Session session, String sequenceName) {
        Dialect dialect = ((SessionFactoryImplementor) session.getSessionFactory()).getDialect();
        String sql = dialect.getSequenceNextValString(sequenceName);

        Query query = session.createSQLQuery(sql);
        long result = ((Number) query.uniqueResult()).longValue();

        return result;
    }

    public static String getJdbcUrl(SessionFactory sessionFactory, Configuration configuration) {
        if (StringUtils.isNotEmpty(configuration.getProperty("hibernate.connection.url"))) {
            return configuration.getProperty("hibernate.connection.url");
        }
        if (SessionFactoryUtils.getDataSource(sessionFactory) instanceof ComboPooledDataSource) {
            return ((ComboPooledDataSource) SessionFactoryUtils.getDataSource(sessionFactory)).getJdbcUrl();
        }
        else {
            return "<unknown database>";
        }
    }

    public static boolean isOracleDialectInUse(Session session) {
        Dialect dialect = ((SessionFactoryImplementor) session.getSessionFactory()).getDialect();
        return dialect.getClass().getSimpleName().matches("Oracle.*Dialect");
    }

    public static void executeStatement(Connection connection, String command) throws UnrecoverablePersistenceException {
        try {
            Statement statement = connection.createStatement();
            statement.execute(command);
            statement.close();
        } catch (SQLException ex) {
            throw new UnrecoverablePersistenceException("The SQL command '" + command + "' failed.", ex);
        }
    }

    @SuppressWarnings("deprecation")
    public static void executeStatements(SessionFactory sessionFactory, String... commands)
        throws UnrecoverablePersistenceException {
        Session session = sessionFactory.openSession();
        Connection connection = session.connection();
        try {
            for (String command : commands) {
                executeStatement(connection, command);
            }
        } finally {
            session.close();
        }
    }
}
