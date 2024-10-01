package DataHandling;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import DataHandling.UserScore;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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



    public static void saveTopScores(List<UserScore> topScores) {
        JsonObject scores = new JsonObject();
        for (UserScore userScore : topScores) {
            // Create a JsonObject to store both score and level
            JsonObject scoreDetails = new JsonObject();
            scoreDetails.addProperty("score", userScore.score);
            scoreDetails.addProperty("level", userScore.level);

            scores.add(userScore.username, scoreDetails);  // Add it to the main JsonObject
        }

        try (FileWriter writer = new FileWriter(SCORE_FILE)) {
            writer.write(scores.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<UserScore> loadTopScores() {
        List<UserScore> userScores = new ArrayList<>();
        JsonObject scores = loadScores();

        for (String username : scores.keySet()) {
            JsonElement scoreElement = scores.get(username);
            if (scoreElement != null && scoreElement.isJsonObject()) {
                JsonObject scoreDetails = scoreElement.getAsJsonObject();
                int score = scoreDetails.get("score").getAsInt();
                int level = scoreDetails.get("level").getAsInt(); // Retrieve the level

                userScores.add(new UserScore(username, score, level));
            }
        }

        Collections.sort(userScores);  // Sorts in descending order by score and level

        return userScores;
    }

    public static void addNewScore(String username, int score, int level) {
        List<UserScore> topScores = loadTopScores();
        topScores.add(new UserScore(username, score, level));

        Collections.sort(topScores);  // Sorts in descending order by score and level

        if (topScores.size() > 10) {
            topScores = topScores.subList(0, 10);
        }

        saveTopScores(topScores);
    }

        public static void removeScores() {
        // Prompt the user for confirmation
        int response = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to remove all scores?",
                "Confirm Removal",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        // If the user confirms, proceed to remove scores
        if (response == JOptionPane.YES_OPTION) {
            try {
                // Read the existing scores
                FileReader reader = new FileReader(SCORE_FILE);
                JsonObject scores = JsonParser.parseReader(reader).getAsJsonObject();
                reader.close();

                // Clear all scores
                scores.entrySet().clear();

                // Write the updated JSON back to the file
                try (FileWriter writer = new FileWriter(SCORE_FILE)) {
                    writer.write(scores.toString());
                    writer.flush();
                }
                JOptionPane.showMessageDialog(null, "Scores have been removed successfully.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while removing scores.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

}



