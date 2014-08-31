package com.ipdev.architect;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.SpringVersion;

import com.google.common.collect.Iterables;

public class SpringContextTestSupport {
    private static final Log LOG = LogFactory.getLog(SpringContextTestSupport.class);

    protected static String[] DEFAULT_SPRING_RESOURCES = {
        "ipdev-common.spring.xml"
    };

    private static final String DEFAULT_APP_NAME = "ipdev-test";
    private static ApplicationContext applicationContext;
    private static final Object initializationLock = new Object();
    private static String[] springResources = DEFAULT_SPRING_RESOURCES;

    public enum Mode {
        EAGER,
        LAZY;
    }

    public static boolean isInitialized() {
        return applicationContext != null;
    }

    /**
     * Initializes AppConfig/Spring using the xbaCommonContext.spring.xml file.
     */
    public static void initialize() {
        initialize(Mode.EAGER);
    }

    /**
     * Initializes AppConfig/Spring using the commonContext.spring.xml file, optionally lazy-instantiating beans.
     */
    public static void initialize(Mode mode) {
        initialize(mode, DEFAULT_APP_NAME, SpringContextTestSupport.DEFAULT_SPRING_RESOURCES);
    }

    /**
     * Initializes AppConfig/Spring using an application-specific configuration files.
     */
    public static void initialize(String appName, String[] appSpringResources) {
        initialize(Mode.EAGER, appName, appSpringResources);
    }

    public static void initialize(Mode mode, String appName, String[] appSpringResources) {
        synchronized (initializationLock) {
            if (applicationContext != null) {
                return;
            }

            LOG.info("Initializing Spring context [springVersion=" + SpringVersion.getVersion()
                + ", mode=" + mode + "]");
            springResources = appSpringResources;

            GenericApplicationContext context = new GenericApplicationContext();
            XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(context);
            if (mode == Mode.LAZY) {
                reader.setDocumentReaderClass(LazyInitByDefaultBeanDefinitionDocumentReader.class);
            }
            reader.setNamespaceHandlerResolver(new NamespaceResolverWithEmmaCompatability());
            reader.loadBeanDefinitions(appSpringResources);
            context.refresh();
            applicationContext = context;
        }
    }

    /**
     * Returns the bean of the given class type, provided that there is exactly one instance of that type. If the
     * application context has not yet been initialized, it will be initialized using bean lazy-loading.
     * 
     * @throws RuntimeException
     *             if no bean is found, or if multiple are found.
     */
    public static <T> T getBean(Class<T> type) {
        // Even when initializing lazily, Hibernate appears to always be loading (not sure why)
        initialize(Mode.LAZY);

        Map<String, T> beans = applicationContext.getBeansOfType(type);
        if (beans == null || beans.isEmpty()) {
            throw new RuntimeException(
                "No bean of type " + type + " was found in " + Arrays.asList(springResources));
        }
        if (beans.size() > 1) {
            throw new RuntimeException(
                "Ambigious bean definition for " + type + " found in " + Arrays.asList(springResources) +
                    ": " + beans);
        }
        return Iterables.get(beans.values(), 0);
    }

    public static <T> T getBean(String beanName, Class<T> requiredType) {
        initialize(Mode.LAZY);

        return applicationContext.getBean(beanName, requiredType);
    }

    /**
     * Returns the application context.
     */
    public static ApplicationContext getApplicationContext() {
        initialize(Mode.EAGER);
        return applicationContext;
    }

    public static void destroy() {
        synchronized (initializationLock) {
            applicationContext = null;
        }
    }

}
