/**
 * Модуль хранящий класс хранящий объекты плейлистов
 */
package com.njves.demo.playlist;

import com.njves.demo.list.LinkedList;

/**
 * Singleton класс хранящий объекты плейлистов
 */
public class PlaylistStorage {

    /**
     * Объект хранящий плейличты
     */
    private static PlaylistStorage instance;

    /**
     * Текущий плейлист системы
     */
    private Playlist currentPlaylist;

    /**
     * Список хранящий объекты плейлиста
     */
    private LinkedList<Playlist> playlistLinkedList;

    /**
     * Конструктор хранилища
     */
    private PlaylistStorage() {
        playlistLinkedList = new LinkedList<>();
    }

    /**
     * Метод singleton возвращающий объект хранлища
     * @return объект хранилища
     */
    public static PlaylistStorage getInstance() {
        if(instance == null) {
            instance = new PlaylistStorage();
        }
        return instance;
    }

    /**
     * Возвращает список с объектами плейлиста
     * @return список
     */
    public LinkedList<Playlist> getList() {
        return playlistLinkedList;
    }

    /**
     * Устанавливает список
     * @param list список плейлистов
     */
    public void setList(LinkedList<Playlist> list) {
        this.playlistLinkedList = list;
    }

    /**
     * Добавляет плейлист в список
     * @param playlist плейлист
     */
    public void addPlaylist(Playlist playlist) {
        playlistLinkedList.append(playlist);
    }

    /**
     * Удаляет плейлист из списка
     * @param playlist плейлист
     */
    public void removePlaylist(Playlist playlist) {
        playlistLinkedList.remove(playlist);
    }

    /**
     * Устанавливает глобальный список в системе
     * @param playlist плейлист
     */
    public void setCurrentPlaylist(Playlist playlist) {
        if(playlist.get(0) != null) {
            playlist.playAll(playlist.get(0).getData());
        }
        this.currentPlaylist = playlist;
    }

    /**
     * Возвращает текущий плейлист в системе
     * @return текущий плейлист
     */
    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

}
