package screens;

import DataHandling.ScoreHandler;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

import static Components.ComponentFactory.*;

class UserScore {
    String username;
    int score;

    UserScore(String username, int score) {
        this.username = username;
        this.score = score;
    }
}

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

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.PAGE_END);

        // Load and display scores
        displayScores();
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
                scoreDisplaysVertical.add(createUserScoreLabel(userScore.username, userScore.score));
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

        // Use ScoreHandler to load scores from file
        JsonObject scores = ScoreHandler.loadScores();

        // Parse the loaded JSON scores
        for (String username : scores.keySet()) {
            JsonElement scoreElement = scores.get(username);
            if (scoreElement != null && scoreElement.isJsonPrimitive()) {
                int score = scoreElement.getAsInt();
                userScores.add(new UserScore(username, score));
            }
        }

        return userScores;
    }
}
