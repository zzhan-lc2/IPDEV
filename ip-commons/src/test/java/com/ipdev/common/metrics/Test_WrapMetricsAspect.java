package com.ipdev.common.metrics;

import org.aspectj.lang.ProceedingJoinPoint;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "unit" })
public class Test_WrapMetricsAspect {
    public static class DummyClass {
        @ReportMetrics
        public int dummyMethod() {
            return 0;
        }
    }

    public void test_wrapMethodForProfile() throws Throwable {
        DummyClass obj = new DummyClass();
        int expected = obj.dummyMethod();

        // Create mocks
        ProceedingJoinPoint joinPoint = Mockito.mock(ProceedingJoinPoint.class);

        Object target = this;

        WrapMetricsAspect aspect = new WrapMetricsAspect();

        // Scenario
        Mockito.when(joinPoint.getTarget()).thenReturn(target);
        Mockito.when(joinPoint.proceed()).thenReturn(obj.dummyMethod());

        int actual = (Integer) aspect.wrapMethodForProfile(joinPoint);

        // Verify
        Assert.assertEquals(expected, actual);
    }

}
