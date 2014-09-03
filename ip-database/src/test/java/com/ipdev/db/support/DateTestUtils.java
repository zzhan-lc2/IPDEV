package com.ipdev.db.support;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public final class DateTestUtils {
    static final String DATE_PATTERN = "yyyy-MM-dd";

    public static Date parseDate(String date_yyyy_MM_dd) {
        try {
            return DateUtils.parseDate(date_yyyy_MM_dd, DATE_PATTERN);
        } catch (ParseException e) {
            return null;
        }
    }

}
