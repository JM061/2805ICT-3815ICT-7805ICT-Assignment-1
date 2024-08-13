package screens;
import javax.swing.*;
import java.awt.*;


    public class HomeScreen extends JPanel {
        public HomeScreen(TetrisApp app) {
            setLayout(new BorderLayout());
            JLabel titleLabel = new JLabel("Tetris");
            titleLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            //Create Button Panel
            JPanel buttonPanel = new JPanel();

            //Create Buttons
            JButton startButton = new JButton("Start");
            startButton.setBackground(Color.WHITE);
            startButton.addActionListener(e -> app.showScreen("GameDisplay"));

            startButton.setPreferredSize(new Dimension(125, 50));

            JButton configButton = new JButton("Configuration");
            configButton.addActionListener(e -> app.showScreen("Config"));
            configButton.setBackground(Color.WHITE);
            configButton.setPreferredSize(new Dimension(125, 50));


            JButton highScoreButton = new JButton("High Scores");
            highScoreButton.addActionListener(e -> app.showScreen("HighScores"));
            highScoreButton.setBackground(Color.WHITE);
            highScoreButton.setPreferredSize(new Dimension(125, 50));


            //Add Buttons to ButtonPanel
            buttonPanel.add(highScoreButton);
            buttonPanel.add(startButton);
            buttonPanel.add(configButton);




            add(titleLabel, BorderLayout.NORTH);
            add(buttonPanel, BorderLayout.SOUTH);
        }
    }

