package Sounds;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoundHandler {
    private Map<String, Clip> soundEffects;
    public boolean isMuted;
    // Singleton instance
    private static volatile SoundHandler instance = null;

    // Private constructor to prevent external instantiation
    private SoundHandler(String[] soundFiles) {
        soundEffects = new HashMap<>();
        loadSoundEffects(soundFiles);
    }

    // Singleton method to get the instance
    public static synchronized SoundHandler getInstance(String[] soundFiles) {
        if (instance == null) {
            instance = new SoundHandler(soundFiles);
        }
        return instance;
    }

    // Load sound effects from provided sound files
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

    // Plays sound effect when the function is called
    public void playSoundEffect(final String soundName) {
        if (!isMuted) {
            Clip clip = soundEffects.get(soundName);
            if (clip != null) {
                new Thread(() -> {
                    clip.setFramePosition(0); // Rewind to the beginning
                    clip.start(); // Start playing the clip
                }).start();
            } else {
                System.err.println("Sound effect not found: " + soundName);
            }
        }
    }

    // Close all clips
    public void close() {
        for (Clip clip : soundEffects.values()) {
            clip.close();
        }
    }

    public void toggleSound() {
        isMuted = !isMuted; // Toggle the mute state
        if (isMuted) {
            System.out.println("Sound muted.");
        } else {
            System.out.println("Sound unmuted.");
        }
    }
}
