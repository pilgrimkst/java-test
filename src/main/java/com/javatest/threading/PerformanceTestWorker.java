package com.javatest.threading;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class PerformanceTestWorker extends RecursiveTask<PerformanceTestResult> {
    private int executionCount;
    private int numberOfThreads;
    private Runnable runnable;

    public PerformanceTestWorker(int executionCount, int numberOfThreads, Runnable runnable) {
        this.executionCount = executionCount;
        this.numberOfThreads = numberOfThreads;
        this.runnable = runnable;
    }

    @Override
    protected PerformanceTestResult compute() {
        List<PerformanceTestJob> jobs = range(0, numberOfThreads)
                .mapToObj(i -> new PerformanceTestJob(runnable, executionCount))
                .collect(toList());

        jobs.forEach(ForkJoinTask::fork);

        return jobs
                .stream()
                .map(ForkJoinTask::join)
                .collect(reducing(acc(), this::mergeResults));
    }

    private PerformanceTestResult mergeResults(PerformanceTestResult r1, PerformanceTestResult r2) {
        return new PerformanceTestResult(r1.getTotalTime() + r2.getTotalTime(), min(r1.getMinTime(), r2.getMinTime()), max(r1.getMaxTime(), r2.getMaxTime()));
    }

    private PerformanceTestResult acc() {
        return new PerformanceTestResult(0, Long.MAX_VALUE, Long.MIN_VALUE);
    }
}
