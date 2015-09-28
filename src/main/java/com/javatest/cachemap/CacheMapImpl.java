package com.javatest.cachemap;

import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.function.Supplier;

public class CacheMapImpl<KeyType, ValueType> implements CacheMap<KeyType, ValueType> {
    private long timeToLive;
    private WeakHashMap<KeyType, TimedValue> map = new WeakHashMap<>();
    private TreeMap<Long, KeyType> actualKeys = new TreeMap<>();

    private final Supplier<Long> timestampSupplier;

    public CacheMapImpl(Supplier<Long> timestampSupplier) {
        this.timestampSupplier = timestampSupplier;
    }

    @Override
    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    @Override
    public long getTimeToLive() {
        return timeToLive;
    }

    @Override
    public ValueType put(KeyType key, ValueType value) {
        TimedValue oldValue = map.put(key, new TimedValue(value));
        return oldValue == null || isExpired(oldValue) ? null : oldValue.value;
    }

    @Override
    public void clearExpired() {
        actualKeys.headMap(timestampSupplier.get() - getTimeToLive()).clear();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key) && isNotExpired(map.get(key));
    }

    @Override
    public boolean containsValue(Object value) {
        return map
                .entrySet()
                .stream()
                .filter(e -> value.equals(e.getValue().value) && isNotExpired(e.getValue()))
                .findFirst()
                .isPresent();
    }

    @Override
    public ValueType get(Object key) {
        TimedValue value = map.get(key);
        return value == null || isExpired(value) ? null : value.value;
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty() || haveNoActualKeys(map);
    }

    private boolean haveNoActualKeys(WeakHashMap<KeyType, TimedValue> map) {
        return !map.entrySet().stream().anyMatch(e -> !isOutdated(e.getValue().createdIn));
    }

    @Override
    public ValueType remove(Object key) {
        TimedValue oldValue = map.remove(key);
        if (oldValue != null) {
            actualKeys.remove(oldValue.createdIn);
            if (isNotExpired(oldValue)) return oldValue.value;
        }
        return null;
    }

    @Override
    public int size() {
        return map.size() - numberOfExpired(map);
    }

    private int numberOfExpired(WeakHashMap<KeyType, TimedValue> map) {
        return (int) map.entrySet().stream().filter(e -> isExpired(e.getValue())).count();
    }

    private boolean isExpired(TimedValue value) {
        return isOutdated(value.createdIn);
    }

    private boolean isOutdated(Long timestamp) {
        return timestampSupplier.get() - timestamp > getTimeToLive();
    }

    private boolean isNotExpired(TimedValue value) {
        return !isExpired(value);
    }

    private class TimedValue {
        final ValueType value;
        final long createdIn;

        private TimedValue(ValueType value) {
            this.value = value;
            createdIn = timestampSupplier.get();
        }
    }
}
