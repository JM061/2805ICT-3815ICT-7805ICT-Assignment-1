package screens;

import Game.GameField;
import Game.GameSettings;

import javax.swing.*;
import java.awt.*;
import DataHandling.ConfigHandler;
import com.google.gson.JsonObject;
import Game.GameField.*;


public class TetrisApp {
    public JFrame applicationFrame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private GameDisplay gameDisplay;
    private GameSettings gameSettings;
    public JsonObject settings;
    private HighScoreScreen highScoreScreen;

    public TetrisApp() {
        this.gameSettings = new GameSettings(); // Create an instance or load it from config

        //move all of this to another file
        SwingUtilities.invokeLater(() -> {
            settings = ConfigHandler.loadSettings();

            applicationFrame = new JFrame("Tetris Application");
            applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            applicationFrame.setSize(800, 800);
          
            applicationFrame.setLocationRelativeTo(null);

            cardLayout = new CardLayout();
            mainPanel = new JPanel(cardLayout);
            // Create the different screens
            JPanel homeScreen = new HomeScreen(this);
            JPanel configScreen = new ConfigScreen( this, gameSettings);
            highScoreScreen = new HighScoreScreen(this);
            gameDisplay = new GameDisplay(this, gameSettings);

            // Add screens to the main panel
            mainPanel.add(homeScreen, "Home");
            mainPanel.add(configScreen, "Config");
            mainPanel.add(highScoreScreen, "HighScores");
            mainPanel.add(gameDisplay, "GameDisplay");

            // Show the home screen initially
            cardLayout.show(mainPanel, "Home");
           // System.out.println("CURRENT GAME STATUS:" + GAME_STATUS);
            applicationFrame.add(mainPanel);
            applicationFrame.setVisible(true);
        });
    }

    public void showScreen(String screenName) {
       cardLayout.show(mainPanel, screenName);
       if (screenName.equals("HighScores")) {
           highScoreScreen.refreshScores();//refreshes scores when page is loaded
       }
       if(screenName.equals("GameDisplay")) {
           gameDisplay.onShow();
       }
    }

    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen(2000);
        splash.showSplash();
        SwingUtilities.invokeLater(TetrisApp::new);
    }
    public JFrame getFrame() {
        return applicationFrame;
    }
}
