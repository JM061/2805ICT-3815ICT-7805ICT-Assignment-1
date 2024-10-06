package screens;

import javax.swing.*;
import java.awt.*;
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
        backButton.addActionListener(e -> {
            gameField1.togglePause();
            gameField2.togglePause();
            int response = JOptionPane.showConfirmDialog(app.getFrame(),
                    "Are you sure you want to go back?",
                    "Leave Game?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                app.showScreen("Home"); // If user selects yes, they will be taken back to the home screen
            } else {
                // Resume the focus on the game panel so it can capture the 'P' key
                gameField1.requestFocusInWindow(); // Ensures Player 1 can capture key events
                gameField2.requestFocusInWindow(); // Ensures Player 2 can capture key events
            }
        });
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
        gameField1 = new GameField(fieldHeight, fieldWidth, this, true); // Pass true for Player 1
        gameInfoDisplay1 = new GameInfoDisplay(1, 0, 1, 0);
        gameField1.addObserver(gameInfoDisplay1);

        // Player 2
        gameField2 = new GameField(fieldHeight, fieldWidth, this, false); // Pass false for Player 2
        gameInfoDisplay2 = new GameInfoDisplay(1, 0, 1, 0);
        gameField2.addObserver(gameInfoDisplay2);

        gamePanel.add(gameField1);
        gamePanel.add(gameInfoDisplay1);
        gamePanel.add(gameField2);
        gamePanel.add(gameInfoDisplay2);

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    public void onShow() {
        resetGame();
    }

    public GameField getGameField1() {
        return gameField1;
    }

    public GameField getGameField2() {
        return gameField2;
    }

}
