package com.ipdev.db.support;

import org.hibernate.Hibernate;
import org.hibernate.dialect.HSQLDialect;
import org.hibernate.dialect.function.StandardSQLFunction;

public class H2SQLDialect extends HSQLDialect {
    public H2SQLDialect() {
        super();
        registerFunction("trunc", new StandardSQLFunction("trunc", Hibernate.DATE));
    }
}
