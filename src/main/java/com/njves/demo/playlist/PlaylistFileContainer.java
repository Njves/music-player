package com.njves.demo.playlist;

import com.njves.demo.list.LinkedList;
import com.njves.demo.model.Track;

import java.util.List;

public class PlaylistFileContainer {
    LinkedList<Track> trackList;

    public PlaylistFileContainer(LinkedList<Track> trackList) {
        this.trackList = trackList;
    }
}
