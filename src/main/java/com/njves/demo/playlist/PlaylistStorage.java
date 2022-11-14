package com.njves.demo.playlist;

import com.njves.demo.list.LinkedList;

public class PlaylistStorage {

    private static PlaylistStorage instance;

    private Playlist currentPlaylist;
    private LinkedList<Playlist> playlistLinkedList;

    private PlaylistStorage() {
        playlistLinkedList = new LinkedList<>();
    }

    public static PlaylistStorage getInstance() {
        if(instance == null) {
            instance = new PlaylistStorage();
        }
        return instance;
    }

    public LinkedList<Playlist> getList() {
        return playlistLinkedList;
    }

    public void setList(LinkedList<Playlist> list) {
        this.playlistLinkedList = list;
    }

    public void addPlaylist(Playlist playlist) {
        playlistLinkedList.append(playlist);
    }

    public void removePlaylist(Playlist playlist) {
        playlistLinkedList.remove(playlist);
    }

    public void setCurrentPlaylist(Playlist playlist) {
        if(playlist.get(0) != null) {
            playlist.playAll(playlist.get(0).getData());
        }
        this.currentPlaylist = playlist;
    }

    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

}
