package com.njves.demo.list;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class LinkedList<T> implements Iterable<T> {

    protected LinkedListNode<T> firstItem;
    protected int length;

    public LinkedList() {

    }

    public LinkedList(LinkedListNode<T> firstItem) {
        appendFirst(firstItem);
    }

    private void appendFirst(LinkedListNode<T> firstNode) {
        this.firstItem = firstNode;
        firstItem.setNextItem(firstItem);
        firstItem.setPreviousItem(firstItem);
        length++;
    }

    private boolean isEmpty() {
        return firstItem == null;
    }

    private void checkIsEmpty() {
        if(isEmpty()) {
            throw new RuntimeException("Список пуст");
        }
    }

    public void appendLeft(T data) {
        LinkedListNode<T> newNode = new LinkedListNode<>(data);
        if(isEmpty()) {
            appendFirst(newNode);
            return;
        }
        newNode.setPreviousItem(firstItem.getPreviousItem());
        newNode.setNextItem(firstItem);
        firstItem.getPreviousItem().setNextItem(newNode);
        firstItem.setPreviousItem(newNode);
        firstItem = newNode;
        length++;
    }

    public void appendRight(T data) {
        LinkedListNode<T> newNode = new LinkedListNode<>(data);
        if(isEmpty()) {
            appendFirst(newNode);
            return;
        }
        LinkedListNode<T> lastNode = last();
        newNode.setPreviousItem(lastNode);
        newNode.setNextItem(firstItem);
        lastNode.setNextItem(newNode);
        firstItem.setPreviousItem(newNode);
        length++;
    }

    public void append(T data) {
        appendRight(data);
    }


    public void insert(LinkedListNode<T> previous, T data) {
        if(!isContains(previous.getData())) {
            throw new RuntimeException(new ValueError("На ноды нет ссылки в списке, или нода пуста"));
        }
        LinkedListNode<T> newNode = new LinkedListNode<>(data);
        newNode.setPreviousItem(previous);
        newNode.setNextItem(previous.getNextItem());
        previous.setNextItem(newNode);
        previous.getNextItem().setPreviousItem(newNode);
        length++;
    }

    public int size() {
        return length;
    }

    public LinkedListNode<T> get(int index) {
        if(index == 0) return firstItem;
        int counter = 0;
        LinkedListNode<T> currentNode = firstItem;
        while (currentNode.getNextItem() != firstItem) {
            currentNode = currentNode.getNextItem();
            counter++;
            if(counter == index) {
                return currentNode;
            }
        }
        throw new NullPointerException("Такой ноды не существует");
    }


    public boolean isContains(T value) {
        for (T data: this) {
            if(data.equals(value)) {
                return true;
            }
        }
        return false;
    }

    private void checkIsContains(T value) {
        if(!isContains(value)) {
            throw new RuntimeException(new ValueError("Такого элемента не существует"));
        }
    }

    private LinkedListNode<T> getNodeByData(T data) {
        for (int i = 0; i < size(); i++) {
            LinkedListNode<T> node = get(i);
            if(node == null) {
                throw new NullPointerException("Нода отстуствует");
            }
            if(node.getData().equals(data)) {
                return node;
            }
        }
        try {
            throw new ValueError("Такого элемента не существует ");
        } catch (ValueError e) {
            throw new RuntimeException(e);
        }
    }

    public Iterator<T> reversed() {
        return new ReversedLinkedListIterator();
    }


    public void remove(T value) {
        checkIsEmpty();
        checkIsContains(value);

        LinkedListNode<T> removedNode = getNodeByData(value);
        LinkedListNode<T> lastNode = last();

        removedNode.getPreviousItem().setNextItem(removedNode.getNextItem());
        removedNode.getNextItem().setPreviousItem(removedNode.getPreviousItem());

        if(removedNode.equals(firstItem)) {
            firstItem = removedNode.getNextItem();
        }
        removedNode = null;
        length--;
    }

    public LinkedListNode<T> last() {
        checkIsEmpty();
        LinkedListNode<T> nextItem = firstItem;
        while(nextItem.getNextItem() != firstItem) {
            nextItem = nextItem.getNextItem();
        }
        return nextItem;
    }

    public T[] toArray() {
        Object[] array = new Object[length];
        LinkedListIterator iterator = new LinkedListIterator();
        int counter = 0;
        while(iterator.hasNext()) {
            T object = iterator.next();
            array[counter] = object;
            counter++;
        }
        return (T[]) array;
    }

    @Override
    public String toString() {
        StringBuilder linkedListString = new StringBuilder();
        LinkedListNode<T> currentNode = firstItem;
        linkedListString.append("{ previous: ").append(currentNode.getPreviousItem()).append(", current: ").append(currentNode).append(" next: ").append(currentNode.getNextItem()).append("\n");

        for (int i = 0; i < length - 1; i++) {
            currentNode = currentNode.getNextItem();
            linkedListString.append("previous: ").append(currentNode.getPreviousItem()).append(", current: ").append(currentNode).append(" next: ").append(currentNode.getNextItem()).append("\n");
        }
        linkedListString.append(" }");
        return linkedListString.toString();
    }

    class LinkedListIterator implements Iterator<T> {
        LinkedListNode<T> lastReturned;
        private LinkedListNode<T> next;
        private int counter = 0;
        public LinkedListIterator() {
            this.next = firstItem;
        }


        @Override
        public boolean hasNext() {
            return counter < length;
        }

        @Override
        public T next() {
            lastReturned = next;
            next = next.getNextItem();
            counter++;
            return lastReturned.getData();
        }

    }

    class ReversedLinkedListIterator implements Iterator<T> {
        LinkedListNode<T> lastReturned;
        private LinkedListNode<T> next;
        private int counter = 0;
        public ReversedLinkedListIterator() {
            this.next = last();
        }


        @Override
        public boolean hasNext() {
            return counter < length;
        }

        @Override
        public T next() {
            lastReturned = next;
            next = next.getPreviousItem();
            counter++;
            return lastReturned.getData();
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedList<?> that = (LinkedList<?>) o;
        return length == that.length && Objects.equals(firstItem, that.firstItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstItem, length);
    }
}
