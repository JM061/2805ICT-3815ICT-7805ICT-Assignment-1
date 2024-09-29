package DataHandling;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreHandler{
    public static final String SCORE_FILE = "gameScores.json";
    private static final Gson gson = new Gson();


    public static JsonObject loadScores() {
        File scoreFile = new File(SCORE_FILE);
        JsonObject scores = new JsonObject(); // Initialize an empty JsonObject

        if (!scoreFile.exists()) {
            System.out.println("No Score File found. Creating New Score File.");

            // Try to create the file and write an empty JsonObject to it
            try (FileWriter writer = new FileWriter(scoreFile)) {
                writer.write(scores.toString()); // Write empty JsonObject to the file
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return scores; // Return the empty scores object
        } else {
            try (FileReader reader = new FileReader(scoreFile)) {
                scores = gson.fromJson(reader, JsonObject.class); // Load the JSON from file
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return scores;
    }



    public static void saveScores(JsonObject scores) {
        try (FileWriter writer = new FileWriter(SCORE_FILE)) {
            gson.toJson(scores, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
