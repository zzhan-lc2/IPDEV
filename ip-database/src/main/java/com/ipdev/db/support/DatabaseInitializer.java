package com.ipdev.db.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

public class DatabaseInitializer {
    private final Log log = LogFactory.getLog(DatabaseInitializer.class);

    private final SessionFactory sessionFactory;
    private final Configuration configuration;
    private final String databaseName;
    private List<String> sequenceList = new ArrayList<String>();

    private String schemaName;
    private boolean createSchema = false;
    private boolean createEntities = false;
    private boolean createFunctions = false;

    public DatabaseInitializer(LocalSessionFactoryBean springSessionFactoryBean) {
        this((SessionFactory) springSessionFactoryBean.getObject(), springSessionFactoryBean.getConfiguration());
    }

    public DatabaseInitializer(SessionFactory sessionFactory, Configuration configuration) {
        this.sessionFactory = sessionFactory;
        this.configuration = configuration;
        this.databaseName = HibernateUtil.getJdbcUrl(sessionFactory, configuration);

        initSequenceLists();
    }

    private void initSequenceLists() {
        // TODO: add complete sequence names here for testing
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public void setCreateSchema(boolean createSchema) {
        this.createSchema = createSchema;
    }

    public void setCreateEntities(boolean createEntities) {
        this.createEntities = createEntities;
    }

    public void setCreateFunctions(boolean createFunctions) {
        this.createFunctions = createFunctions;
    }

    public void init() {
        log.info("Initializing database for the database " + databaseName);
        if (createSchema) {
            createSchema();
        }
        if (createEntities) {
            createEntities();
        }
        if (createFunctions) {
            createFunctions();
        }
    }

    @SuppressWarnings(value = "deprecation")
    protected void createSchema() {
        log.info("Creating schema for the database " + databaseName);

        Session session = sessionFactory.openSession();
        try {
            if (StringUtils.isNotEmpty(schemaName)) {
                String schemaCommand = "CREATE SCHEMA IF NOT EXISTS " + schemaName;
                HibernateUtil.executeStatement(session, schemaCommand);
            }

            for (String seq : this.sequenceList) {
                String seqCommand = "CREATE SEQUENCE IF NOT EXISTS " + seq;
                HibernateUtil.executeStatement(session, seqCommand);
            }

            boolean isPrintingEnabled = true; // false;
            boolean isExecutionEnabled = true;

            SchemaExport schemaExport = new SchemaExport(configuration); // connection);
            schemaExport.create(isPrintingEnabled, isExecutionEnabled);
        } finally {
            session.close();
        }
    }

    protected void createEntities() {
        log.info("Creating entities for the database " + databaseName);
    }

    protected void createFunctions() {
        log.info("Creating functions for the database " + databaseName);
        /*
         * if (!configuration.getSqlFunctions().containsKey("trunc")) { configuration.addSqlFunction("trunc", new
         * StandardSQLFunction("trunc", Hibernate.DATE)); HibernateUtil.executeStatements(sessionFactory,
         * "CREATE ALIAS IF NOT EXISTS TRUNC FOR \"" + H2TruncateFunction.NAME + "\";"); }
         */
    }

}
