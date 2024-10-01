package screens;

import DataHandling.ScoreHandler;
import DataHandling.UserScore; // Import the UserScore class
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import java.util.Collections;
import static Components.ComponentFactory.*;


public class HighScoreScreen extends JPanel {
    private TetrisApp app;
    private JPanel scoreDisplayPanel; // Panel for scores or message

    public HighScoreScreen(TetrisApp app) {
        this.app = app;

        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("High Scores");
        titleLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Panel for score display or message
        scoreDisplayPanel = new JPanel();
        add(scoreDisplayPanel, BorderLayout.CENTER);

        // Back button creation
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> app.showScreen("Home"));
        backButton.setBackground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(100, 50));

        JButton clearScoresButton = new JButton("Clear Scores");
        clearScoresButton.setBackground(Color.WHITE);
        clearScoresButton.setPreferredSize(new Dimension(125, 50));
        clearScoresButton.addActionListener(e -> {
            ScoreHandler.removeScores();
            this.refreshScores();});

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(clearScoresButton);
        add(buttonPanel, BorderLayout.PAGE_END);

        // Load and display scores
        displayScores();
    }

    //refresh scores on load
    public void refreshScores() {
        displayScores();
        System.out.println("Refreshed Scores");
        revalidate();
        repaint();
    }


    // Load user scores and update the score display
    private void displayScores() {
        List<UserScore> userScores = loadUserScores();

        // Clear previous content in the score display panel
        scoreDisplayPanel.removeAll();
        if (!userScores.isEmpty()) {
            // Display user scores
            Box scoreDisplaysVertical = Box.createVerticalBox();
            for (UserScore userScore : userScores) {
                scoreDisplaysVertical.add(createUserScoreLabel(userScore.username, userScore.score, userScore.level));
            }
            scoreDisplayPanel.add(scoreDisplaysVertical);
        } else {
            // Display "No scores available yet" message
            JLabel noScoresLabel = new JLabel("No scores available yet.");
            noScoresLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
            noScoresLabel.setHorizontalAlignment(SwingConstants.CENTER);
            scoreDisplayPanel.add(noScoresLabel, BorderLayout.CENTER);
        }

        // Refresh the panel
        scoreDisplayPanel.repaint();
    }

    // Load scores from ScoreHandler
    private List<UserScore> loadUserScores() {
        List<UserScore> userScores = new ArrayList<>();
        JsonObject scores = ScoreHandler.loadScores();

        // Parse and add scores to the list
        for (String username : scores.keySet()) {
            JsonElement userScoreElement = scores.get(username);

            // Make sure the userScoreElement is a JsonObject
            if (userScoreElement != null && userScoreElement.isJsonObject()) {
                JsonObject userScoreObject = userScoreElement.getAsJsonObject();

                // Extract score and level
                int score = userScoreObject.get("score").getAsInt();
                int level = userScoreObject.get("level").getAsInt();

                userScores.add(new UserScore(username, score, level));
            }
        }

        // Sort the list using the compareTo method defined in UserScore (sorts by score)
        Collections.sort(userScores);

        return userScores;
    }
}
