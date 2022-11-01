package com.njves.demo.playlist;

import com.njves.demo.list.LinkedList;
import com.njves.demo.list.LinkedListNode;
import com.njves.demo.model.Track;

public class Playlist extends LinkedList<Track> {
    private LinkedListNode<Track> current;

    public Playlist() {

    }

    public void playAll(Track item) {
        LinkedListNode<Track> currentNode = firstItem;

        while(currentNode.getNextItem() != firstItem) {
            if(currentNode.getData().equals(item)) {
                current = currentNode;
                return;
            }
            currentNode = current.getNextItem();
        }
    }

    public Track nextTrack() {
        current = current.getNextItem();
        return current.getData();
    }

    public Track previousTrack() {
        current = current.getPreviousItem();
        return current.getData();
    }

    public Track current() {
        return current.getData();
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "current=" + current +
                '}';
    }
}
