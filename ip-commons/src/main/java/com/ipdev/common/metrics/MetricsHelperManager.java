package com.ipdev.common.metrics;

import javax.measure.unit.Unit;

public interface MetricsHelperManager {
    public static interface MetricsCallback<T> {
        T execute() throws Exception;
    }

    /**
     * This is the same as reportTimeMetrics(String program, String operation, String metricFieldName, double value,
     * Unit unit). The program name is defined by the system.
     * 
     * @param operation
     *            the operation name (normally it's the class name)
     * @param metricFieldName
     *            the specific metric field (normally it's the method name)
     * @param startTime
     *            the start time
     * @param value
     *            the metrics value
     * @param unit
     *            the unit type for the value
     */
    void reportTimeMetrics(String operation, String metricFieldName, long startTime, double value,
        Unit<?> unit);

    /**
     * Report time-based metrics for a specific program name/operation/metric field name.
     * 
     * @param program
     *            the program/application name
     * @param operation
     *            the operation name (normally it's the class name)
     * @param metricFieldName
     *            the specific metric field (normally it's the method name)
     * @param startTime
     *            the start time
     * @param value
     *            the metrics value
     * @param unit
     *            the unit type for the value
     */
    void reportTimeMetrics(String program, String operation, String metricFieldName, long startTime,
        double value, Unit<?> unit);

    /**
     * Report time metrics.
     * 
     * @param <T>
     *            the generic type
     * @param operationName
     *            the operation name (normally it's the class name)
     * @param cb
     *            the callback method
     * @return the result of callback method execution
     */
    <T> T reportTimeMetrics(String operationName, MetricsCallback<T> cb) throws Exception;
}
