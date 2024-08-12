package screens;
import javax.swing.*;
import java.awt.*;

public class HighScoreScreen extends JPanel {
    public HighScoreScreen (Main app) {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("High Scores");
        titleLabel.setFont(new Font("Dialog",Font.PLAIN, 32));
        add(titleLabel, BorderLayout.NORTH);
        //panel creation
        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        //button creation
        JButton backButton = new JButton("Back");
        //title creation
        //Adding buttons to buttonPanel
        buttonPanel.add(backButton);


        //Set location of panels
        add(buttonPanel, BorderLayout.PAGE_END);

    }
}
