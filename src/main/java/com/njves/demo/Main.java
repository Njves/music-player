package com.njves.demo;

import com.njves.demo.list.LinkedList;
import com.njves.demo.model.Track;
import com.njves.demo.playlist.PlaylistFileContainer;
import com.njves.demo.playlist.PlaylistFileParser;


public class Main {
    public static void main(String[] args) {

        LinkedList<Track> tracks = AudioExtractor.getInstance().getTracks();

        new PlaylistFileParser().toJson(new PlaylistFileContainer(tracks));


    }
}
