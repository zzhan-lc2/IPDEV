package com.ipdev.architect;

import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.w3c.dom.Element;

public class LazyInitByDefaultBeanDefinitionDocumentReader extends DefaultBeanDefinitionDocumentReader {

    @Override
    protected BeanDefinitionParserDelegate createDelegate(XmlReaderContext readerContext, Element root,
        BeanDefinitionParserDelegate parentDelegate) {
        root.setAttribute(BeanDefinitionParserDelegate.DEFAULT_LAZY_INIT_ATTRIBUTE, "true");
        return super.createDelegate(readerContext, root, parentDelegate);
    }

}
