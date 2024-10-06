package Game;

import screens.GameDisplay;

public class TetrisFacade {
    private GameField gameField;
    private GameField gameField2;

    private GameInfoDisplay gameInfoDisplay;
    private GameObserver gameObserver;
    private GameSettings gameSettings;

    public TetrisFacade() {
        // Initialize components with default values
        int initialLevel = 1; // Default initial level
        int initialScore = 0;  // Default score
        int initialRowsCleared = 0;  // Default rows cleared

        // Pass the initial values to the GameInfoDisplay constructor
        gameInfoDisplay = new GameInfoDisplay(initialLevel, initialScore, initialLevel, initialRowsCleared);

        // Anonymous class for GameObserver since it's abstract
        gameObserver = new GameObserver() {
            @Override
            public void updateScore(int score) {
                // Implementation for updating score
                System.out.println("Score updated: " + score);
            }

            @Override
            public void updateLevel(int level) {
                // Implementation for updating level
                System.out.println("Level updated: " + level);
            }

            @Override
            public void updateRowsCleared(int rowsCleared) {
                // Implementation for updating rows cleared
                System.out.println("Rows cleared: " + rowsCleared);
            }
        };

        gameSettings = new GameSettings();
    }

    // Start a new game
    public void startGame(int rows, int cols, GameDisplay gameDisplay) {
        // Initialize GameField with rows, columns, and GameDisplay
        gameField = new GameField(rows, cols, gameDisplay, true);
        gameField2 = new GameField(rows, cols, gameDisplay, false);
        gameField.gameStart(); // Sets GAME_STATUS to GAME_STARTED and starts the timer inside GameField
        gameField.spawnTetromino();


        // Set up the game start logic
        gameField.gameStart(); // Sets GAME_STATUS to GAME_STARTED and starts the timer inside GameField
        gameField.spawnTetromino(); // Spawns the first Tetromino

        // Start the background music
        gameField.startMusic();

        // Reset game info display for a fresh game
        gameInfoDisplay.resetInfo();  // Resets score, level, and rows cleared

        // Set up the observer and add it to the game field to receive updates
        gameField.addObserver(gameObserver); // Add the GameObserver to be notified of updates
    }

    // Delegate movement to GameField
    //Player 1 Movement
    public void moveTetrominoLeft() {
        gameField.moveTetrominoLeft();
    }

    public void moveTetrominoRight() {
        gameField.moveTetrominoRight();
    }

    public void rotateTetromino() {
        gameField.rotateTetromino();
    }


    // Toggle Pause/Resume Game using existing togglePause method in GameField
    public void togglePauseGame() {
        gameField.togglePause();
    }

    // Game over handling
    public void endGame() {
        gameField.gameOver();  // Call GameField's gameOver() for all game over logic
     //   gameInfoDisplay.showGameOver(); // Display Game Over info on UI
        updateGameData();  // Update score, level, and rows cleared
        gameField.userScoreEntry(); // Delegate score entry handling to GameField

        // Optionally remove the observer at the end of the game
        gameField.removeObserver(gameObserver);
    }

    // Update game data (score, level, rows cleared) and apply settings
    public void applySettings(GameSettings newSettings) {
        this.gameSettings = newSettings;
        updateGameData();  // Update game data when new settings are applied
    }

    // Method to update game data (score, level, rows removed) and notify observers
    private void updateGameData() {
        gameField.getupdateGameData(); // Retrieves and updates the game data (score, level, rows)

        // Notify all observers about the updated game data
        gameField.notifyObservers();

        // Update GameInfoDisplay with the latest game data
        int score = gameField.getScore();
        int level = gameField.getLevel();
        int rowsRemoved = gameField.getRowsRemoved();

        gameInfoDisplay.updateScore(score);
        gameInfoDisplay.updateLevel(level);
        gameInfoDisplay.updateRowsCleared(rowsRemoved);
    }
}
