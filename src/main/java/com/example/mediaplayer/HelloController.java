package com.example.mediaplayer;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class HelloController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MediaView centre;
    @FXML
    private Slider time;
    @FXML
    private Slider volume;
    @FXML
    private Pane lowBar;
    @FXML
    private Label shows;
    @FXML
    private Label showpl;
    @FXML
    private Label showp;


    private String path;
    Media media;
    private MediaPlayer mplayer;

       public void stop(ActionEvent event)
    {
        mplayer.stop();
    }
    public void pause(ActionEvent event)
    {
        mplayer.pause();
    }
    public void play(ActionEvent event)
    {
        mplayer.play();
    }
    public void showPause()
    {
        showp.setVisible(true);
    }
    public void showPlay()
    {
        showpl.setVisible(true);
    }
    public void showStop()

    {
        shows.setVisible(true);
    }


    public void disPause()
    {
        showp.setVisible(false);
    }
    public void disPlay()
    {
        showpl.setVisible(false);
    }
    public void disStop()

    {
        shows.setVisible(false);
    }






    public void choseFile(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        path = file.toURI().toString();

        if (path != null ){
            media = new Media(path);
            mplayer = new MediaPlayer(media);
            centre.setMediaPlayer(mplayer);
            DoubleProperty width = centre.fitWidthProperty();
            width.bind(Bindings.selectDouble(centre.sceneProperty(),"width"));
            DoubleProperty height = centre.fitHeightProperty();
            height.bind(Bindings.selectDouble(centre.sceneProperty(),"height"));



            mplayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {

                    time.setValue(t1.toSeconds());
                }
            });

            time.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mplayer.seek(Duration.seconds(time.getValue()));
                }
            });

            time.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mplayer.seek(Duration.seconds(time.getValue()));
                }
            });

            mplayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    Duration end = media.getDuration();
                    time.setMax(end.toSeconds());

                }
            });
            volume.setValue(mplayer.getVolume() * 100);
            volume.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mplayer.setVolume((volume.getValue()/100));
                }
            });

            mplayer.play();



        }

    }


    @Override
    public void initialize (URL arg0, ResourceBundle arg1){
showp.setVisible(false);
        showpl.setVisible(false);
        shows.setVisible(false);


    }

}
