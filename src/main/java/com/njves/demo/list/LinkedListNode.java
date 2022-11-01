package com.njves.demo.list;

import java.util.Objects;

public class LinkedListNode<T> {

    private LinkedListNode<T> nextItem;
    private LinkedListNode<T> previousItem;
    private T data;

    public LinkedListNode(T data) {
        this.data = data;
    }

    public LinkedListNode<T> getNextItem() {
        return nextItem;
    }

    public void setNextItem(LinkedListNode<T> nextItem) {
        this.nextItem = nextItem;
    }

    public LinkedListNode<T> getPreviousItem() {
        return previousItem;
    }

    public void setPreviousItem(LinkedListNode<T> previousItem) {
        this.previousItem = previousItem;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedListNode<?> that = (LinkedListNode<?>) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nextItem, previousItem, data);
    }

    @Override
    public String toString() {
        return "LinkedListNode{" +
                "data=" + data +
                '}';
    }
}
