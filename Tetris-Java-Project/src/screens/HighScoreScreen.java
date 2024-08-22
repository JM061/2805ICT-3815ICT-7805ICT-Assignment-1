package screens;

import javax.swing.*;
import java.awt.*;
import static Components.ComponentFactory.*;


public class HighScoreScreen extends JPanel {
    private TetrisApp app;
    public HighScoreScreen (TetrisApp app) {
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

        scoreDisplaysVertical.add(createUserScoreLabel("Jacob", 10000));
        scoreDisplaysVertical.add(createUserScoreLabel("Bob", 3230));

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
