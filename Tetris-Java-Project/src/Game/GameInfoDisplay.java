package Game;

import javax.swing.*;
import java.awt.*;


public class GameInfoDisplay extends JPanel implements GameObserver {
    private JLabel levelLabel;
    private JLabel scoreLabel;
    private JLabel initialLevelLabel;
    private JLabel linesErasedLabel;

    public GameInfoDisplay(int level, int score, int initialLevel, int linesErased) {
        JPanel gameInfoPanel =  new JPanel(new GridLayout(4,1));

        levelLabel = new JLabel("Current Level: " + level);
        scoreLabel = new JLabel("Current Score: " + score);
        initialLevelLabel = new JLabel("Initial Level: "+ initialLevel);
        linesErasedLabel = new JLabel("Lines Erased: "+ linesErased);

        gameInfoPanel.add(levelLabel);
        gameInfoPanel.add(scoreLabel);
        gameInfoPanel.add(initialLevelLabel);
        gameInfoPanel.add(linesErasedLabel);
        add(gameInfoPanel);
        gameInfoPanel.repaint();
        gameInfoPanel.revalidate();
    }

    // Method to reset the displayed information for a new game
    public void resetInfo() {
        updateLevel(1); // Reset to initial level
        updateScore(0); // Reset score to 0
        updateRowsCleared(0); // Reset rows cleared to 0
    }

    @Override
    public void updateScore(int score) {
        scoreLabel.setText("Current Score: " + score);
        //System.out.println("Score: " + score);
    }

    @Override
    public void updateLevel(int level) {
        levelLabel.setText("Current Level: " + level);

        System.out.println("Level: " + level);
    }

    @Override
    public void updateRowsCleared(int rowsCleared) {
        linesErasedLabel.setText("Lines Erased: " + rowsCleared);
        System.out.println("Rows Cleared: " + rowsCleared);
    }

}
