package com.njves.demo.list;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

public class ListTest {

    /**
     * Проверяет добавление в начало
     */
    @Test
    public void testAppendLeft() {
        LinkedList<Integer> list = new LinkedList<>();
        list.appendLeft(10);
        list.appendLeft(100);
        list.appendLeft(-10);
        Assert.assertArrayEquals(list.toArray(), new Integer[] { -10, 100, 10 });
    }

    /**
     * Проверяет добавление в конец
     */
    @Test
    public void testAppendRight() {
        LinkedList<Integer> list = new LinkedList<>();
        list.appendRight(34);
        list.appendRight(100);
        list.appendRight(-999);
        list.appendRight(null);
        Assert.assertArrayEquals(list.toArray(), new Integer[] {34, 100, -999, null });
    }

    /**
     * Проверяет является ли первый элемент пустого списка null
     */
    @Test
    public void testFirstItemIsNull() {
        LinkedList<Integer> list = new LinkedList<>();
        Assert.assertNull(list.getFirstItem());
    }

    /**
     * Проверяет явлляется ли первый элемент null если передать первый элемент в конструктор
     */
    @Test
    public void testFirstItemIsNotNullWhenGiveFirstItemInConstructor() {
        LinkedList<Integer> list = new LinkedList<>(new LinkedListNode<>(100));
        Assert.assertNotNull(list.getFirstItem());
    }

    /**
     * Проверяет является ли первый элемент null если добавить список в элемент
     */
    @Test
    public void testFirstItemIsNotNull() {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(100);
        Assert.assertNotNull(list.getFirstItem());
    }

    /**
     * Проверяет является ли последний элемент null, если список пустой
     */
    @Test(expected = RuntimeException.class)
    public void testLastIsNull() {
        LinkedList<Integer> list = new LinkedList<>();
        list.last();
    }

    /**
     * Проверяет ли последний элемент null если добавить в список элемент
     */
    @Test
    public void testLastIsNotNull() {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(100);
        Assert.assertNotNull(list.last());
    }

    /**
     * Проверяет последний элемент списка
     */
    @Test
    public void testLast() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.append(i);
        }
        Assert.assertEquals(list.last().getData(), (Integer) 99);
    }

    /**
     * Проверяет количество элементов в списке
     */
    @Test
    public void testSize() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 10_000; i++) {
            list.append(i);
        }
        Assert.assertEquals(list.size(), 10_000);
    }

    /**
     * Проверяет получение элемента в списке по индексу
     */
    @Test
    public void testGet() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.append(i);
        }
        for (Integer i = 0; i < 100; i++) {
            Assert.assertEquals(list.get((int) i).getData(), i);
        }
    }

    /**
     * Проверяет вставку элемента в список по узлу
     */
    @Test
    public void testInsert() {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(23);
        LinkedListNode<Integer> firstNode = list.get(0);
        list.insert(firstNode, 24);
        Assert.assertArrayEquals(list.toArray(), new Integer[] {23, 24});
        list.insert(firstNode, 1000);
        Assert.assertArrayEquals(list.toArray(), new Integer[] { 23, 1000, 24 });
    }

    /**
     * Проверяет метод изменения позиций узлов в списке
     */
    @Test
    public void testSwap() {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);
        list.append(2);
        list.append(3);
        list.swap(list.get(0), list.last());
        Assert.assertArrayEquals(list.toArray(), new Integer[] {3, 2, 1});

        list = new LinkedList<>();
        list.append(1);
        list.append(2);
        list.append(3);
        list.swap(list.last(), list.last());
        Assert.assertArrayEquals(list.toArray(), new Integer[] {1, 2, 3});

        list = new LinkedList<>();
        list.append(1);
        list.append(2);
        list.append(3);
        list.swap(list.last(), list.get(0));
        Assert.assertArrayEquals(list.toArray(), new Integer[] {3, 2, 1});
    }

    @Test(expected = NullPointerException.class)
    public void testSwapExcepted() {
        LinkedList<Integer> list = new LinkedList<>();
        list.swap(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testGetThrow() {
        LinkedList<Integer> list = new LinkedList<>();
        list.get(null);
    }
    @Test
    public void testGetByObject() {
        LinkedList<Integer> list = new LinkedList<>();
        Integer data = 40;
        list.append(10);
        list.append(data);
        list.append(30);
        Assert.assertEquals(list.get(data).getData(), data);
        Assert.assertEquals(list.get((Integer) 10).getData(), (Integer) 10);
        Assert.assertEquals(list.get((Integer) 30).getData(), (Integer) 30);
    }


    /**
     * Проверяет вставку элемента в узлом не принадлежащим списку
     */
    @Test(expected = RuntimeException.class)
    public void testInsertNotValid() {
        LinkedList<Integer> list = new LinkedList<>();
        LinkedListNode<Integer> anotherNode = new LinkedListNode<>(15);
        list.append(50);
        list.insert(anotherNode, 59);
        list.insert(list.get(0), 51);
        Assert.assertArrayEquals(list.toArray(), new Integer[] {50, 51});
    }

    /**
     * Проверяет вставку элемента с null узлом
     */
    @Test(expected = RuntimeException.class)
    public void testInsertNodeIsNull() {
        LinkedList<Integer> list = new LinkedList<>();
        list.insert(null, 40);
    }

    /**
     * Проверяет удаление элемента из списка
     */
    @Test
    public void testRemove() {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(320);
        list.append(228);
        list.append(1337);
        list.append(420);
        list.remove(228);
        Assert.assertArrayEquals(list.toArray(), new Integer[] {320, 1337, 420});
        list.remove(1337);
        Assert.assertArrayEquals(list.toArray(), new Integer[] {320, 420});
        list.remove(420);
        Assert.assertArrayEquals(list.toArray(), new Integer[] {320});
        list.remove(320);
        Assert.assertArrayEquals(list.toArray(), new Integer[] {});
    }

    /**
     * Проверяет удаление элемента, который не находится в списке
     */
    @Test(expected = RuntimeException.class)
    public void testRemoveWithoutObject() {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(320);
        list.append(288);
        list.append(1337);
        list.remove(420);
    }

    /**
     * Проверяет получение элемента по индексу, с несуществущем значением
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetInvalid() {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(320);
        list.append(228);
        list.get(10);
        list.get(-2);
    }

    /**
     * Проверяет итератор хранящий элементы наоборот
     */
    @Test
    public void testReversed() {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(320);
        list.append(228);
        Iterator<Integer> iterator = list.reversed();
        Integer[] excepted = new Integer[list.size()];
        int counter = 0;
        while(iterator.hasNext()) {
            excepted[counter] = iterator.next();
            counter++;
        }
        Assert.assertArrayEquals(excepted, new Integer[] { 228, 320});
    }

    /**
     * Проверяет сравнение списков
     */
    @Test
    public void testEquals() {
        LinkedList<Integer> excepted = new LinkedList<>();
        LinkedList<Integer> actual = new LinkedList<>();
        Assert.assertTrue(excepted.equals(actual));
        excepted.append(100);
        excepted.append(101);
        actual.append(100);
        actual.append(101);
        Assert.assertTrue(excepted.equals(actual));
    }

    /**
     * Проверяет что разные элементы - разные
     */
    @Test
    public void testNotEquals() {
        LinkedList<Integer> excepted = new LinkedList<>();
        excepted.append(103);
        LinkedList<Integer> actual = new LinkedList<>();
        Assert.assertNotEquals(excepted, actual);
        excepted.append(100);
        excepted.append(101);
        actual.append(100);
        actual.append(101);
        actual.append(102);
        Assert.assertNotEquals(excepted, actual);
    }


}
