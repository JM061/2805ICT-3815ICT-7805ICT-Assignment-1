package DataHandling;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigHandler {

    public static  String CONFIG_FILE = "configurationSettings.json";
    private static final Gson gson = new Gson();

    public static void setConfigFile(String customConfigFile) {
        CONFIG_FILE = customConfigFile;
    }

    public static JsonObject loadSettings() {
        File configFile = new File(CONFIG_FILE);
        JsonObject settings;
        System.out.println("Looking for configuration file at: " + configFile.getAbsolutePath());

        if (!configFile.exists()) {
            System.out.println("Configuration file not found. Creating new file with default settings.");
            settings = setDefaultSettings();
            saveSettings(settings); // Save default settings to the file
        } else {
            try (FileReader reader = new FileReader(configFile)) {
                settings = gson.fromJson(reader, JsonObject.class);
            } catch (IOException e) {
                System.err.println("Failed to read configuration file. Creating default settings.");
                e.printStackTrace();
                settings = setDefaultSettings();
            } catch (Exception e) {
                System.err.println("An unexpected error occurred.");
                e.printStackTrace();
                settings = setDefaultSettings();
            }
        }

        return settings;
    }

    public static void saveSettings(JsonObject settings) {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(settings, writer);
            System.out.println("Configuration settings saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save configuration settings.");
            e.printStackTrace();
        }
    }

    private static JsonObject setDefaultSettings() {
        JsonObject defaultSettings = new JsonObject();
        defaultSettings.addProperty("fieldWidth", 10);
        defaultSettings.addProperty("fieldHeight", 20);
        defaultSettings.addProperty("gameLevel", 1);
        defaultSettings.addProperty("musicEnabled", true);
        defaultSettings.addProperty("soundEffectsEnabled", true);
        defaultSettings.addProperty("extendedMode", false);
        defaultSettings.addProperty("twoPlayerMode", false); // Default to one player
        return defaultSettings;
    }

    public static int getFieldWidth() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get("fieldWidth").getAsInt();
        } catch (Exception e) {
            e.printStackTrace();
            return 10; // Return default value if something goes wrong
        }
    }

    public static int getFieldHeight() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get("fieldHeight").getAsInt();
        } catch (Exception e) {
            e.printStackTrace();
            return 20; // Return default value if something goes wrong
        }
    }

    public static void setFieldHeight(int newHeight) {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            jsonObject.addProperty("fieldHeight", newHeight);
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                writer.write(jsonObject.toString());
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setFieldWidth(int newWidth) {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            jsonObject.addProperty("fieldWidth", newWidth);
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                writer.write(jsonObject.toString());
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setMusic(boolean musicStatus) {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            jsonObject.addProperty("musicEnabled", musicStatus);
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                writer.write(jsonObject.toString());
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setSoundEffects(boolean soundEffectStatus) {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            jsonObject.addProperty("soundEffectsEnabled", soundEffectStatus);
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                writer.write(jsonObject.toString());
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setExtendedMode(boolean extendedStatus) {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            jsonObject.addProperty("extendedMode", extendedStatus);
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                writer.write(jsonObject.toString());
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getExtendedMode() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get("extendedMode").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getTwoPlayerMode() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get("twoPlayerMode").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void setTwoPlayerMode(boolean twoPlayerMode) {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            jsonObject.addProperty("twoPlayerMode", twoPlayerMode);
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                writer.write(jsonObject.toString());
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getMusicStatus() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get("musicEnabled").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getSoundEffectsStatus() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get("soundEffectsEnabled").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
