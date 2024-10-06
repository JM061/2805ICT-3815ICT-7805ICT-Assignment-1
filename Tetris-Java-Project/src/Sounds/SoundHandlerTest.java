package Sounds;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SoundHandlerTest {

    private SoundHandler soundHandler;
    private String[] soundFiles = {
            "Sounds/clear_row_sound.wav",
            "Sounds/game_over_sound.wav",
            "Sounds/level_up_sound.wav",
            "Sounds/place_tetromino.wav"
    };

    @BeforeEach
    void setUp() {
        // Initialize the SoundHandler with the provided sound files
        soundHandler = SoundHandler.getInstance(soundFiles);
    }

    @AfterEach
    void tearDown() {
        // Close the sound handler after each test to release resources
        soundHandler.close();
    }

    @Test
    void testLoadSoundEffects() {
        // Ensure all sound files have been loaded into the soundEffects map
        Map<String, Clip> soundEffects = soundHandler.getSoundEffects(); // Expose getter for testing purposes

        assertNotNull(soundEffects, "Sound effects map should not be null.");
        assertEquals(4, soundEffects.size(), "All sound files should be loaded.");

        // Check if each sound file is loaded correctly
        for (String soundFile : soundFiles) {
            assertTrue(soundEffects.containsKey(soundFile), "Sound file " + soundFile + " should be loaded.");
        }
    }

    @Test
    void testPlaySoundEffect() {
        // Test playing a sound effect (verify that the Clip is reset and started)
        soundHandler.playSoundEffect("Sounds/clear_row_sound.wav");
        Clip clip = soundHandler.getSoundEffects().get("Sounds/clear_row_sound.wav");
        assertNotNull(clip, "Clip for 'clear_row_sound' should be loaded.");
        assertEquals(0, clip.getFramePosition(), "Clip should start at frame position 0 when played.");
    }

    @Test
    void testToggleSound() {
        // Initially, sound is unmuted
        soundHandler.playSoundEffect("Sounds/game_over_sound.wav");
        assertFalse(soundHandler.isMuted(), "Sound should not be muted initially.");

        // Toggle to mute
        soundHandler.toggleSound();
        assertTrue(soundHandler.isMuted(), "Sound should be muted after toggle.");

        // Toggle to unmute
        soundHandler.toggleSound();
        assertFalse(soundHandler.isMuted(), "Sound should be unmuted after second toggle.");
    }
}