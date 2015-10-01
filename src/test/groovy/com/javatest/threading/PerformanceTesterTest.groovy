package com.javatest.threading

import com.javatest.threading.impl.PerformanceTesterImpl
import spock.lang.Specification

class PerformanceTesterTest extends Specification {

    def "should measure execution time statistics"() {
        given:
        def threadPoolSize = 2
        def executionCount = 10
        def warmupInvocations = 1500
        def testingImplementation = Mock(Runnable)

        def performanceTester = new PerformanceTesterImpl(warmupInvocations)


        def measuredInvocations = executionCount * threadPoolSize * 2
        def expectedMinTime = measuredInvocations * 5
        def expectedMaxTime = measuredInvocations * 10

        when:
        def result = performanceTester.runPerformanceTest(testingImplementation, executionCount, threadPoolSize)
        print("result is $result")

        then:
        1520 * testingImplementation.run() >> { Math.random() > 0.5 ? Thread.sleep(5) : Thread.sleep(10) }

        then:
        result.totalTime > expectedMinTime
        result.totalTime < expectedMaxTime
        result.minTime == 5
        result.maxTime > 10

    }
}
