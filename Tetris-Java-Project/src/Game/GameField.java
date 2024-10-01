package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import DataHandling.ScoreHandler;
import DataHandling.ScoreHandler.*;
import DataHandling.UserScore;
import com.google.gson.JsonObject;


public class GameField extends JPanel {
    private final Color[][] grid;
    private int rows;
    private int cols;
    private Tetromino currentTetromino;
    private ArrayList<Tetromino> placedTetrominos; // List to store placed tetrominos
    private final int GAME_PRESTART = 0;
    private final int GAME_STARTED = 1;
    private final int GAME_PAUSED = 2;
    private final int GAME_FINISHED = 3;
    public int GAME_STATUS = GAME_PRESTART;

    private Map<String, Action> actions;
    private Timer timer;
    private static final int DROP_DELAY = 500; // delay in milliseconds

    private int score = 0; //sets default score of 0
    private int rowsCleared = 0; // Total rows cleared in the game
    private int level = 1;

    public GameField(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Color[rows][cols];  // Store colors instead of integers
        this.setPreferredSize(new Dimension(251, 501)); // Set preferred size
        this.setBackground(Color.WHITE);
        this.placedTetrominos = new ArrayList<>(); // Ensure it's initialized here
        setFocusable(true);
        requestFocusInWindow();//sets focus on window to allow keyboard inputs
        setupKeyBindings();//function to setup keybindings

        // Generates the first tetromino on page load
        TetrominoShapeDefiner randomShape = TetrominoShapeDefiner.getRandomShape();
        currentTetromino = new Tetromino(randomShape, cols / 2, 0);


        gameStart();
        if(GAME_STATUS == GAME_STARTED) {
            timer = new Timer(DROP_DELAY, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moveTetrominoDown();

                }
            });
            timer.start(); // Start the timer when the game field is created
            //System.out.println("GAME STARTED");
        }

    }


    public void gameStart(){
        GAME_STATUS = GAME_STARTED;
        System.out.println("GAME STARTED. STATUS:"  + GAME_STATUS);
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

    //moves tetromino right
    public void moveTetrominoRight() {
        if (GAME_STATUS == GAME_STARTED) {
            if (currentTetromino != null && canMoveTo(currentTetromino, currentTetromino.getX() + 1, currentTetromino.getY())) {
                currentTetromino.setX(currentTetromino.getX() + 1);
                repaint(); // Request to redraw the game field with the updated position
            }
        }
    }

//moves tetromino down
    public void moveTetrominoDown() {
        if (GAME_STATUS == GAME_STARTED) {
            if (canMoveDown(currentTetromino)) {
                currentTetromino.moveDown();
            } else {
                // Place the tetromino and generate a new one
                placeTetromino();
            }
            repaint();
        }
    }

    //checks if the tetromino can be moved
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
            GAME_STATUS = GAME_PAUSED;
            timer.stop(); // Stop the timer when the game is paused
        } else if (GAME_STATUS == GAME_PAUSED) {
            GAME_STATUS = GAME_STARTED;
            timer.start(); // Restart the timer when the game resumes
        }
        repaint();
    }

    private void placeTetromino() {
        if (GAME_STATUS == GAME_STARTED) {
            Color tetrominoColor = currentTetromino.getColor();  // Get the color of the current Tetromino
            for (Point block : currentTetromino.getBlocks()) {
                int gridX = currentTetromino.getX() + block.x;
                int gridY = currentTetromino.getY() + block.y;

                // Store the color of the block in the grid
                grid[gridY][gridX] = tetrominoColor;
            }

            // Clear any full rows after placing the Tetromino
            clearFullRows();
            placedTetrominos.add(currentTetromino);
            spawnTetromino();

            repaint();  // Repaint the game field after updating the grid
        }
    }

    //checks if rows are full and clears rows if they are
    //calculates score based on number of rows cleared
    private void clearFullRows() {
        int rowsClearedInBatch = 0; // Tracks how many rows are cleared in this iteration
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
                rowsClearedInBatch++;
                row--;
            }
        }
        // Use switch case to determine how many rows have been cleared
        switch (rowsClearedInBatch) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 600;
                break;
            case 4:
                score += 1000;
                break;
        }
        // Add to total rows cleared
        rowsCleared += rowsClearedInBatch;

        // Check if the player advances a level (every 10 rows cleared)
        if (rowsCleared >= level * 10) {
            levelUp();
        }
        // Output score and level information
        System.out.println("Rows Cleared: " + rowsClearedInBatch + ", Total Score: " + score + ", Level: " + level);
    }


    private void levelUp() {
        level++;
        System.out.println("Level Up! Now at Level " + level);
    }

    private void clearRow(int row) {
        for (int col = 0; col < cols; col++) {
            grid[row][col] = null;  // Clear the row by setting all cells to null
        }
    }

    //move rows down when the row is cleared
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
    public void spawnTetromino() {
        if(GAME_STATUS == GAME_STARTED) {
            TetrominoShapeDefiner randomShape = TetrominoShapeDefiner.getRandomShape();
            currentTetromino = new Tetromino(randomShape, cols / 2, 0);

            // Check if the new Tetromino can be placed at the top
            if (!canMoveTo(currentTetromino, currentTetromino.getX(), currentTetromino.getY())) {
                gameOver();  // End the game if the new Tetromino cannot be placed
            }
        }
    }

    private void gameOver() {
        GAME_STATUS = GAME_FINISHED;
        timer.stop();  // Stop the game loop
        System.out.println("Current Game Status: "+GAME_STATUS);
        JOptionPane.showMessageDialog(this, "Game Over! The grid is full.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        userScoreEntry();
        clearGrid();   // Clear the entire grid
    }

    private boolean isScoreInTopTen(int score) {

        if(score != 0) {
            List<UserScore> topScores = ScoreHandler.loadTopScores();
            return topScores.size() < 10 || score > topScores.get(topScores.size() - 1).score;
        } else {
            return false;

        }
    }


    private void userScoreEntry() {
        // Handle end game logic
        if (isScoreInTopTen(score)) {
            String username = null;

            while (username == null || username.trim().isEmpty()) {
                try {
                    username = JOptionPane.showInputDialog("Enter your name to save your score:");

                    if (username == null || username.trim().isEmpty()) {
                        // Display an error message if the input is invalid
                        JOptionPane.showMessageDialog(null, "Please enter a valid name.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    // Handle any unexpected exceptions
                    JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            // After getting a valid username, add the score
            ScoreHandler.addNewScore(username, score, level);
        }
    }


    //checks if the tetromino can be moved down
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

    public void clearGrid() {
        // Clear the grid by setting all cells to null
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = null;
            }
        }
        score = 0; // Reset score
        placedTetrominos.clear(); // Clear placed tetrominos
        repaint();  // Repaint the game field to reflect changes
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
