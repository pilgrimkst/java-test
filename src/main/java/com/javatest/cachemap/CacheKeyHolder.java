package com.javatest.cachemap;

import java.util.LinkedList;

/**
 * Holds cache keys for CacheMapImpl. to speedup cleaning expired values keys are separated in groups,
 * and are deleted when whole group is expired. this helps to reduce complexity of throwing outdated values
 * @param <T>
 */
public class CacheKeyHolder<T> {
    private LinkedList<Node<T>> buckets = new LinkedList<>();
    private long timeUnitSize;

    /***
     * Creates CacheKeyHolder with sertain timeUnitSize.
     * @param timeUnitSize - size of time group in milliseconds
     *                     This parameter sets ballance between memory and cpu consumption:
     *                     The bigger is group - you get more memory overhead
     *                     (if timeUnitSize is 1000 then any key that is expired at 1000 won't be deleted until 1000 will pass)
     *                     At this price you get faster cleanup, The whole group that expires at 1000 deletes in one action
     *                     It gives you a constant time cleanup
     */
    public CacheKeyHolder(long timeUnitSize) {
        this.timeUnitSize = timeUnitSize;
    }

    public void put(T key, long currentTimestamp, long expiresAt) {
        add(expiresAt, key);
        clearExpired(currentTimestamp);
    }

    public void clearExpired(long expiresAt) {
        while (buckets.size() > 0 && buckets.peekFirst().getExpiredAt() < expiresAt) {
            unlinkNode(buckets.pollFirst());
        }
    }

    private void unlinkNode(Node<T> tNode) {
        Node<T> n = tNode;
        n.setPrev(null);
    }

    private void add(long expiresAt, T key) {
        long timeUnit = getTimeUnit(expiresAt);
        Node<T> newNode = new Node<>(key, null, expiresAt);
        if (lastBucketExpired(expiresAt)) {
            Node<T> keyNode = new Node<>(null, newNode, timeUnit);
            buckets.add(keyNode);
        } else {
            insertNode(buckets.peekLast(), newNode);
        }
    }

    private boolean lastBucketExpired(long currentTimestamp) {
        return buckets.peekLast() == null || buckets.peekLast().getExpiredAt() < currentTimestamp;
    }

    private void insertNode(Node<T> keyNode, Node<T> newNode) {
        Node<T> prev = keyNode.getPrev();
        keyNode.setPrev(newNode);
        newNode.setPrev(prev);
    }

    private long getTimeUnit(long expiresAt) {
        long totalFullTimeUnits = expiresAt / timeUnitSize;
        return totalFullTimeUnits * timeUnitSize + timeUnitSize;
    }

    protected LinkedList<Node<T>> getBuckets() {
        return buckets;
    }

}
