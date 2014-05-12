package com.ipdev.common.statistics;

import java.io.Serializable;

import com.ipdev.common.datatype.DateRange;

public class Counter implements Serializable {
    private static final long serialVersionUID = 1L;

    private String countryCode;
    private String state; // state/province name
    private String db;
    private DateRange dateRange;
    private Integer counts;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

}
