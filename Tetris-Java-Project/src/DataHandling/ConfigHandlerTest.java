package DataHandling;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class ConfigHandlerTest {

    private final String testConfigFile = "testConfigurationSettings.json"; // Use a test config file

    @BeforeEach
    void setUp() {
        // Create a temporary config file for testing
        ConfigHandler.CONFIG_FILE = testConfigFile;
        JsonObject defaultSettings = ConfigHandler.loadSettings();
        ConfigHandler.saveSettings(defaultSettings);
    }

    @AfterEach
    void tearDown() throws Exception {
        // Clean up by removing the test config file after each test
        Files.deleteIfExists(new File(testConfigFile).toPath());
    }

    // Test loading settings from the configuration file
    @Test
    void testLoadSettings_CreatesDefaultFile() {
        // Ensure the config file does not exist before the test
        File configFile = new File(ConfigHandler.CONFIG_FILE);
        if (configFile.exists()) {
            configFile.delete();
        }

        // Load settings, which should create the default config file
        JsonObject settings = ConfigHandler.loadSettings();

        // Verify that the config file has been created
        assertTrue(configFile.exists(), "Configuration file should be created.");

        // Verify the default settings
        assertEquals(10, settings.get("fieldWidth").getAsInt(), "Default fieldWidth should be 10.");
        assertEquals(20, settings.get("fieldHeight").getAsInt(), "Default fieldHeight should be 20.");
    }


    // Test saving modified settings to the configuration file
    @Test
    void testSaveSettings_SavesNewValues() {
        JsonObject settings = new JsonObject();
        settings.addProperty("fieldWidth", 15);
        settings.addProperty("fieldHeight", 25);

        // Save the new settings
        ConfigHandler.saveSettings(settings);

        // Reload settings to verify
        JsonObject loadedSettings = ConfigHandler.loadSettings();

        // Verify the values were saved correctly
        assertEquals(15, loadedSettings.get("fieldWidth").getAsInt(), "fieldWidth should be 15.");
        assertEquals(25, loadedSettings.get("fieldHeight").getAsInt(), "fieldHeight should be 25.");
    }

    // Test retrieving the field width from the configuration file
    @Test
    void getWidth() {
        int width = ConfigHandler.getFieldWidth();
        assertEquals(10, width, "Default field width should be 10");
    }

    // Test retrieving the field height from the configuration file
    @Test
    void getHeight() {
        int height = ConfigHandler.getFieldHeight();
        assertEquals(20, height, "Default field height should be 20");
    }

    // Test setting a new field height in the configuration file
    @Test
    void setHeight() {
        ConfigHandler.setFieldHeight(25);
        int updatedHeight = ConfigHandler.getFieldHeight();
        assertEquals(25, updatedHeight, "Field height should be updated to 25");
    }

    // Test setting a new field width in the configuration file
    @Test
    void setWidth() {
        ConfigHandler.setFieldWidth(30);
        int updatedWidth = ConfigHandler.getFieldWidth();
        assertEquals(30, updatedWidth, "Field width should be updated to 30");
    }

    // Test enabling/disabling music in the configuration file
    @Test
    void setMusic() {
        ConfigHandler.setMusic(false);
        assertFalse(ConfigHandler.getMusicStatus(), "Music should be disabled");

        ConfigHandler.setMusic(true);
        assertTrue(ConfigHandler.getMusicStatus(), "Music should be enabled");
    }

    // Test enabling/disabling sound effects in the configuration file
    @Test
    void setSound() {
        ConfigHandler.setSoundEffects(false);
        assertFalse(ConfigHandler.getSoundEffectsStatus(), "Sound effects should be disabled");

        ConfigHandler.setSoundEffects(true);
        assertTrue(ConfigHandler.getSoundEffectsStatus(), "Sound effects should be enabled");
    }

    // Test setting and retrieving the extended mode status
    @Test
    void setExtended() {
        ConfigHandler.setExtendedMode(true);
        assertTrue(ConfigHandler.getExtendedMode(), "Extended mode should be enabled");

        ConfigHandler.setExtendedMode(false);
        assertFalse(ConfigHandler.getExtendedMode(), "Extended mode should be disabled");
    }

    // Test retrieving the extended mode status from the configuration file
    @Test
    void getExtended() {
        boolean extended = ConfigHandler.getExtendedMode();
        assertFalse(extended, "Default extended mode should be false");
    }

    // Test retrieving the music status from the configuration file
    @Test
    void getMusic() {
        boolean music = ConfigHandler.getMusicStatus();
        assertTrue(music, "Default music status should be enabled (true)");
    }

    // Test retrieving the sound effects status from the configuration file
    @Test
    void getSound() {
        boolean soundEffects = ConfigHandler.getSoundEffectsStatus();
        assertTrue(soundEffects, "Default sound effects status should be enabled (true)");
    }
}
