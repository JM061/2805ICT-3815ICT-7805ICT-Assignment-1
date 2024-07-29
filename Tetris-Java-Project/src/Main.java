import javax.swing.*;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen(2000);
        splash.showSplash();


        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Software Engineering A1: Home");
            mainFrame.setSize(800, 600);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

             //code to create Panels
            JPanel buttonPanel = new JPanel();
            JPanel titlePanel = new JPanel();


            //title creation
            JLabel titleLabel = new JLabel("Tetris");
            titleLabel.setFont(new Font("Dialog",Font.PLAIN, 32));

            //create buttons
            JButton startButton = new JButton("Start");
            JButton configButton = new JButton("Configuration");
            JButton highScoreButton = new JButton("High Scores");

            //Adding buttons to buttonPanel
            buttonPanel.add(highScoreButton);
            buttonPanel.add(startButton);
            buttonPanel.add(configButton);

            //Adding label to titlePanel
            titlePanel.add(titleLabel);

            //Set location of panels
            mainFrame.add(titlePanel, BorderLayout.NORTH);
            mainFrame.add(buttonPanel, BorderLayout.PAGE_END);
            mainFrame.setVisible(true);

        });
    }
}