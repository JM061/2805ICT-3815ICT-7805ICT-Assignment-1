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

        // Check if the config file exists, if not create it with default settings
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

    // Save settings to the JSON file
    public static void saveSettings(JsonObject settings) {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(settings, writer);
            System.out.println("Configuration settings saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save configuration settings.");
            e.printStackTrace();
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
        defaultSettings.addProperty("player2Status", false);
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
        // Validate the new height
        if (newHeight < 10 || newHeight > 30) {
            throw new IllegalArgumentException("fieldHeight must be between 10 and 30");
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            // Parse the existing configuration
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            // Modify the fieldHeight value
            jsonObject.addProperty("fieldHeight", newHeight);
            // Write the updated JSON back to the file
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                writer.write(jsonObject.toString());
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setFieldWidth(int newWidth) {
        // Validate the new width
        if (newWidth < 5 || newWidth > 20) {
            throw new IllegalArgumentException("fieldWidth must be between 5 and 20");
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            // Parse the existing configuration
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            // Modify the fieldWidth value
            jsonObject.addProperty("fieldWidth", newWidth);
            // Write the updated JSON back to the file
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                writer.write(jsonObject.toString());
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions (FileReader or FileWriter)
        }
    }

    public static void setGameLevel(int newLevel){
        // Validate the new width
        if (newLevel < 1 || newLevel > 10) {
            throw new IllegalArgumentException("Game must be between 1 and 10");
        }
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            // Parse the existing configuration
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            // Modify the fieldWidth value
            jsonObject.addProperty("gameLevel", newLevel);
            // Write the updated JSON back to the file
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                writer.write(jsonObject.toString());
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions (FileReader or FileWriter)
        }
    }




    public static void setMusic(boolean musicStatus){
        try (FileReader reader = new FileReader(CONFIG_FILE)){
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            jsonObject.addProperty("musicEnabled", musicStatus);


            try(FileWriter writer = new FileWriter(CONFIG_FILE)){
                writer.write(jsonObject.toString());
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


public static void setSoundEffects(boolean soundEffectStatus){
    try (FileReader reader = new FileReader(CONFIG_FILE)){
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        jsonObject.addProperty("soundEffectsEnabled", soundEffectStatus);


        try(FileWriter writer = new FileWriter(CONFIG_FILE)){
            writer.write(jsonObject.toString());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } catch (Exception e){
        e.printStackTrace();
    }
}

    public static void setPlayer2(boolean player2Status) {
        try (FileReader reader = new FileReader(CONFIG_FILE)){
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            jsonObject.addProperty("player2Status", player2Status);
            try(FileWriter writer = new FileWriter(CONFIG_FILE)){
                writer.write(jsonObject.toString());
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public static boolean getPlayer2Status(){
        try (FileReader reader = new FileReader(CONFIG_FILE)){
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get("player2Status").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



public static void setExtendedMode(boolean extendedStatus){
    try (FileReader reader = new FileReader(CONFIG_FILE)){
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        jsonObject.addProperty("extendedMode", extendedStatus);


        try(FileWriter writer = new FileWriter(CONFIG_FILE)){
            writer.write(jsonObject.toString());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } catch (Exception e){
        e.printStackTrace();
    }
}

    public static boolean getExtendedMode(){
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get("extendedMode").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return default value if something goes wrong
        }
    }

    public static boolean getMusicStatus(){
        try (FileReader reader = new FileReader(CONFIG_FILE)){
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get("musicEnabled").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean getSoundEffectsStatus(){
        try(FileReader reader = new FileReader(CONFIG_FILE)){
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get("soundEffectsEnabled").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static int getInitLevel(){
        try(FileReader reader = new FileReader(CONFIG_FILE)){
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get("gameLevel").getAsInt();
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }


    public static boolean getAiPlayStatus(){
        try(FileReader reader = new FileReader(CONFIG_FILE)){
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get("soundEffectsEnabled").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}





