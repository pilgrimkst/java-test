package com.javatest.threading.impl;

import com.javatest.threading.PerformanceTestResult;

import java.util.concurrent.Callable;
import java.util.concurrent.RecursiveTask;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.System.nanoTime;

public class PerformanceTestJob extends RecursiveTask<PerformanceTestResult> {
    public static final int NANOS_IN_MILLISECOND = 1_000_000;
    private Runnable runnable;
    private int executionCount;

    public PerformanceTestJob(Runnable runnable, int executionCount) {
        this.runnable = runnable;
        this.executionCount = executionCount;
    }


    private long execute(Runnable runnable) {
        long start = nanoTime();
        runnable.run();
        long end = nanoTime();
        return (end - start) / NANOS_IN_MILLISECOND;
    }

    @Override
    protected PerformanceTestResult compute() {
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        long total = 0;

        for (int i = 0; i < executionCount; i++) {
            long executionTimeInMillis = execute(runnable);
            min = min(min, executionTimeInMillis);
            max = max(max, executionTimeInMillis);
            total += executionTimeInMillis;
        }

        return new PerformanceTestResult(total, min, max);
    }
}
