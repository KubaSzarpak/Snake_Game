package Sounds;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.Objects;


public class EatingSound {

    private Clip clip;

    public EatingSound(){
        try {
            AudioInputStream audioSystem = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/Sounds/Eat_Munch.wav")));
            clip = AudioSystem.getClip();
            clip.open(audioSystem);
            FloatControl valumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            valumeControl.setValue(-30.0f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){
        clip.setFramePosition(0);
        clip.start();
    }

    public void stop(){
        clip.stop();
    }
}
