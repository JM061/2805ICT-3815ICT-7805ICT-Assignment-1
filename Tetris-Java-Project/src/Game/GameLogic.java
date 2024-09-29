package Game;

import java.util.ArrayList;
import java.awt.Point;

public class GameLogic {
    // Game status constants
    private static final int GAME_PRESTART = 0;
    private static final int GAME_STARTED = 1;
    private static final int GAME_PAUSED = 2;
    private static final int GAME_FINISHED = 3;

    // Variable to track the current game status
    private int GAME_STATUS = GAME_PRESTART;

    private int[][] grid;
    private final int rows = 20;
    private final int cols = 10;
    private Tetromino currentTetromino;  // The active Tetromino that the player is controlling
    private ArrayList<Tetromino> placedTetrominos;  // List to store placed Tetrominoes

    public GameLogic() {
        // Initialize the grid with 0s (empty cells)
        grid = new int[rows][cols];
        placedTetrominos = new ArrayList<>();  // Initialize the list of placed Tetrominoes
    }

    // Check for collision at the top of the grid
    public boolean checkTopCollision(Tetromino tetromino) {
        for (Point block : tetromino.getBlocks()) {
            int gridX = tetromino.getX() + block.x;
            int gridY = tetromino.getY() + block.y;

            // Ensure we are within grid bounds and check for collision
            if (gridY >= 0 && gridY < rows && gridX >= 0 && gridX < cols) {
                if (grid[gridY][gridX] != 0) {
                    return true;  // Collision detected
                }
            }
        }
        return false;  // No collision
    }

    // Method to simulate game over
    public void gameOver() {
        GAME_STATUS = GAME_FINISHED;
        System.out.println("Game Over!");
    }

    // Method to rotate the current Tetromino
    public void rotateTetromino() {
        if (GAME_STATUS == GAME_STARTED) {
            currentTetromino.rotate();  // Rotate the Tetromino

            // Check if the rotated Tetromino collides with the grid or other blocks
            if (!canMoveTo(currentTetromino, currentTetromino.getX(), currentTetromino.getY())) {
                currentTetromino.rotateBack();  // Undo rotation if it collides
            }
        }
    }

    // Check if the Tetromino can move to the specified position
    public boolean canMoveTo(Tetromino tetromino, int newX, int newY) {
        if(GAME_STATUS == GAME_STARTED){
            for (Point block : tetromino.getBlocks()) {
                int gridX = newX + block.x;
                int gridY = newY + block.y;

                // Check if the new position is outside the grid bounds
                if (gridX < 0 || gridX >= cols || gridY < 0 || gridY >= rows) {
                    return false;  // Out of bounds
                }

                // Check if the new position collides with other blocks in the grid
                if (grid[gridY][gridX] != 0) {
                    return false;  // Collision detected
                }
            }
        }
        return true;  // No collision, movement is possible
    }


    // Method to spawn a new Tetromino and check for game over condition
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

    // Place the Tetromino and update the grid
    public void placeTetromino() {
        if (GAME_STATUS == GAME_STARTED) {
            for (Point block : currentTetromino.getBlocks()) {
                int gridX = currentTetromino.getX() + block.x;
                int gridY = currentTetromino.getY() + block.y;

                // Mark the grid cell as occupied
                grid[gridY][gridX] = 1;
            }

            // Spawn a new Tetromino after placing the current one
            spawnTetromino();
        }
    }
}
