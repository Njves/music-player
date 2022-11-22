/**
 * Модуль содеражщий класс узла
 */
package com.njves.demo.list;

import java.util.Objects;

/**
 * Представляет узел связанного списка
 * @param <T>
 */
public class LinkedListNode<T> {

    /**
     * Следующий элемент узла
     */
    private LinkedListNode<T> nextItem;

    /**
     * Предыдущий элемент списка
     */
    private LinkedListNode<T> previousItem;

    /**
     * Данные хранящий узел
     */
    private T data;

    /**
     * Приватный конструктор
     */
    private LinkedListNode() {

    }
    /**
     * Конструктор узла
     * @param data данные хранящий узел
     */
    public LinkedListNode(T data) {
        this.data = data;
    }

    /**
     * Возвращает следующий элемент узла
     * @return следующий элемент узла
     */
    public LinkedListNode<T> getNextItem() {
        return nextItem;
    }

    /**
     * Устанавливает следующий элемент узла
     * @param nextItem следующий элемент
     */
    public void setNextItem(LinkedListNode<T> nextItem) {
        this.nextItem = nextItem;
    }

    /**
     * Возвращает предыдущий элемент
     * @return предыдущий элемент
     */
    public LinkedListNode<T> getPreviousItem() {
        return previousItem;
    }

    /**
     * Устанавливает предыдущий элемент
     * @param previousItem предыдущий элемент
     */
    public void setPreviousItem(LinkedListNode<T> previousItem) {
        this.previousItem = previousItem;
    }

    /**
     * Возвращает данные узла
     * @return данные узла
     */
    public T getData() {
        return data;
    }

    /**
     * Сравнивает два объекта между собой
     * @param o сравниваемый объект
     * @return равны ли между собой объекты
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedListNode<?> that = (LinkedListNode<?>) o;
        return Objects.equals(data, that.data);
    }

    /**
     * Возвращает хэш код объекта
     * @return хэш код объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(nextItem, previousItem, data);
    }

    /**
     * Возвращает строковое представление объекта
     * @return строковое представление объекта
     */
    @Override
    public String toString() {
        return "LinkedListNode{" +
                "data=" + data +
                '}';
    }
}
