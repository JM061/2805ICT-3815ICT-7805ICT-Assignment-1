package screens;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
    public HighScoreScreen (TetrisApp app) {
        List<UserScore> userScores = new ArrayList<>();
        userScores.add(new UserScore("Jacob", 100000));
        userScores.add(new UserScore("Jonas", 32302));
        userScores.add(new UserScore("Antu", 23521));
        userScores.add(new UserScore("Matilda", 23455));
        userScores.add(new UserScore("Sachin", 2134));
        userScores.add(new UserScore("Garry", 1234));
        userScores.add(new UserScore("Larry", 1234));
        userScores.add(new UserScore("Billy", 1234));
        userScores.add(new UserScore("Jarrod", 1234));
        userScores.add(new UserScore("Jane", 1234));




        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("High Scores");
        titleLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);
        //panel creation
        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel ScoreDisplayPanel = new JPanel();
        Box scoreDisplaysVertical = Box.createVerticalBox();

        for (UserScore userScore : userScores) {
            scoreDisplaysVertical.add(createUserScoreLabel(userScore.username, userScore.score));
        }


        //button creation


        ScoreDisplayPanel.add(scoreDisplaysVertical);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> app.showScreen("Home"));
        backButton.setBackground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(100, 50));
        //backButton.setBorder(new RoundedBorder(Color.LIGHT_GRAY, 1, 10));

        //title creation
        //Adding buttons to buttonPanel
        buttonPanel.add(backButton);


        //Set location of panels
        add(ScoreDisplayPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);

    }




}
