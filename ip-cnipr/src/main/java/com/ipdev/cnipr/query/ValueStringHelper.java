package com.ipdev.cnipr.query;

import java.text.SimpleDateFormat;

import com.ipdev.common.datatype.DateRange;

public class ValueStringHelper {
    static final String datePattern = "yyyyMMdd";
    static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(datePattern);

    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof DateRange) {
            return dateRangeToString((DateRange) obj);
        }
        // TODO: other types need to be handled specially

        return obj.toString();
    }

    static String dateRangeToString(DateRange dateRange) {
        StringBuilder sb = new StringBuilder()
            .append(DATE_FORMAT.format(dateRange.getStartDate()));
        if (dateRange.getEndDate() != null) {
            sb.append(" to ").append(DATE_FORMAT.format(dateRange.getEndDate()));
        }
        return sb.toString();
    }
}
