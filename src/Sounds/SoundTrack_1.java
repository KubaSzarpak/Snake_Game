package Sounds;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.Objects;

public class SoundTrack_1 {

    private Clip clip;

    public SoundTrack_1(){
        try {
            AudioInputStream audioSystem = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/sounds/SoundTrack_1.wav")));
            clip = AudioSystem.getClip();
            clip.open(audioSystem);
            FloatControl valumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            valumeControl.setValue(-20.0f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){
        clip.setFramePosition(0);
        clip.start();
        clip.loop(10);
    }

    public void stop(){
        clip.stop();
    }
}
