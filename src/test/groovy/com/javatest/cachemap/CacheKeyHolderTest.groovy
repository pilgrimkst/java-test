package com.javatest.cachemap

import spock.lang.Specification

class CacheKeyHolderTest extends Specification {
    static def long TIME_UNIT_GROUP = 10;

    def "stores all values in groups"() {
        given:
        def holder = new CacheKeyHolder<String>(TIME_UNIT_GROUP)
        def setOfKeysWithTimestamps = [
                [key: "A", expiresAt: 8l, current: 4],
                [key: "B", expiresAt: 9l, current: 5],
                [key: "D", expiresAt: 10l, current: 6],
                [key: "C", expiresAt: 11l, current: 7]
        ]
        when:
        setOfKeysWithTimestamps.each { k -> holder.put(k.key, k.current, k.expiresAt) }

        then:
        holder.buckets.toString() == "[" +
                "Node{elem=null, prev=Node{elem=D, prev=Node{elem=B, prev=Node{elem=A, prev=null, expiredAt=8}, expiredAt=9}, expiredAt=10}, expiredAt=10}, " +
                "Node{elem=null, prev=Node{elem=C, prev=null, expiredAt=11}, expiredAt=20}]"
    }

    def "should clear groups if they are outdated on each add"() {
        given:
        def holder = new CacheKeyHolder<String>(TIME_UNIT_GROUP)
        def setOfKeysWithTimestamps = [
                [key: "A", expiresAt: 8, current: 5],
                [key: "B", expiresAt: 9, current: 5],
                [key: "C", expiresAt: 15, current: 11]
        ]
        when:
        setOfKeysWithTimestamps.each { k -> holder.put(k.key, k.current, k.expiresAt) }

        then:
        holder.buckets.toString() == "[" +
                "Node{elem=null, prev=Node{elem=C, prev=null, expiredAt=15}, expiredAt=20}]"
    }
}
