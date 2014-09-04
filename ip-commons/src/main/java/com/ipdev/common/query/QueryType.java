package com.ipdev.common.query;

import java.util.Map;

import com.google.common.collect.Maps;

public enum QueryType {
    SYNC_PATENTS(10, "我的专利同步"),
    SYNC_MONITORING(20, "预警同步"),
    SYNC_REPORTS(30, "报表相关同步");

    // ////////////////////
    private final String description;
    private final Integer value;

    private QueryType(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    static Map<Integer, QueryType> cache_ = Maps.newHashMap();

    public static QueryType getByValue(Integer value) {
        if (cache_ == null) {
            cache_ = Maps.newHashMap();
            for (QueryType type : QueryType.values()) {
                cache_.put(type.getValue(), type);
            }
        }
        return cache_.get(value);
    }
}
