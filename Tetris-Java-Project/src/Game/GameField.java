package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Game.GameObserver;
import Game.Tetromino;
import screens.GameDisplay;

public class GameField extends JPanel {
    private List<GameObserver> observers = new ArrayList<>();
    private final Color[][] grid;
    private int rows;
    private int cols;
    private Tetromino currentTetromino;
    private ArrayList<Tetromino> placedTetrominos;
    private final int GAME_PRESTART = 0;
    private final int GAME_STARTED = 1;
    private final int GAME_PAUSED = 2;
    private final int GAME_FINISHED = 3;
    public int GAME_STATUS = GAME_PRESTART;

    private Timer timer;
    private static final int DROP_DELAY = 500; // delay in milliseconds
    private int score = 0;
    private int rowsCleared = 0;
    private int level = 1;

    // Declare and initialize actions map
    private Map<String, Action> actions = new HashMap<>();

    public GameField(int rows, int cols, GameDisplay gameDisplay, boolean isPlayerOne) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Color[rows][cols];  // Store colors instead of integers
        this.setPreferredSize(new Dimension(251, 501)); // Set preferred size
        this.setBackground(Color.WHITE);
        this.placedTetrominos = new ArrayList<>(); // Ensure it's initialized here
        setFocusable(true);
        requestFocusInWindow(); // Sets focus on window to allow keyboard inputs
        setupKeyBindings(isPlayerOne); // Function to set up key bindings for player 1 or player 2

        // Generates the first tetromino on page load
        TetrominoShapeDefiner randomShape = TetrominoShapeDefiner.getRandomShape();
        currentTetromino = new Tetromino(randomShape, cols / 2, 0);

