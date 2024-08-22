package screens;

import javax.swing.*;
import java.awt.*;
import static Components.ComponentFactory.*;

public class HomeScreen extends JPanel {

    public HomeScreen(TetrisApp app) {
        setLayout(new BorderLayout());

        // Create title label
        JLabel titleLabel = new JLabel("Tetris");
        titleLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create button panel and set its layout to BoxLayout (vertical layout)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Center the button panel within the available space
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create buttons and add them to the button panel with spacing
        JButton startButton = createNavigationbutton("Start", "GameDisplay", 150, 50, app);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalStrut(15)); // Add space between buttons

        JButton configButton = createNavigationbutton("Configuration", "Config", 150, 50, app);
        configButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button
        buttonPanel.add(configButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        JButton highScoreButton = createNavigationbutton("High Scores", "HighScores", 150, 50, app);
        highScoreButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button
        buttonPanel.add(highScoreButton);
        buttonPanel.add(Box.createVerticalStrut(15));

        JButton exitButton = createNavigationbutton("Exit", "Exit", 150, 50, app);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button
        buttonPanel.add(exitButton);

        // Add title label to the top
        add(titleLabel, BorderLayout.NORTH);

        // Center the button panel vertically and horizontally
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.add(buttonPanel, new GridBagConstraints());

        add(wrapperPanel, BorderLayout.CENTER);
    }
}
