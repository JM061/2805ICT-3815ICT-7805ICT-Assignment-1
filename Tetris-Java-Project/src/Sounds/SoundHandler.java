package Sounds;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SoundHandler {
    private Map<String, Clip> soundEffects;

    public SoundHandler(String[] soundFiles){
        soundEffects = new HashMap<>();
        loadSoundEffects(soundFiles);
    }

//load sound effects saves in sound files
    private void loadSoundEffects(String[] soundFiles) {
        for (String filePath : soundFiles) {
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                        getClass().getClassLoader().getResourceAsStream(filePath)
                );
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                soundEffects.put(filePath, clip);
                System.out.println(filePath + " loaded");
            } catch (Exception e) {
                System.err.println("Error loading sound file: " + filePath);
                e.printStackTrace();
            }
        }
    }

//plays sound effect when function is called
    public void playSoundEffect(final String soundName){
        Clip clip = soundEffects.get(soundName);
        if (clip != null) {
            new Thread(()->{
                clip.setFramePosition(0);
                clip.start();
            }).start();
        } else {
            System.err.println("Sound effect not found: " + soundName);
        }
    }

    public void close(){
        for(Clip clip: soundEffects.values()){
            clip.close();
        }
    }

}
