package screens;
import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {
        //SplashScreen splash = new SplashScreen(2000);
        //splash.showSplash();



        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Software Engineering A1: Home");
            mainFrame.setSize(800, 600);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            CardLayout cardLayout = new CardLayout();

            JPanel MainPanel = new JPanel(cardLayout); //Main panel for card layout

            //create each screen for the panels
            HomeScreen homeScreen = new HomeScreen(this);
            ConfigScreen configScreen = new ConfigScreen(this);
            HighScoreScreen HighScoreScreen = new HighScoreScreen(this);

            MainPanel.add(homeScreen, "Home");
            MainPanel.add(configScreen, "Config");
            MainPanel.add(HighScoreScreen, "High Score");

            cardLayout.show(MainPanel, "Home");
            mainFrame.add(MainPanel);

        });
        //public void showScreen(String screenName) {
         //   cardLayout.show(mainPanel, screenName);
        //}
    }
}