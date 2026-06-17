package horseracer.GameLogic;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

/**
 * Music class to play music
 *
 * @version 1.0.0
 * @author Tianrui Xu
 */
public class Music{
    /** Media file*/
    private Media media;
    /** The media player*/
    private MediaPlayer player;

    /**
     * Instantiates a new Music
     *
     * @param path the file path
     */
    public Music(String path){
        try{
            this.media = new Media(
                Objects.requireNonNull(getClass().getResource(path)).toString()
            );
            this.player = new MediaPlayer(media);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Play or resume music
     */
    public void play(){
        player.setVolume(0.4);
        this.player.play();
    }

    /**
     * Pause music
     */
    public void pause(){
        player.pause();
    }

    /**
     * Stop music
     */
    public void stop(){
        player.stop();
    }
}
