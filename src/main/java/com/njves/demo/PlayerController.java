package com.njves.demo;

import com.njves.demo.list.LinkedList;
import com.njves.demo.model.Track;
import com.njves.demo.playlist.Playlist;
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
            try {
                Parent root = FXMLLoader.load(getClass().getResource("plview.fxml"));
                Scene scene = new Scene(root, 800, 600);
                Stage stage = new Stage();

                stage.setTitle("FXML Welcome");
                stage.setScene(scene);
                stage.show();
                borderPane.getScene().getWindow().hide();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void initPlaylist() {
        playlist = new Playlist();
        for (Track track : tracks) {
            playlist.append(track);
        }
        playlist.playAll(tracks.get(0).getData());
        media = new Media(new File(playlist.current().getFileLink()).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        playlist.playAll(AudioExtractor.getInstance().getTracks().get(0).getData());
        update();
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
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                musicLengthSlider.setValue(t1.toMillis() / mediaPlayer.getTotalDuration().toMillis() * 100);

            }
        });

        musicLengthSlider.setOnMouseClicked(e -> {

            if(e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                Duration duration = mediaPlayer.getMedia().getDuration();
                mediaPlayer.seek(duration.multiply(musicLengthSlider.getValue()/100.0));

            }
        });
        System.out.println(track);

    }
}
