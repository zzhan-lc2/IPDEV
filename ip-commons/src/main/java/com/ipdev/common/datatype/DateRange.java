package com.ipdev.common.datatype;

import java.io.Serializable;
import java.util.Date;

public class DateRange implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date startDate;
    private boolean startDateInclusive = true;
    private Date endDate;
    private boolean endDateInclusive = true;

    public Date getStartDate() {
        return startDate;
    }

    public DateRange setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public boolean isStartDateInclusive() {
        return startDateInclusive;
    }

    public DateRange setStartDateInclusive(boolean startDateInclusive) {
        this.startDateInclusive = startDateInclusive;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public DateRange setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public boolean isEndDateInclusive() {
        return endDateInclusive;
    }

    public DateRange setEndDateInclusive(boolean endDateInclusive) {
        this.endDateInclusive = endDateInclusive;
        return this;
    }

}
