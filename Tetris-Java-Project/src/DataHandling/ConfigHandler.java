package DataHandling;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigHandler {

    public static final String CONFIG_FILE = "configurationSettings.json";
    private static final Gson gson = new Gson();


    public static JsonObject loadSettings() {
        File configFile = new File(CONFIG_FILE);
        JsonObject settings;

        // Check if the config file exists, if not create it with default settings
        if (!configFile.exists()) {
            System.out.println("Configuration file not found. Creating new file with default settings.");
            settings = setDefaultSettings();
            saveSettings(settings); // Save default settings to the file
        } else {
            try (FileReader reader = new FileReader(configFile)) {
                settings = gson.fromJson(reader, JsonObject.class);
            } catch (IOException e) {
                e.printStackTrace(); // Log the error
                settings = setDefaultSettings();
            }
        }

        return settings;
    }

    // Save settings to the JSON file
    public static void saveSettings(JsonObject settings) {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(settings, writer);
        } catch (IOException e) {
            e.printStackTrace(); // Log the error
        }
    }

    // Set default settings if config file doesn't exist
    private static JsonObject setDefaultSettings() {
        JsonObject defaultSettings = new JsonObject();
        defaultSettings.addProperty("fieldWidth", 10);
        defaultSettings.addProperty("fieldHeight", 20);
        defaultSettings.addProperty("gameLevel", 1);
        defaultSettings.addProperty("musicEnabled", true);
        defaultSettings.addProperty("soundEffectsEnabled", true);
        defaultSettings.addProperty("extendedMode", false);
        return defaultSettings;
    }
}