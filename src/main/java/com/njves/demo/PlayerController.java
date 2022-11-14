package com.njves.demo;

import com.njves.demo.list.LinkedList;
import com.njves.demo.model.Track;
import com.njves.demo.playlist.Playlist;
import com.njves.demo.playlist.PlaylistStorage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {
    @FXML
    public BorderPane borderPane;
    @FXML
    private ImageView coverImageView;

    @FXML
    private Text musicTitleText;

    @FXML
    private Slider musicLengthSlider;

    @FXML
    private Button prevButton;

    @FXML
    private Button playButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button backButton;

    private Playlist playlist;
    private final AudioExtractor audioExtractor = AudioExtractor.getInstance();
    private final LinkedList<Track> tracks = audioExtractor.getTracks();
    private boolean isPlaying = false;
    private Media media;
    private MediaPlayer mediaPlayer;
    private boolean clickedFlag = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPlaylist();
        nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            playlist.nextTrack();
            update();
        });
        prevButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            playlist.previousTrack();
            update();
        });

        playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if(isPlaying) {
                playButton.setText("Pause");
                mediaPlayer.pause();
            } else {
                playButton.setText("Play");
                mediaPlayer.play();
            }
            isPlaying = !isPlaying;
        });
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            openPlaylistViewScreen();
        });
    }

    private void initPlaylist() {
        getEnvironmentPlaylist();
        media = new Media(new File(playlist.current().getFileLink()).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        if(PlaylistStorage.getInstance().getCurrentPlaylist() == null) {
            playlist.playAll(tracks.get(2).getData());
        }
        update();
    }

    private void getEnvironmentPlaylist() {
        playlist = PlaylistStorage.getInstance().getCurrentPlaylist();
        if(playlist == null) {
            playlist = new Playlist();
            for(Track track : tracks) {
                playlist.append(track);
            }
        }
    }

    private void update() {
        Track track = playlist.current();
        media = new Media(new File(playlist.current().getFileLink()).toURI().toString());
        mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(media);

        Image coverImage = new Image(track.getCoverLink());
        coverImageView.setImage(coverImage);
        musicTitleText.setText(track.getTitle());
        isPlaying = false;
        musicLengthSlider.setValue(0);

        setTimePropertyListener();
        setOnDragDetectedListener();
        setOnSliderClickListener();
        setOnEndOfMediaListener();
        System.out.println(track);
    }

    private void setTimePropertyListener() {
        mediaPlayer.currentTimeProperty().addListener((observableValue, duration, t1) -> {
            if(!clickedFlag)
                musicLengthSlider.setValue(t1.toMillis() / mediaPlayer.getTotalDuration().toMillis() * 100);
        });
    }

    private void setOnDragDetectedListener() {
        musicLengthSlider.setOnDragDetected(e -> {
            clickedFlag = true;
        });
    }

    private void setOnSliderClickListener() {
        musicLengthSlider.setOnMousePressed(e -> {
            clickedFlag = true;
            Duration duration = mediaPlayer.getMedia().getDuration();
            mediaPlayer.seek(duration.multiply(musicLengthSlider.getValue()/100.0));
        });
        musicLengthSlider.setOnMouseReleased(e -> clickedFlag = false);
    }

    private void setOnEndOfMediaListener() {
        mediaPlayer.setOnEndOfMedia(() -> {
            playlist.nextTrack();
            update();
        });
    }

    private void openPlaylistViewScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(MainApplication.VIEW_FILE_PLAYLIST));
            Scene scene = new Scene(root, 800, 600);
            Stage stage = new Stage();

            stage.setTitle("Выбор плейлиста");
            stage.setScene(scene);
            stage.show();
            mediaPlayer.stop();
            borderPane.getScene().getWindow().hide();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
