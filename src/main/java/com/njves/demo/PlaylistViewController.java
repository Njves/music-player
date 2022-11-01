package com.njves.demo;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistViewController implements Initializable {
    public Button buttonAdd;
    public Button buttonRemove;
    public Button buttonEdit;
    public ListView<Integer> listView;
    public HBox hBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            listView.getItems().add(10);
        });

        buttonRemove.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            listView.getItems().remove((Integer) 10);
        });

        buttonEdit.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("lbplview.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root, 800, 600);
            Stage stage = new Stage();
            stage.setTitle("FXML Welcome");
            stage.setScene(scene);
            stage.show();
            hBox.getScene().getWindow().hide();
        });
    }
}
