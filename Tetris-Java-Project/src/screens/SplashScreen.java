package screens;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SplashScreen extends JWindow {
    final private int duration;

    public SplashScreen(int duration) {
        this.duration = duration;
    }

    public void showSplash() {
        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.white);
        // Set the window's bounds, centering the window
        final int width = 600;
        final int height = 800;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);
        // Build the splash screen
        /*
         * JLabel label = new JLabel(new
         * ImageIcon(this.getClass().getResource("/resources/tetrissplashresized.png")))
         * ;
         */

        URL imageUrl = getClass().getClassLoader().getResource("tetrissplashresized.png");
        JLabel label = null;
        if (imageUrl == null) {
            System.err.println("Image not found!");
        } else {
            label = new JLabel(new ImageIcon(imageUrl));
            // Add the label to your layout as needed
        }

        JLabel copyrt = new JLabel("Copyright 2024, Group 12 Tetris Game",
                JLabel.CENTER);
        copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        content.add(label, BorderLayout.CENTER);
        content.add(copyrt, BorderLayout.SOUTH);
        Color oraRed = new Color(28, 115, 255, 255);
        content.setBorder(BorderFactory.createLineBorder(oraRed, 10));
        // Display it
        setVisible(true);
        // Wait a little while, maybe showing a progress bar
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setVisible(false);
    }

    public void showSplashAndExit() {
        showSplash();
        System.exit(0);
    }

    public static void main(String[] args) {
        // Show the splash screen
        SplashScreen splash = new SplashScreen(3000);
        splash.showSplashAndExit();
    }
}