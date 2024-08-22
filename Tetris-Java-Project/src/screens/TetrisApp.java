package screens;
import javax.swing.*;
import java.awt.*;


public class TetrisApp {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public TetrisApp() {
        SplashScreen splash = new SplashScreen(2000);
        splash.showSplash();

        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Tetris Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 800);
            frame.setLocationRelativeTo(null);

            cardLayout = new CardLayout();
            mainPanel = new JPanel(cardLayout);

            // Create the different screens
            JPanel homeScreen = new HomeScreen(this);
            JPanel configScreen = new ConfigScreen(this);
            JPanel highScoreScreen = new HighScoreScreen(this);
            JPanel gameDisplay = new GameDisplay(this);

            // Add screens to the main panel
            mainPanel.add(homeScreen, "Home");
            mainPanel.add(configScreen, "Config");
            mainPanel.add(highScoreScreen, "HighScores");
            mainPanel.add(gameDisplay, "GameDisplay");

            // Show the home screen initially
            cardLayout.show(mainPanel, "Home");

            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }

    public void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TetrisApp::new);
    }
}
