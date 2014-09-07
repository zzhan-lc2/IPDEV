package com.ipdev.apps;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.ipdev.architect.AppConfig;

public abstract class IpAppBase {
    static {
        AppConfigLog4jConfigurator.configureForBootstrap();
    }

    protected Logger LOG = LoggerFactory.getLogger(this.getClass());

    private static final String DEFAULT_ROOT = "/local";

    public static final String IP_APP_NAME = "IpTool";
    static final String IP_APP_GROUP = "IPDEV";

    static String transactionManagerBeanName = "transactionManager";
    static ApplicationContext applicationContext = null;

    static String[] applicationContextResources = {
        "ipdev-apps-common.spring.xml"
    };

    public static void setApplicationContextResources(String[] applicationResources) {
        applicationContextResources = applicationResources;
    }

    public static void configure(String[] args, String appName, String[] applicationResources) {
        initializeAppConfig(args, IP_APP_GROUP, appName);
        setApplicationContextResources(applicationResources);
    }

    public static void configure(String[] args, String appName) {
        initializeAppConfig(args, IP_APP_GROUP, appName);
    }

    public static void configure(String[] args) {
        configure(args, IP_APP_NAME);
    }

    private static void initializeAppConfig(String[] args, String appGroupName, String appName) {
        if (StringUtils.isEmpty(appName)) {
            appName = IP_APP_NAME;
        }
        if (!AppConfig.isInitialized()) {
            AppConfig.initialize(appName, appGroupName, args);
            AppConfigLog4jConfigurator.configureFromAppConfig();
        }
    }

    /**
     * @return the Spring application context.ClassPathXmlApplicationContext(
     */
    protected synchronized static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext(applicationContextResources);
            initializeReadOnlyTransactionlessSession();
        }
        return applicationContext;
    }

    /**
     * Returns the bean of the given class type, provided that there is exactly one instance of that type. If the
     * application context has not yet been initialized, it will be initialized.
     * 
     * @throws RuntimeException
     *             if no bean is found, or if multiple are found.
     */
    public static <T extends IpAppBase> T getApplication(Class<T> type) {
        Map<String, T> beans = getApplicationContext().getBeansOfType(type);
        if (beans == null || beans.isEmpty()) {
            throw new RuntimeException(
                "No bean of type " + type + " was found in " + Arrays.asList(applicationContextResources));
        }
        if (beans.size() > 1) {
            throw new RuntimeException(
                "Ambigious bean definition for " + type + " found in " + Arrays.asList(applicationContextResources) +
                    ": " + beans);
        }
        return Iterables.getFirst(beans.values(), null);
    }

    /**
     * Initializes a default persistence session for the application, which can only be used for transaction-less
     * read-only access.
     * <p>
     * This allows application startup to occur without needing to explicitly open a transaction. However, once data
     * needs to be written a <code>@Transactional</code> block or programmatic transaction must be used.
     * <p>
     * This transaction will be automatically cleaned upon termination of the application.
     */
    private static void initializeReadOnlyTransactionlessSession() {
        PlatformTransactionManager transactionManager = (PlatformTransactionManager) (
            getApplicationContext().getBean(transactionManagerBeanName));

        DefaultTransactionDefinition trxDef = new DefaultTransactionDefinition();
        trxDef.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        trxDef.setReadOnly(true);

        transactionManager.getTransaction(trxDef);
    }

    public static void initForAdhoc(String domain, Map<String, String> additionalArgs) {
        try {
            AppConfigLog4jConfigurator.configureForBootstrap();
        } catch (Exception e) {
            // ignore
        }

        Map<String, String> defaults = Maps.newLinkedHashMap();
        defaults.put("domain", domain);
        if (additionalArgs != null) {
            defaults.putAll(additionalArgs);
        }

        ArgsUtils.putEnvVar("ACTUAL_ENVIRONMENT_ROOT", DEFAULT_ROOT);

        configure(ArgsUtils.addDefaultArguments(new String[] { "--root=" + DEFAULT_ROOT, "" }, defaults));
    }
}
