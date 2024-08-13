package screens;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HighScoreScreen extends JPanel {
    public HighScoreScreen (TetrisApp app) {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("High Scores");
        titleLabel.setFont(new Font("Dialog",Font.PLAIN, 32));
        add(titleLabel, BorderLayout.NORTH);
        //panel creation
        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        //button creation
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> app.showScreen("Home"));
        //title creation
        //Adding buttons to buttonPanel
        buttonPanel.add(backButton);


        //Set location of panels
        add(buttonPanel, BorderLayout.PAGE_END);

    }
}
