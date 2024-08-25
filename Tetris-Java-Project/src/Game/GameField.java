package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import java.util.ArrayList;


public class GameField extends JPanel {
    private int[][] grid;
    private int rows;
    private int cols;
    private Tetromino currentTetromino;
    private ArrayList<Tetromino> placedTetrominos; // List to store placed tetrominos
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
        this.placedTetrominos = new ArrayList<>(); // Ensure it's initialized here
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
        if (currentTetromino != null) {
            // Check if moving left is within bounds and not colliding with other blocks
            if (canMoveTo(currentTetromino, currentTetromino.getX() - 1, currentTetromino.getY())) {
                currentTetromino.setX(currentTetromino.getX() - 1);
                repaint(); // Request to redraw the game field with the updated position
            }
        }
    }

    public void moveTetrominoRight() {
        if (currentTetromino != null) {
            // Check if moving right is within bounds and not colliding with other blocks
            if (canMoveTo(currentTetromino, currentTetromino.getX() + 1, currentTetromino.getY())) {
                currentTetromino.setX(currentTetromino.getX() + 1);
                repaint(); // Request to redraw the game field with the updated position
            }
        }
    }

    public void moveTetrominoDown() {
        if (canMoveDown(currentTetromino)) {
            currentTetromino.moveDown();
        } else {
            // Place the tetromino and generate a new one
            placeTetromino();
        }
        repaint();
    }

    // Checks if the tetromino can move to the specified position
    private boolean canMoveTo(Tetromino tetromino, int newX, int newY) {
        // logic to check if the new position is within bounds and does not collide with existing blocks
        for (Point block : tetromino.getBlocks()) {
            int newCol = block.x + newX;
            int newRow = block.y + newY;

            // Check if the new position is within the grid bounds
            if (newCol < 0 || newCol >= cols || newRow >= rows) {
                return false;
            }

            // Check if the new position collides with existing blocks
            if (grid[newRow][newCol] != 0) {
                return false;
            }
        }
        return true;
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

    // Example method for placing tetrominoes
    private void placeTetromino() {
        placedTetrominos.add(currentTetromino);
        //checkForFullRows(); // Optional: Check if any row is fully occupied
        TetrominoShapeDefiner randomShape = TetrominoShapeDefiner.getRandomShape();
        currentTetromino = new Tetromino(randomShape, cols / 2, 0);
    }


    private boolean canMoveDown(Tetromino tetromino) {
        for (Point block : tetromino.getBlocks()) {
            int newX = tetromino.getX() + block.x;
            int newY = tetromino.getY() + block.y + 1; // Check the position below

            // Check if it hits the bottom of the grid
            if (newY >= rows) {
                return false;
            }

            // Check if it collides with any placed tetromino
            for (Tetromino placed : placedTetrominos) {
                for (Point placedBlock : placed.getBlocks()) {
                    if (newX == placed.getX() + placedBlock.x && newY == placed.getY() + placedBlock.y) {
                        return false;
                    }
                }
            }
        }
        return true;
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

        //draw placed tetromino
        if (placedTetrominos != null) {
            for (Tetromino tetromino : placedTetrominos) {
                int cellSize = Math.min(getWidth() / cols, getHeight() / rows);
                tetromino.draw(g, cellSize);
            }
        }

        // Draw the current tetromino
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
