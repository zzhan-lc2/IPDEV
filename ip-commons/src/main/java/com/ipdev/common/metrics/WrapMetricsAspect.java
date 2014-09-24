package com.ipdev.common.metrics;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.measure.unit.SI;

import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class WrapMetricsAspect {
    MetricsHelperManager metricsManager;
    private Map<Method, String> opNames = new ConcurrentHashMap<Method, String>();

    public void setMetricsManager(MetricsHelperManager metricsManager) {
        this.metricsManager = metricsManager;
    }

    /**
     * A point-cut for all methods annotated by @ReportMetrics
     */
    @Pointcut("@annotation(com.ipdev.common.metrics.ReportMetrics)")
    public void initReportMetricsLayer() {
    }

    @Around("initReportMetricsLayer()")
    public Object wrapMethodForProfile(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            return joinPoint.proceed();
        } finally {
            if (null != metricsManager) {
                MethodSignature signature = (MethodSignature) (joinPoint.getSignature());
                Method method = signature.getMethod();
                Class<?> clazz = method.getDeclaringClass();

                String opName = opNames.get(method);
                if (opName == null) {
                    String className = clazz.getSimpleName();
                    opName = className + "." + signature.getMethod().getName();
                    opNames.put(method, opName);
                }

                stopWatch.stop();
                metricsManager.reportTimeMetrics(opName, "Time", stopWatch.getStartTime(), stopWatch.getTime(),
                    SI.MILLI(SI.SECOND));
            }
        }

    }
}
