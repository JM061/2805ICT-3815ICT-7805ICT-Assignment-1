package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameField extends JPanel {
    private final Color[][] grid;
    private int rows;
    private int cols;
    private Tetromino currentTetromino;
    private ArrayList<Tetromino> placedTetrominos;
    private final int GAME_STARTED = 1;
    private final int GAME_PAUSED = 2;
    private final int GAME_FINISHED = 3;
    public int GAME_STATUS = GAME_STARTED;

    private Map<String, Action> actions;
    private static final int DROP_DELAY = 500; // delay in milliseconds

    //Creating a thread for handling the Tetromino movement
    private Thread gameThread;

    public GameField(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Color[rows][cols];
        this.setPreferredSize(new Dimension(251, 501));
        this.setBackground(Color.WHITE);
        this.placedTetrominos = new ArrayList<>();
        setFocusable(true);
        requestFocusInWindow(); // Set focus on window to allow keyboard inputs
        setupKeyBindings(); // Function to setup keybindings

        // Generates the first Tetromino on page load
        spawnTetromino();  // Make sure this is called before the thread starts

        // Start the game logic thread
        gameThread = new Thread(() -> {
            try {
                while (GAME_STATUS == GAME_STARTED) {
                    moveTetrominoDown(); // Move the Tetromino down
                    Thread.sleep(DROP_DELAY); // Wait before the next move
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        gameThread.start();
    }


    public synchronized void moveTetrominoDown() {
        if (GAME_STATUS == GAME_STARTED && canMoveDown(currentTetromino)) {
            currentTetromino.moveDown();
            repaint();
        } else {
            placeTetromino(); // Place the current Tetromino and spawn a new one
        }
    }


    // Function to stop the thread when the game is paused or finished
    public void stopGameThread() {
        GAME_STATUS = GAME_PAUSED;
        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
        }
    }

    // Function to restart the game thread
    public void startGameThread() {
        GAME_STATUS = GAME_STARTED;
        if (gameThread != null && !gameThread.isAlive()) {
            gameThread = new Thread(() -> {
                try {
                    while (GAME_STATUS == GAME_STARTED) {
                        moveTetrominoDown();
                        Thread.sleep(DROP_DELAY);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            gameThread.start();
        }
    }


    // Key binding setup
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
        actions.put("rotate", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotateTetromino();
            }
        });

        // Bind keys to actions
        inputMap.put(KeyStroke.getKeyStroke("P"), "pause");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke("UP"), "rotate");

        actionMap.put("pause", actions.get("pause"));
        actionMap.put("moveLeft", actions.get("moveLeft"));
        actionMap.put("moveRight", actions.get("moveRight"));
        actionMap.put("moveDown", actions.get("moveDown"));
        actionMap.put("rotate", actions.get("rotate"));
    }

    // Function to rotate the current tetromino
    public void rotateTetromino() {
        if (GAME_STATUS == GAME_STARTED) {
            if (currentTetromino != null) {
                currentTetromino.rotate();  // Rotate the Tetromino

                // Check if the rotated Tetromino collides with the grid or placed blocks
                if (!canMoveTo(currentTetromino, currentTetromino.getX(), currentTetromino.getY())) {
                    currentTetromino.rotateBack();  // Undo the rotation if it collides
                }

                repaint();  // Redraw the game field with the rotated tetromino
            }
        }
    }


    // Function to move tetromino left
    public void moveTetrominoLeft() {
        if (GAME_STATUS == GAME_STARTED) {
            if (currentTetromino != null && canMoveTo(currentTetromino, currentTetromino.getX() - 1, currentTetromino.getY())) {
                currentTetromino.setX(currentTetromino.getX() - 1);
                repaint(); // Request to redraw the game field with the updated position
            }
        }
    }


    public void moveTetrominoRight() {
        if (GAME_STATUS == GAME_STARTED) {
            if (currentTetromino != null && canMoveTo(currentTetromino, currentTetromino.getX() + 1, currentTetromino.getY())) {
                currentTetromino.setX(currentTetromino.getX() + 1);
                repaint(); // Request to redraw the game field with the updated position
            }
        }
    }


    private boolean canMoveTo(Tetromino tetromino, int newX, int newY) {
        for (Point block : tetromino.getBlocks()) {
            int newCol = block.x + newX;
            int newRow = block.y + newY;

            // Check if the new position is within the grid bounds
            if (newCol < 0 || newCol >= cols || newRow < 0 || newRow >= rows || grid[newRow][newCol] != null) {
                return false;
            }
        }
        return true;
    }


    // Change status to paused
    private void togglePause() {
        if (GAME_STATUS == GAME_STARTED) {
            stopGameThread(); // Stop the game thread when paused
        } else if (GAME_STATUS == GAME_PAUSED) {
            startGameThread(); // Restart the game thread when resumed
        }
    }


    private void placeTetromino() {
        Color tetrominoColor = currentTetromino.getColor();
        for (Point block : currentTetromino.getBlocks()) {
            int gridX = currentTetromino.getX() + block.x;
            int gridY = currentTetromino.getY() + block.y;
            grid[gridY][gridX] = tetrominoColor;
        }
        clearFullRows(); // Check for and clear full rows
        placedTetrominos.add(currentTetromino);
        spawnTetromino(); // Spawn a new Tetromino after placing the current one

        repaint();
    }


    private void clearFullRows() {
        for (int row = 0; row < rows; row++) {
            boolean isRowFull = true;
            // Check if the row is full
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == null) {
                    isRowFull = false;
                    break;
                }
            }


            // If the row is full, clear it and shift all rows above down
            if (isRowFull) {
                clearRow(row);
                shiftRowsDown(row);
                row--;  // Check the same row again after shifting, as it now contains the row above
            }
        }
    }


    private void clearRow(int row) {
        for (int col = 0; col < cols; col++) {
            grid[row][col] = null;  // Clear the row by setting all cells to null
        }
    }


    private void shiftRowsDown(int clearedRow) {
        // Shift all rows above the cleared row down by one
        for (int row = clearedRow; row > 0; row--) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = grid[row - 1][col];  // Move the above row down
            }
        }

        // Clear the top row after shifting to avoid leftover blocks
        for (int col = 0; col < cols; col++) {
            grid[0][col] = null;
        }
    }

    // Method to spawn a new Tetromino
    private void spawnTetromino() {
        TetrominoShapeDefiner randomShape = TetrominoShapeDefiner.getRandomShape();
        currentTetromino = new Tetromino(randomShape, cols / 2, 0);

        // Check if the new Tetromino can be placed at the top
        if (!canMoveTo(currentTetromino, currentTetromino.getX(), currentTetromino.getY())) {
            gameOver(); // End the game if the new Tetromino cannot be placed
        }
    }

    private void gameOver() {
        GAME_STATUS = GAME_FINISHED;
        stopGameThread(); // Stop the game thread
        JOptionPane.showMessageDialog(this, "Game Over! The grid is full.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }


    private boolean canMoveDown(Tetromino tetromino) {
        for (Point block : tetromino.getBlocks()) {
            int newX = tetromino.getX() + block.x;
            int newY = tetromino.getY() + block.y + 1;

            // Check if it hits the bottom of the grid
            if (newY >= rows) {
                return false;
            }

            // Check if it collides with any placed Tetromino
            if (grid[newY][newX] != null) {
                return false;
            }
        }
        return true;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        generateCells(g);

        // Draw placed blocks based on the grid state
        int cellSize = Math.min(getWidth() / cols, getHeight() / rows);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] != null) {  // If the cell is occupied, draw a block with the stored color
                    g.setColor(grid[row][col]);
                    g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                }
            }
        }

        // Draw the current tetromino
        if (currentTetromino != null) {
            currentTetromino.draw(g, cellSize);
        }

        if (GAME_STATUS == GAME_PAUSED) {
            showPaused(g);
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
