package com.javatest.threading;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class PerformanceTesterImpl implements PerformanceTester {
    private final int warmupIterations;

    public PerformanceTesterImpl(int warmupIterations) {
        this.warmupIterations = warmupIterations;
    }

    @Override
    public synchronized PerformanceTestResult runPerformanceTest(Runnable task, int executionCount, int threadPoolSize) throws InterruptedException {
        if (warmupIterations > 0) {
            IntStream.range(0, warmupIterations).forEach(i -> task.run());
        }

        ForkJoinPool forkJoinPool = new ForkJoinPool(threadPoolSize);
        PerformanceTestWorker worker = new PerformanceTestWorker(executionCount, threadPoolSize, task);
        forkJoinPool.invoke(worker); //doing this for first time to compile all code for F\J
        return forkJoinPool.invoke(worker);
    }
}
