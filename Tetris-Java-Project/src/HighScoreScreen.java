import javax.swing.*;
import java.awt.*;

public class HighScoreScreen {
    public static void main(String[] args) {
        JFrame highScoreFrame = new JFrame("Software Engineering A1: High Score");
        highScoreFrame.setSize(800, 600);
        highScoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //panel creation
        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        //button creation
        JButton backButton = new JButton("Back");


        //title creation
        JLabel titleLabel = new JLabel("High Scores");
        titleLabel.setFont(new Font("Dialog",Font.PLAIN, 32));

        //Adding buttons to buttonPanel
        titlePanel.add(titleLabel);
        buttonPanel.add(backButton);


        //Set location of panels
        highScoreFrame.add(titlePanel, BorderLayout.NORTH);
        highScoreFrame.add(buttonPanel, BorderLayout.PAGE_END);
        highScoreFrame.setVisible(true);

    }
}
