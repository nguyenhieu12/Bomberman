import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class TheMedia {
    public static MediaPlayer getMediaPlayer(String path) {
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        return mediaPlayer;
    }

}