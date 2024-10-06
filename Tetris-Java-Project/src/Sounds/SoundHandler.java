package Sounds;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoundHandler {
    private Map<String, Clip> soundEffects;
    private boolean isMuted;

    // Volatile keyword ensures visibility across threads
    private static volatile SoundHandler instance = null;
    private static String[] soundFilesConfig;

    // Private constructor to prevent external instantiation
    private SoundHandler() {
        soundEffects = new HashMap<>();
        if (soundFilesConfig != null) {
            loadSoundEffects(soundFilesConfig);
        }
    }
    // Thread-safe getInstance using double-checked locking
    public static SoundHandler getInstance(String[] soundFiles) {
        SoundHandler result = instance;
        if (result == null) {
            synchronized (SoundHandler.class) {
                result = instance;
                if (result == null) {
                    soundFilesConfig = soundFiles;
                    instance = result = new SoundHandler();
                }
            }
        }
        return result;
    }

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
    public Map<String, Clip> getSoundEffects() {
        return soundEffects;
    }

    public void playSoundEffect(final String soundName) {
        if (!isMuted) {
            Clip clip = soundEffects.get(soundName);
            if (clip != null) {
                new Thread(() -> {
                    clip.setFramePosition(0);
                    clip.start();
                }).start();
            } else {
                System.err.println("Sound effect not found: " + soundName);
            }
        }
    }

    public void close() {
        for (Clip clip : soundEffects.values()) {
            clip.close();
        }
    }

    public void toggleSound() {
        isMuted = !isMuted;
        if (isMuted) {
            System.out.println("Sound muted.");
        } else {
            System.out.println("Sound unmuted.");
        }
    }

    public boolean isMuted() {
        return isMuted;
    }
}