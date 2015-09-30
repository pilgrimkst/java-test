package com.javatest.cachemap;

class Node<T> {
    private T elem;
    private Node<T> prev;
    private long expiredAt;

    public Node(T elem, Node<T> prev, long expiredAt) {
        this.setElem(elem);
        this.setPrev(prev);
        this.setExpiredAt(expiredAt);
    }

    @Override
    public String toString() {
        return "Node{" +
                "elem=" + getElem() +
                ", prev=" + getPrev() +
                ", expiredAt=" + getExpiredAt() +
                '}';
    }

    public T getElem() {
        return elem;
    }

    public void setElem(T elem) {
        this.elem = elem;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public long getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(long expiredAt) {
        this.expiredAt = expiredAt;
    }
}
