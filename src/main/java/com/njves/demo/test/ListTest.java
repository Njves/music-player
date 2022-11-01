package com.njves.demo.test;

import com.njves.demo.list.LinkedList;
import com.njves.demo.list.LinkedListNode;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;


public class ListTest extends TestCase {

    private LinkedList<Integer> linkedList;
    private final Integer[][] DATASET = new Integer[][] { {1}, {2, 3}, {6, 4, 2, 9} };
    private final Integer[][] LAST = new Integer[][] {{1}, {3}, {9}};

    public LinkedList<Integer> createList(Integer[] dataset) {
        linkedList = new LinkedList<>();
        for (int data : dataset) {
            linkedList.append(data);
        }
        return linkedList;
    }

    @Test
    public void testLast() {
        for (int i = 0; i < DATASET.length; i++) {
            linkedList = createList(DATASET[i]);
            assertEquals(linkedList.last().getData(), LAST[i][0]);
        }
    }


    @Test
    public void testGet() {

    }

    @Test
    public void testAppend() {

    }

    @Test
    public void testRemove() {

    }

    @Test
    public void testIsContains() {

    }
}
