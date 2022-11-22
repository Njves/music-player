package com.njves.demo;

import com.njves.demo.list.LinkedList;
import com.njves.demo.list.LinkedListNode;
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
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
    private LinkedList<Track> libraryList = AudioExtractor.getInstance().getTracks();
    @FXML
    public Button buttonDownload;

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

        buttonDownload.setOnMouseClicked(event -> {
            addFileToLibrary();
        });
    }

    private void initializePlaylistListView() {
        listViewPlaylist.getItems().clear();
        for (Track track : playlist) {
            listViewPlaylist.getItems().add(track);
        }
    }

    private void initializeLibraryListView() {
        libraryList = AudioExtractor.getInstance().getTracks();
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
        listViewPlaylist.getItems().clear();
        listViewLibrary.getItems().clear();
        initializeLibraryListView();
        initializePlaylistListView();
    }

    private void removeTrackFromPlaylist() {

        Track[] deletedTracks = new Track[listViewPlaylist.getSelectionModel().getSelectedItems().size()];
        int counter = 0;
        for (Track track : listViewPlaylist.getSelectionModel().getSelectedItems()) {
            deletedTracks[counter] = track;
            playlist.remove(track);
            counter++;
        }
        for(Track track : deletedTracks) {
            listViewPlaylist.getItems().remove(track);
        }
        listViewPlaylist.getItems().clear();
        listViewLibrary.getItems().clear();
        initializeLibraryListView();
        initializePlaylistListView();
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
        if(listViewPlaylist.getItems().size() == 0 || listViewPlaylist.getSelectionModel().getSelectedItems().size() == 0) {
            return;
        }

        int match = listViewPlaylist.getItems().indexOf(listViewPlaylist.getSelectionModel().getSelectedItems().get(0));
        LinkedListNode<Track> track = playlist.get(listViewPlaylist.getItems().get(match));
        if(match == 0) {
            match = listViewPlaylist.getItems().size();
        }
        playlist.swap(track,
                playlist.get(listViewPlaylist.getItems().get(match-1)));

        initializePlaylistListView();

    }

    private void moveToDown() {
        if(listViewPlaylist.getItems().size() == 0 || listViewPlaylist.getSelectionModel().getSelectedItems().size() == 0) {
            return;
        }
        int match = listViewPlaylist.getItems().indexOf(listViewPlaylist.getSelectionModel().getSelectedItems().get(0));
        LinkedListNode<Track> track = playlist.get(listViewPlaylist.getItems().get(match));
        if(match == listViewPlaylist.getItems().size() - 1) {
            match = -1;
        }
        playlist.swap(track,
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

    private void addFileToLibrary() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Загрузить музыку");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("mp3", "*.mp3"),
                new FileChooser.ExtensionFilter("waw", "*.waw"));
        File file = chooser.showOpenDialog(parent.getScene().getWindow());
        if(file == null) {
            return;
        }
        try {
            System.out.println(file.getName());
            Files.copy(file.toPath(),
                    new File(AudioExtractor.getInstance().LIBRARY_PATH, file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listViewLibrary.getItems().clear();
        initializeLibraryListView();

    }

    private void hide() {
        parent.getScene().getWindow().hide();
    }
}
