package com.njves.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    public static final String VIEW_FILE_PLAYER = "doca.fxml";
    public static final String VIEW_FILE_PLAYLIST = "plview.fxml";
    public static final String VIEW_FILE_LIBRARY_PLAYLIST = "lbplview.fxml";

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(VIEW_FILE_PLAYER));
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}