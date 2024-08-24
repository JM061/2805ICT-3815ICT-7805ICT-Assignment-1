package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class GameField extends JPanel {
    private int[][] grid;
    private int rows;
    private int cols;
    private Tetromino currentTetromino;
    private final int GAME_STARTED = 1;
    private final int GAME_PAUSED = 2;
    private final int GAME_FINISHED = 3;
    public int GAME_STATUS = GAME_STARTED;

    private Map<String, Action> actions;

    public GameField(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new int[rows][cols];
        this.setPreferredSize(new Dimension(251, 501)); // Set preferred size
        this.setBackground(Color.WHITE);

        setFocusable(true);
        requestFocusInWindow();

        setupKeyBindings();


        //generates the first tetromino on page load
        //random
        TetrominoShapeDefiner randomShape = TetrominoShapeDefiner.getRandomShape();
        currentTetromino = new Tetromino(randomShape, cols / 2, 0);
    }

    //key binding setup
    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        // Define actions for key events
        actions = new HashMap<>();
        actions.put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        });
        actions.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveTetrominoLeft();
            }
        });
        actions.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveTetrominoRight();
            }
        });
        actions.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveTetrominoDown();
            }
        });

        // Bind keys to actions
        inputMap.put(KeyStroke.getKeyStroke("P"), "pause");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");

        actionMap.put("pause", actions.get("pause"));
        actionMap.put("moveLeft", actions.get("moveLeft"));
        actionMap.put("moveRight", actions.get("moveRight"));
        actionMap.put("moveDown", actions.get("moveDown"));
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setBlock(int row, int col, int value) {
        grid[row][col] = value;
    }

    // Function to move tetromino left
    public void moveTetrominoLeft() {
        System.out.println("LEFT KEY PRESSED");
    }

    // Function to move tetromino right
    public void moveTetrominoRight() {
        System.out.println("RIGHT KEY PRESSED");
    }

    // Function to move tetromino down
    public void moveTetrominoDown() {
        System.out.println("DOWN KEY PRESSED");
    }

    // Change status to paused
    private void togglePause() {
        if (GAME_STATUS == GAME_STARTED) {
            GAME_STATUS = GAME_PAUSED;
            System.out.println("GAME Status: " + GAME_STATUS);
        } else if (GAME_STATUS == GAME_PAUSED) {
            GAME_STATUS = GAME_STARTED;
            System.out.println("Game Status: " + GAME_STATUS);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        generateCells(g);

        if (GAME_STATUS == 1) {
            System.out.println("GAME STATUS: " + GAME_STATUS);
        }
        if (GAME_STATUS == GAME_PAUSED) {
            showPaused(g);
        }

        if (currentTetromino != null) {
            int cellSize = Math.min(getWidth() / cols, getHeight() / rows);
            currentTetromino.draw(g, cellSize);
        }
    }

    // Displays pause text when game is paused
    private void showPaused(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        String pauseMessage = "Game Paused";
        String pauseMessage2 = "Please press 'P' to keep playing";
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(pauseMessage)) / 2;
        int y = (getHeight() / 2) + fm.getAscent();
        g.drawString(pauseMessage, x, y);
        g.drawString(pauseMessage2, x - 65, y + fm.getAscent());
    }

    private void generateCells(Graphics g) {
        int cellSize = Math.min(getWidth() / cols, getHeight() / rows);
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    }
}