        gameStart();
        if (GAME_STATUS == GAME_STARTED) {
            timer = new Timer(DROP_DELAY, e -> moveTetrominoDown());
            timer.start(); // Start the timer when the game field is created
        }
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.updateScore(score);
            observer.updateLevel(level);
            observer.updateRowsCleared(rowsCleared);
        }
    }

    public void gameStart() {
        GAME_STATUS = GAME_STARTED;
        System.out.println("GAME STARTED. STATUS:" + GAME_STATUS);
        if (GAME_STATUS == GAME_STARTED) {
            timer = new Timer(DROP_DELAY, e -> moveTetrominoDown());
            timer.start(); // Start the timer when the game field is created
        }
    }

    // Key binding setup for Player 1 (Arrows) and Player 2 (WASD)
    private void setupKeyBindings(boolean isPlayerOne) {
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        if (isPlayerOne) {
            // Player 1: Arrow keys
            actions.put("moveLeft", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moveTetrominoLeft();
                }
            });
            inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
            actionMap.put("moveLeft", actions.get("moveLeft"));

            actions.put("moveRight", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moveTetrominoRight();
                }
            });
            inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
            actionMap.put("moveRight", actions.get("moveRight"));

            actions.put("moveDown", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moveTetrominoDown();
                }
            });
            inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
            actionMap.put("moveDown", actions.get("moveDown"));

            actions.put("rotate", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rotateTetromino();
                }
            });
            inputMap.put(KeyStroke.getKeyStroke("UP"), "rotate");
            actionMap.put("rotate", actions.get("rotate"));

        } else {
            // Player 2: WASD keys
            actions.put("moveLeft", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moveTetrominoLeft();
                }
            });
            inputMap.put(KeyStroke.getKeyStroke("A"), "moveLeft");
            actionMap.put("moveLeft", actions.get("moveLeft"));

            actions.put("moveRight", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moveTetrominoRight();
                }
            });
            inputMap.put(KeyStroke.getKeyStroke("D"), "moveRight");
            actionMap.put("moveRight", actions.get("moveRight"));

            actions.put("moveDown", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moveTetrominoDown();
                }
            });
            inputMap.put(KeyStroke.getKeyStroke("S"), "moveDown");
            actionMap.put("moveDown", actions.get("moveDown"));

            actions.put("rotate", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rotateTetromino();
                }
            });
            inputMap.put(KeyStroke.getKeyStroke("W"), "rotate");
            actionMap.put("rotate", actions.get("rotate"));
        }
    }

    // Toggle game pause
    private void togglePause() {
        if (GAME_STATUS == GAME_STARTED) {
            GAME_STATUS = GAME_PAUSED;
            timer.stop(); // Stop the timer when the game is paused
        } else if (GAME_STATUS == GAME_PAUSED) {
            GAME_STATUS = GAME_STARTED;
            timer.start(); // Restart the timer when the game resumes
        }
        repaint();
    }

    // Function to rotate the current tetromino
    public void rotateTetromino() {
        if (GAME_STATUS == GAME_STARTED) {
            if (currentTetromino != null) {
                currentTetromino.rotate();

                // Check if the rotated Tetromino collides with the grid or placed blocks
                if (!canMoveTo(currentTetromino, currentTetromino.getX(), currentTetromino.getY())) {
                    currentTetromino.rotateBack();
                }

                repaint(); // Redraw the game field with the rotated tetromino
            }
        }
    }

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
                repaint();
            }
        }
    }

    public void moveTetrominoDown() {
        if (GAME_STATUS == GAME_STARTED) {
            if (canMoveDown(currentTetromino)) {
                currentTetromino.moveDown();
            } else {
                placeTetromino();
            }
            repaint();
        }
    }

    // Check if tetromino can move to new position
    private boolean canMoveTo(Tetromino tetromino, int newX, int newY) {
        for (Point block : tetromino.getBlocks()) {
            int newCol = block.x + newX;
            int newRow = block.y + newY;

            if (newCol < 0 || newCol >= cols || newRow < 0 || newRow >= rows || grid[newRow][newCol] != null) {
                return false;
            }
        }
        return true;
    }

    private void placeTetromino() {
        if (GAME_STATUS == GAME_STARTED) {
            Color tetrominoColor = currentTetromino.getColor();
            for (Point block : currentTetromino.getBlocks()) {
                int gridX = currentTetromino.getX() + block.x;
                int gridY = currentTetromino.getY() + block.y;
                grid[gridY][gridX] = tetrominoColor;
            }
            clearFullRows();
            placedTetrominos.add(currentTetromino);
            spawnTetromino();
            repaint();
        }
    }

    public void clearFullRows() {
        int rowsClearedInBatch = 0;
        for (int row = 0; row < rows; row++) {
            boolean isRowFull = true;
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == null) {
                    isRowFull = false;
                    break;
                }
            }
            if (isRowFull) {
                clearRow(row);
                shiftRowsDown(row);
                rowsClearedInBatch++;
                row--;
            }
        }

        switch (rowsClearedInBatch) {
            case 1 -> score += 100;
            case 2 -> score += 300;
            case 3 -> score += 600;
            case 4 -> score += 1000;
        }

        rowsCleared += rowsClearedInBatch;
        if (rowsCleared >= level * 10) {
            levelUp();
        }
        notifyObservers();
    }

    private void levelUp() {
        level++;
        notifyObservers();
    }

    private void clearRow(int row) {
        for (int col = 0; col < cols; col++) {
            grid[row][col] = null;
        }
    }

    private void shiftRowsDown(int clearedRow) {
        for (int row = clearedRow; row > 0; row--) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = grid[row - 1][col];
            }
        }
        for (int col = 0; col < cols; col++) {
            grid[0][col] = null;
        }
    }

    // Method to spawn a new tetromino at the top of the grid
    private void spawnTetromino() {
        TetrominoShapeDefiner randomShape = TetrominoShapeDefiner.getRandomShape();
        currentTetromino = new Tetromino(randomShape, cols / 2, 0);
        if (!canMoveTo(currentTetromino, currentTetromino.getX(), currentTetromino.getY())) {
            GAME_STATUS = GAME_FINISHED;
            System.out.println("GAME OVER. FINAL SCORE: " + score);
            timer.stop();
        }
    }

    private boolean canMoveDown(Tetromino tetromino) {
        return canMoveTo(tetromino, tetromino.getX(), tetromino.getY() + 1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw grid background
        g.setColor(Color.LIGHT_GRAY);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                g.drawRect(col * 25, row * 25, 25, 25);
            }
        }

        // Draw placed Tetrominos
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] != null) {
                    g.setColor(grid[row][col]);
                    g.fillRect(col * 25, row * 25, 25, 25);
                }
            }
        }

        // Draw the current tetromino
        if (currentTetromino != null) {
            currentTetromino.draw(g, 25); // Assuming a 25x25 grid size
        }
    }

    public void resetGame() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = null;
            }
        }
        score = 0;
        rowsCleared = 0;
        level = 1;
        notifyObservers();
        spawnTetromino();
        GAME_STATUS = GAME_STARTED;
        timer.restart();
        repaint();
    }
}
