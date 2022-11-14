/**
 * Модуль содержащий реализацию кольцевого связанного списка
 */
package com.njves.demo.list;

import java.util.Iterator;
import java.util.Objects;

/**
 * Представляет кольцевой двусвязанный список
 * @param <T>
 */
public class LinkedList<T> implements Iterable<T> {
    /**
     * Первый элемент в списке
     */
    protected LinkedListNode<T> firstItem;

    /**
     * Длина списка
     */
    protected int length;

    /**
     * Конструктор без параметров
     */
    public LinkedList() {

    }

    /**
     * Конструктор с параметром первого элемента
     * добавляет элемент и делает его первым в списке
     * @param firstItem первый элемент списка
     */
    public LinkedList(LinkedListNode<T> firstItem) {
        appendFirst(firstItem);
    }

    /**
     * Добавляет начальный элемент в список
     * @param firstNode нода помещяемая в начало
     */
    private void appendFirst(LinkedListNode<T> firstNode) {
        this.firstItem = firstNode;
        firstItem.setNextItem(firstItem);
        firstItem.setPreviousItem(firstItem);
        length++;
    }

    /**
     * Проверяет, является ли список пустым
     * @return являетяс ли список пустым
     */
    private boolean isEmpty() {
        return firstItem == null;
    }

    /**
     * Провряет, является ли список пустым
     * и если является выкидывает исключение
     */
    private void checkIsEmpty() {
        if(isEmpty()) {
            throw new RuntimeException("Список пуст");
        }
    }

    /**
     * Добавляет элемент в начало списка
     * @param data элемент добавляемый в начало списка
     */
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

    /**
     * Добавляет элемент в конец списка
     * @param data элемент добавляемый в конец списка
     */
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

    /**
     * Алиас для добавления элемента в конец
     * @param data добавляемый объект
     */
    public void append(T data) {
        appendRight(data);
    }

    /**
     * Добавляет элемент после передаваемого элемента списка
     * @param previous элемент после которого будет вставлен элемент
     * @param data добавляемый объект
     */
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

    /**
     * Возвращает длину списка
     * @return длина списка
     */
    public int size() {
        return length;
    }

    /**
     * Возвращает ноду по индексу
     * @param index индекс
     * @return нода
     */
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

    /**
     * Возвращает ноду по объекту
     * @param object объект
     * @return нода
     */
    public LinkedListNode<T> get(T object) {
        if(object == null) {
            throw new NullPointerException("Объект является null");
        }
        for (int i = 0; i < length; i++) {
            LinkedListNode<T> node = get(i);
            if(node.getData().equals(object)) {
                return node;
            }
        }
        throw new RuntimeException(new NullPointerException("Объект не найден"));
    }

    /**
     * Провреяет, есть ли элемент в списке
     * @param value элемент
     * @return есть ли элемент в списке
     */
    public boolean isContains(T value) {
        for (T data: this) {
            if(data.equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверяет есть ли элемент в списке, и если его нет
     * выкидывает исключение
     * @param value объект
     */
    private void checkIsContains(T value) {
        if(!isContains(value)) {
            throw new RuntimeException(new ValueError("Такого элемента не существует"));
        }
    }

    /**
     * Возвращает ноду по объекту
     * @param data объект
     * @return найденная нода
     */
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

    /**
     * Возвращает итератор перевернутого списка
     * @return итератор reversed списка
     */
    public Iterator<T> reversed() {
        return new ReversedLinkedListIterator();
    }


    /**
     * Удаляет элемент из списка по объекту
     * @param value объект
     */
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

    /**
     * Возвращает последний элемент списка
     * @return первый элемент списка
     */
    public LinkedListNode<T> last() {
        checkIsEmpty();
        LinkedListNode<T> nextItem = firstItem;
        while(nextItem.getNextItem() != firstItem) {
            nextItem = nextItem.getNextItem();
        }
        return nextItem;
    }

    /**
     * Меняет две ноды местами
     * @param start первая нода
     * @param end вторая нода
     */
    public void swap(LinkedListNode<T> start, LinkedListNode<T> end) {
        if(start == null || end == null) {
            throw new NullPointerException("Недопустимая ссылка");
        }
        if(start.equals(end)) {
            return;
        }
        // TODO: Если что добавить проверку на принадлежность ссылки

        LinkedListNode<T> temp = start.getNextItem();
        start.setNextItem(end.getNextItem());
        end.setNextItem(temp);

        start.getNextItem().setPreviousItem(start);
        end.getNextItem().setPreviousItem(end);


        temp = start.getPreviousItem();
        start.setPreviousItem(end.getPreviousItem());
        end.setPreviousItem(temp);

        start.getPreviousItem().setNextItem(start);
        end.getPreviousItem().setNextItem(end);
        if(start == firstItem) {
            firstItem = end;
        } else if (end == firstItem) {
            firstItem = start;
        }
    }

    /**
     * Преобразует список к массиву
     * @return массив
     */
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

    /**
     * Возвращает строкове представление элемента
     * @return строка
     */
    @Override
    public String toString() {
        if(firstItem == null) {
            return "{}";
        }
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

    /**
     * Итератор списка
     */
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

    /**
     * Итератор перевернутого списка
     */
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

    /**
     * Возвращает итератор
     * @return итератор
     */
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    /**
     * Сравнивает два объекта и провряет равны ли они
     * @param o сравниваемый объект
     * @return равны ли объекты
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedList<?> that = (LinkedList<?>) o;
        return length == that.length && Objects.equals(firstItem, that.firstItem);
    }

    public LinkedListNode<T> getFirstItem() {
        return firstItem;
    }

    /**
     * Возвращает хэш код объекта
     * @return хэш код
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstItem, length);
    }
}
