package com.ipdev.db.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark a field in the Hibernate DAO test case by this annotation to get it initialized by class declared by value.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CreateDao {
    public Class<? extends AbstractHibernateDao<?>> value();
}
