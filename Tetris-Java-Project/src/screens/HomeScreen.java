package screens;
import javax.swing.*;
import java.awt.*;


    public class HomeScreen extends JPanel {
        public HomeScreen(Main app) {
            setLayout(new BorderLayout());
            JLabel titleLabel = new JLabel("Tetris");
            titleLabel.setFont(new Font("Dialog", Font.PLAIN, 32));

            //Create Button Panel
            JPanel buttonPanel = new JPanel();

            //Create Buttons
            JButton startButton = new JButton("Start");
            JButton configButton = new JButton("Configuration");
            JButton highScoreButton = new JButton("High Scores");

            //Add Buttons to ButtonPanel
            buttonPanel.add(highScoreButton);
            buttonPanel.add(startButton);
            buttonPanel.add(configButton);

            add(titleLabel, BorderLayout.NORTH);
            add(buttonPanel, BorderLayout.SOUTH);
        }
    }

