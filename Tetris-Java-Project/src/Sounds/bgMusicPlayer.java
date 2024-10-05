package Sounds;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.InputStream;

public class bgMusicPlayer implements Runnable {
    private final String filePath;
    private Player player;
    private boolean isPaused;
    private boolean isStopped;
    private boolean repeat;

    // The singleton instance, volatile to ensure thread safety
    private static volatile bgMusicPlayer instance = null;

    // Private constructor to prevent external instantiation
    private bgMusicPlayer(String filePath) {
        this.filePath = filePath;
        this.isPaused = false;
        this.isStopped = false;
        this.repeat = false;
    }

    // Singleton pattern to ensure only one instance
    public static synchronized bgMusicPlayer getInstance(String filePath) {
        if (instance == null) {
            instance = new bgMusicPlayer(filePath);
        }
        return instance;
    }

    // Initialize the player
    private synchronized boolean initPlayer() {
        try {
            System.out.println("Loading the MP3 file: " + filePath);
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            if (inputStream == null) {
                System.out.println("Error: MP3 file not found at " + filePath);
                return false;
            }
            player = new Player(inputStream);
            System.out.println("MP3 file loaded successfully.");
            return true;
        } catch (JavaLayerException e) {
            System.out.println("Error: Failed to load MP3 file: " + e.getMessage());
            return false;
        }
    }


    @Override
    public void run() {
        do {
            if (!isPaused && !isStopped) {
                boolean initialized = initPlayer();
                if (initialized) {
                    try {
                        System.out.println("Background Music Started");
                        player.play();  // This starts the music playback
                    } catch (JavaLayerException e) {
                        System.out.println("Error during playback: " + e.getMessage());
                    }
                } else {
                    System.out.println("Failed to initialize the player. Exiting...");
                    break;
                }
            }

            // Sleep to avoid busy looping
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error during sleep: " + e.getMessage());
            }
        } while (repeat && !isStopped);  // Loop if repeat is true and not stopped
    }

    public synchronized void pause() {
        isPaused = true;
        if (player != null) {
            player.close();
        }
        System.out.println("Music Paused.");
    }

    public synchronized void resume() {
        if (!isStopped) {
            isPaused = false;
            Thread resumeThread = new Thread(this); // Start a new thread for resuming
            resumeThread.start(); // Start the music playback again
            System.out.println("Music Resumed.");
        } else {
            System.out.println("Cannot resume; music is stopped.");
        }
    }

    public synchronized void stop() {
        isStopped = true;
        if (player != null) {
            player.close();
        }
        System.out.println("Music Stopped.");
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public synchronized boolean isPaused() {
        return isPaused;
    }
}
