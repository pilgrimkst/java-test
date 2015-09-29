package com.javatest.cachemap;

import java.util.*;
import java.util.stream.IntStream;

public class CacheKeyHolder<T> {
    private List<LinkedList<T>> buckets;
    private long start;
    private long timeUnit;
    private int capacity;

    public CacheKeyHolder(long start, long timeUnit, int capacity) {
        this.start = start;
        this.timeUnit = timeUnit;
        this.capacity = capacity;
        buckets = preallocateList(capacity);
    }

    public void put(long timestamp, T key) {
        clearExpired(timestamp);
        add(timestamp, key);
    }

    private void add(long timestamp, T key) {
        int hash = timeunitsSinceStart(timestamp);
        LinkedList<T> coll = getOrCreateBucket(hash);
        coll.add(key);
    }

    private LinkedList<T> getOrCreateBucket(int hash) {
        LinkedList<T> coll = buckets.get(hash);
        if (coll == null) {
            coll = new LinkedList<>();
            buckets.set(hash, coll);
        }
        return coll;
    }

    private int timeunitsSinceStart(long timestamp) {
        return (int) ((timestamp - start) / timeUnit);
    }

    public void clearExpired(long timestamp) {
        int hash = timeunitsSinceStart(timestamp);

        List<LinkedList<T>> newBuckets = preallocateList(capacity);

        if (hash < buckets.size()) {
            List<LinkedList<T>> c = buckets.subList(hash, buckets.size());
            newBuckets.addAll(c);
            start = getStartTimeForBucket(hash);
        } else {
            start = timestamp;
        }

        buckets = newBuckets;
    }

    private ArrayList<LinkedList<T>> preallocateList(int capacity) {
        ArrayList<LinkedList<T>> linkedLists = new ArrayList<>(capacity);
        IntStream.range(0, capacity).forEach(i -> linkedLists.add(new LinkedList<T>()));
        return linkedLists;
    }

    private long getStartTimeForBucket(int hash) {
        return start + timeUnit * hash;
    }

    protected List<LinkedList<T>> getBuckets() {
        return buckets;
    }
}
