package com.njves.demo;

import com.njves.demo.list.LinkedList;
import com.njves.demo.list.LinkedListNode;


public class Main {
    public static void main(String[] args) {

        LinkedListNode<Integer> node = new LinkedListNode<>(10);
        LinkedList<Integer> list = new LinkedList<>();
        list.append(10);
        list.insert(list.get(0), 11);
        System.out.println(list);

    }
}
