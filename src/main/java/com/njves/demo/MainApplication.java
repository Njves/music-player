/**
 * Модуль запускающий GUI
 */
package com.njves.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Запускает приложение плеера
 */
public class MainApplication extends Application {
    /**
     * Файл вертки плеера
     */
    public static final String VIEW_FILE_PLAYER = "doca.fxml";

    /**
     * Файл верстки плейлиста
     */
    public static final String VIEW_FILE_PLAYLIST = "plview.fxml";

    /**
     * Файл верстки настройки плейлиста
     */
    public static final String VIEW_FILE_LIBRARY_PLAYLIST = "lbplview.fxml";

    /**
     * Запускает приложение плейлиста
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(VIEW_FILE_PLAYER));
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Плеер");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Точка входа
     */
    public static void main(String[] args) {
        launch();
    }
}