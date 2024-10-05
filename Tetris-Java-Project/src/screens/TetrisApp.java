package screens;

import javax.swing.*;
import Game.*;
import java.awt.*;
public class TetrisApp {
    public JFrame applicationFrame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private GameDisplay gameDisplay;
    private GameSettings gameSettings;
    private HighScoreScreen highScoreScreen;

    public TetrisApp() {
        gameSettings = new GameSettings();

        SwingUtilities.invokeLater(() -> {
            applicationFrame = new JFrame("Tetris Application");
            applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            applicationFrame.setSize(800, 800);
            applicationFrame.setLocationRelativeTo(null);

            cardLayout = new CardLayout();
            mainPanel = new JPanel(cardLayout);
            JPanel homeScreen = new HomeScreen(this);
            JPanel configScreen = new ConfigScreen(this, gameSettings);
            highScoreScreen = new HighScoreScreen(this);
            gameDisplay = new GameDisplay(this, gameSettings);

            mainPanel.add(homeScreen, "Home");
            mainPanel.add(configScreen, "Config");
            mainPanel.add(highScoreScreen, "HighScores");
            mainPanel.add(gameDisplay, "GameDisplay");

            cardLayout.show(mainPanel, "Home");
            applicationFrame.add(mainPanel);
            applicationFrame.setVisible(true);
        });
    }

    public void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
        if (screenName.equals("HighScores")) {
            highScoreScreen.refreshScores();
        }
        if (screenName.equals("GameDisplay")) {
            gameDisplay.onShow();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TetrisApp::new);
    }
    public JFrame getFrame() {
        return applicationFrame;
    }
}
