package screens;

import javax.swing.*;
import java.awt.*;

import DataHandling.ConfigHandler;
import Game.*;

public class GameDisplay extends JPanel {
    private TetrisApp app;
    private GameSettings gameSettings;
    private JPanel gamePanel;
    private GameField gameField1;
    private GameField gameField2;
    private GameInfoDisplay gameInfoDisplay1;
    private GameInfoDisplay gameInfoDisplay2;

    public GameDisplay(TetrisApp app, GameSettings settings) {
        this.gameSettings = settings;

        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Play");
        titleLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel();
        gamePanel = new JPanel();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> app.showScreen("Home"));

        buttonPanel.add(backButton);
        add(titleLabel, BorderLayout.PAGE_START);
        add(gamePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    public void resetGame() {
        int fieldWidth = gameSettings.getFieldWidth();
        int fieldHeight = gameSettings.getFieldHeight();
        gamePanel.removeAll();

        // Player 1
        gameField1 = new GameField(fieldHeight, fieldWidth, this);
        gameInfoDisplay1 = new GameInfoDisplay(1, 0, 1, 0);
        gameField1.addObserver(gameInfoDisplay1);

        gamePanel.add(gameField1);
        gamePanel.add(gameInfoDisplay1);

        // Check if two-player mode is enabled
        if (ConfigHandler.getTwoPlayerMode()) {
            // Player 2
            gameField2 = new GameField(fieldHeight, fieldWidth, this);
            gameInfoDisplay2 = new GameInfoDisplay(1, 0, 1, 0);
            gameField2.addObserver(gameInfoDisplay2);

            gamePanel.add(gameField2);
            gamePanel.add(gameInfoDisplay2);
        }

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    public void onShow() {
        resetGame();
    }
}
