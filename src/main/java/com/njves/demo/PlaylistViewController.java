package com.njves.demo;

import com.njves.demo.playlist.Playlist;
import com.njves.demo.playlist.PlaylistStorage;
import javafx.fxml.FXML;
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
    @FXML
    public Button buttonPlayer;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonRemove;
    @FXML
    private Button buttonEdit;
    @FXML
    private ListView<Playlist> listView;
    @FXML
    private HBox hBox;

    private final PlaylistStorage storage = PlaylistStorage.getInstance();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializePlaylist();
        buttonAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> addPlaylist());

        buttonRemove.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> removePlaylist());

        buttonEdit.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> openWindowLibrary());

        buttonPlayer.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            openWindowPlayer();
        });
    }

    private void initializePlaylist() {
        System.out.println(storage.getList());
        for (Playlist playlist : storage.getList()) {
            listView.getItems().add(playlist);
        }
    }

    private void addPlaylist() {
        Playlist playlist = new Playlist();
        storage.addPlaylist(playlist);
        listView.getItems().add(playlist);
    }

    private void removePlaylist() {
        Playlist[] playlists = new Playlist[listView.getSelectionModel().getSelectedItems().size()];
        int counter = 0;
        for (Playlist playlist : listView.getSelectionModel().getSelectedItems()) {
            storage.removePlaylist(playlist);
            playlists[counter] = playlist;
            counter++;
        }

        for (int i = 0; i < playlists.length; i++) {
            listView.getItems().remove(playlists[i]);
        }
    }

    private void openWindowLibrary() {
        if(listView.getSelectionModel().getSelectedItems().size() == 0) {
            return;
        }
        Playlist selectedPlaylist = listView.getSelectionModel().getSelectedItems().get(0);
        storage.setCurrentPlaylist(selectedPlaylist);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(MainApplication.VIEW_FILE_LIBRARY_PLAYLIST));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root, 800, 600);
        Stage stage = new Stage();
        stage.setTitle("???????????????? ??????????????????");
        stage.setScene(scene);
        stage.show();
        hide();
    }

    private void openWindowPlayer() {
        if(listView.getSelectionModel().getSelectedItems().size() == 0) {
            return;
        }
        Playlist selectedPlaylist = listView.getSelectionModel().getSelectedItems().get(0);
        if(selectedPlaylist.size() == 0) {
            return;
        }
        storage.setCurrentPlaylist(selectedPlaylist);

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(MainApplication.VIEW_FILE_PLAYER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root, 800, 600);
        Stage stage = new Stage();
        stage.setTitle("??????????");
        stage.setScene(scene);
        stage.show();
        hide();
    }

    private void hide() {
        hBox.getScene().getWindow().hide();
    }
}
