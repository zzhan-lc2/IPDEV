package com.ipdev.db.support;

import org.hibernate.dialect.HSQLDialect;

public class H2SQLDialect extends HSQLDialect {
    public H2SQLDialect() {
        super();
        // registerFunction("trunc", new StandardSQLFunction("trunc", org.hibernate.type.DateType));
    }
}
