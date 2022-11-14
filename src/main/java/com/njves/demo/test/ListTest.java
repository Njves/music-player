package com.njves.demo.test;

import com.njves.demo.list.LinkedList;
import com.njves.demo.list.LinkedListNode;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;


public class ListTest {

    @Test
    public void testAppendLeft() {
        LinkedList<Integer> list = new LinkedList<>();
        list.appendLeft(10);
        list.appendLeft(100);
        list.appendLeft(-10);
        Assert.assertArrayEquals(list.toArray(), new Integer[] { -10, 100, 10 });
    }

    @Test
    public void testAppendRight() {
        LinkedList<Integer> list = new LinkedList<>();
        list.appendRight(34);
        list.appendRight(100);
        list.appendRight(-999);
        list.appendRight(null);
        Assert.assertArrayEquals(list.toArray(), new Integer[] {34, 100, -999, null });
    }

    @Test
    public void testFirstItemIsNull() {
        LinkedList<Integer> list = new LinkedList<>();
        Assert.assertNull(list.getFirstItem());
    }

    @Test
    public void testFirstItemIsNotNullWhenGiveFirstItemInConstructor() {
        LinkedList<Integer> list = new LinkedList<>(new LinkedListNode<>(100));
        Assert.assertNotNull(list.getFirstItem());
    }

    @Test
    public void testFirstItemIsNotNull() {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(100);
        Assert.assertNotNull(list.getFirstItem());
    }

    @Test(expected = RuntimeException.class)
    public void testLastIsNull() {
        LinkedList<Integer> list = new LinkedList<>();
        list.last();
    }

    @Test
    public void testLastIsNotNull() {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(100);
        Assert.assertNotNull(list.last());
    }

    public void testLast() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.append(i);
        }
        Assert.assertEquals(list.last().getData(), (Integer) 99);
    }

    @Test
    public void testSize() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 10_000; i++) {
            list.append(i);
        }
        Assert.assertEquals(list.size(), 10_000);
    }

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
}
