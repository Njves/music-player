package com.njves.demo;

import com.njves.demo.list.LinkedList;
import com.njves.demo.model.Track;
import com.njves.demo.playlist.Playlist;
import com.njves.demo.playlist.PlaylistStorage;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistLibraryController implements Initializable {

    @FXML
    public HBox parent;
    @FXML
    public ListView<Track> listViewPlaylist;
    @FXML
    public ListView<Track> listViewLibrary;
    @FXML
    public Button buttonMoveInPlaylist;
    @FXML
    public Button buttonMoveFromPlaylist;
    @FXML
    public Button buttonBack;
    @FXML
    public Button buttonToTop;
    @FXML
    public Button buttonToDown;
    @FXML
    public Button buttonRemove;
    private final LinkedList<Track> libraryList = AudioExtractor.getInstance().getTracks();

    private Playlist playlist;

    private final PlaylistStorage storage = PlaylistStorage.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        takePlaylist();
        initializeLibraryListView();
        initializePlaylistListView();
        buttonMoveInPlaylist.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("Click");
            moveTrackFromLibraryToPlaylist();
        });

        buttonMoveFromPlaylist.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            removeTrackFromPlaylist();
        });

        buttonBack.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            openNewWindow();
        });

        buttonRemove.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            removeTrackFromPlaylist();
        });

        buttonToTop.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            moveToTop();
        });

        buttonToDown.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            moveToDown();
        });
    }

    private void initializePlaylistListView() {
        listViewPlaylist.getItems().clear();
        for (Track track : playlist) {
            listViewPlaylist.getItems().add(track);
        }
    }

    private void initializeLibraryListView() {
        for(Track track : libraryList) {
            listViewLibrary.getItems().add(track);
        }
    }

    private void moveTrackFromLibraryToPlaylist() {
        for (Track track : listViewLibrary.getSelectionModel().getSelectedItems()) {
            if(isTrackInList(track)) {
                return;
            }
            listViewPlaylist.getItems().add(track);
            playlist.append(track);
        }
    }

    private void removeTrackFromPlaylist() {
        for (Track track : listViewPlaylist.getSelectionModel().getSelectedItems()) {
            listViewPlaylist.getItems().remove(track);
            playlist.remove(track);
        }
    }

    private boolean isTrackInList(Track track) {
        for (Track trackPlaylist : listViewPlaylist.getItems()) {
            if(track.equals(trackPlaylist)) {
                return true;
            }
        }
        return false;
    }

    public void takePlaylist() {
        if(storage.getCurrentPlaylist() == null) {
            throw new NullPointerException("Неудалось получить playlist");
        }
        playlist = storage.getCurrentPlaylist();
    }

    private void moveToTop() {
        int match = listViewPlaylist.getItems().indexOf(listViewPlaylist.getSelectionModel().getSelectedItems().get(0));

        playlist.swap(playlist.get(listViewPlaylist.getItems().get(match)),
                        playlist.get(listViewPlaylist.getItems().get(match - 1)));

        initializePlaylistListView();

    }

    private void moveToDown() {
        int match = listViewPlaylist.getItems().indexOf(listViewPlaylist.getSelectionModel().getSelectedItems().get(0));
        if(match >= playlist.size()) {
            return;
        }
        playlist.swap(playlist.get(listViewPlaylist.getItems().get(match)),
                playlist.get(listViewPlaylist.getItems().get(match + 1)));
        initializePlaylistListView();
    }

    private void openNewWindow() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(MainApplication.VIEW_FILE_PLAYLIST));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root, 800, 600);
        Stage stage = new Stage();
        stage.setTitle("Настрока плейлиста");
        stage.setScene(scene);
        stage.show();
        hide();
    }

    private void hide() {
        parent.getScene().getWindow().hide();
    }
}
