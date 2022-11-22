/**
 * Модуль содеражщий класс ошибки значения
 */
package com.njves.demo.list;

/**
 * Исключение значения
 */
public class ValueError extends Throwable {
    /**
     * Конструктор исключения
     * @param message сообщение исключегия
     */
    public ValueError(String message) {
        super(message);
    }
}
