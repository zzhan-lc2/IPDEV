package com.ipdev.common.metrics;

import java.util.HashMap;
import java.util.Map;

import javax.measure.unit.Unit;

public class DummyMetricsHelperTestManager implements MetricsHelperManager {
    private Map<String, Integer> counters = new HashMap<String, Integer>();

    @Override
    public void reportTimeMetrics(String operation, String metricFieldName, long startTime, double value, Unit<?> unit) {
        reportTimeMetrics("dummy", operation, metricFieldName, startTime, value, unit);
    }

    @Override
    public void reportTimeMetrics(String program, String operation, String metricFieldName, long startTime,
        double value, Unit<?> unit) {
        String signature = operation + ":" + metricFieldName;
        Integer count = counters.get(signature);
        if (null == count) {
            count = Integer.valueOf(1);
        } else {
            count = count + 1;
        }
        counters.put(signature, count);

    }

    @Override
    public <T> T reportTimeMetrics(String operationName, MetricsCallback<T> cb) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Integer getCount(String operation, String name) {
        String signature = operation + ":" + name;
        return counters.get(signature);
    }

}
