/**
 * Модуль содержащий класс плейлиста
 */
package com.njves.demo.playlist;

import com.njves.demo.list.LinkedList;
import com.njves.demo.list.LinkedListNode;
import com.njves.demo.model.Track;

/**
 * Представляет дочерний объект плейлиста основаный на кольцеом двусвзяанным списке
 */
public class Playlist extends LinkedList<Track> {
    /**
     * Указатель на текущий элемент
     */
    private LinkedListNode<Track> current;

    /**
     * Начинает проигрывать песню начиная с композции
     * @param item композиция с которой начнется проигрывание
     */
    public void playAll(Track item) {
        if(item == null) {
            current = firstItem;
        }
        LinkedListNode<Track> currentNode = firstItem;

        while(!currentNode.getNextItem().equals(firstItem)) {
            if(currentNode.getData().equals(item)) {
                current = currentNode;
                return;
            }
            currentNode = currentNode.getNextItem();
        }
    }

    /**
     * Возвращает текущий элемент плейлиста
     * @return текущий элемент
     */
    public LinkedListNode<Track> getCurrent() {
        return current;
    }

    /**
     * Получает следующий элемент узла и устанавливает указатель на следующей элемент
     * @return следующая композиция
     */
    public Track nextTrack() {
        current = current.getNextItem();
        return current.getData();
    }

    /**
     * Получает предыдущий узел плейлиста и устанавливает указатель на предыдущий элемент
     * @return композиция предыдущего узла
     */
    public Track previousTrack() {
        current = current.getPreviousItem();
        return current.getData();
    }

    /**
     * Возвращает текущую композицию, на которую установлен указатель
     * @return текущая композиция
     */
    public Track current() {
        return current.getData();
    }

    /**
     * Добавляет композицию в плейлист
     * @param data добавляемый объект
     */
    @Override
    public void append(Track data) {
        super.append(data);
        if(current == null) {
            current = firstItem;
        }
    }

    /**
     * Строковое представление объекта
     * @return строкове представление объекта
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            Track track = get(i).getData();
            builder.append(track.getArtist()).append(" ").append(track.getTitle()).append("\n");
        }
        if(length == 0) {
            builder.append("Пусто");
        }
        return builder.toString();
    }
}
