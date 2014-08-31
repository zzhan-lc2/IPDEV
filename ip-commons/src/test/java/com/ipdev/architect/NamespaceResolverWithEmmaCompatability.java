package com.ipdev.architect;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.DefaultNamespaceHandlerResolver;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ComponentScanBeanDefinitionParser;
import org.springframework.context.config.ContextNamespaceHandler;

public class NamespaceResolverWithEmmaCompatability extends DefaultNamespaceHandlerResolver {
    private static final String SPRING_CONTEXT_SCHEMA_URI = "http://www.springframework.org/schema/context";
    private static final String COMPONENT_SCAN_TAG = "component-scan";

    private final NamespaceHandler customContextHandler;

    public NamespaceResolverWithEmmaCompatability() {
        super();
        customContextHandler = new CustomContextNamespaceHandler();
        customContextHandler.init();
    }

    @Override
    public NamespaceHandler resolve(String namespaceUri) {
        if (namespaceUri.equals(SPRING_CONTEXT_SCHEMA_URI)) {
            return customContextHandler;
        } else {
            return super.resolve(namespaceUri);
        }
    }

    /**
     * Overrides the 'context' namespace to use a custom 'context:component-scan' parser.
     */
    public static class CustomContextNamespaceHandler extends ContextNamespaceHandler implements NamespaceHandler {

        @Override
        public void init() {
            super.init();
            super.registerBeanDefinitionParser(COMPONENT_SCAN_TAG, new CustomComponentScanParser());
        }

    }

    /**
     * Overrides the default 'context:component-scan' parser to use a custom class path scanner.
     */
    public static class CustomComponentScanParser extends ComponentScanBeanDefinitionParser {

        @Override
        protected ClassPathBeanDefinitionScanner createScanner(XmlReaderContext readerContext, boolean useDefaultFilters) {
            return new CustomClassPathScanner(readerContext.getRegistry(), useDefaultFilters);
        }
    }

    /**
     * Overrides the default class path scanner to recognize Emma-instrumented classes as compatible with their
     * definitions from non-instrumented source files.
     */
    public static final class CustomClassPathScanner extends ClassPathBeanDefinitionScanner {

        private static Set<String> PACKAGES_SCANNED = Collections.synchronizedSet(new HashSet<String>());

        public CustomClassPathScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
            super(registry, useDefaultFilters);
        }

        @Override
        protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
            Set<BeanDefinitionHolder> results = super.doScan(basePackages);
            for (String entry : basePackages) {
                PACKAGES_SCANNED.add(entry);
            }
            return results;
        }

        @Override
        protected boolean isCompatible(BeanDefinition newDefinition, BeanDefinition existingDefinition) {
            if (newDefinition.getBeanClassName().equals(existingDefinition.getBeanClassName())) {
                return true;
            } else {
                return super.isCompatible(newDefinition, existingDefinition);
            }
        }

        public static Set<String> getPackageScanHistory() {
            return Collections.unmodifiableSet(PACKAGES_SCANNED);
        }

    }

}
