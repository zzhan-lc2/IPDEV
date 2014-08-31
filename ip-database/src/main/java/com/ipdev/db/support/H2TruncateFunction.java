package com.ipdev.db.support;

import java.sql.Date;
import java.sql.Timestamp;

public class H2TruncateFunction {
    public static final String NAME = H2TruncateFunction.class.getName() + ".trunc";

    public final static Date trunc(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }

}
