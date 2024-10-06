package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Game.GameObserver;
import Game.Tetromino;
import screens.GameDisplay;

import Sounds.SoundHandler;
import Sounds.bgMusicPlayer;

import DataHandling.ConfigHandler;
import DataHandling.ScoreHandler;
import DataHandling.UserScore;


public class GameField extends JPanel {
    private List<GameObserver> observers = new ArrayList<>();
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

    private Timer timer;
    private static final int DROP_DELAY = 500; // delay in milliseconds

    private int score = 0; //sets default score of 0
    private int rowsCleared = 0; // Total rows cleared in the game
    private int level = 1;

    //
    static SoundHandler soundManager;
    private bgMusicPlayer musicPlayer;
    private Thread musicThread;

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

        //initialise music player (Singleton Instance)
        musicPlayer = bgMusicPlayer.getInstance("/Sounds/backgroundMusic.mp3"); // Get the singleton instance
        startMusic();

        String[] soundFiles = {
                "Sounds/clear_row_sound.wav",
                "Sounds/game_over_sound.wav",
                "Sounds/level_up_sound.wav",
                "Sounds/place_tetromino.wav"
        };
        soundManager = SoundHandler.getInstance(soundFiles);

        gameStart();
        if(GAME_STATUS == GAME_STARTED) {
            timer = new Timer(DROP_DELAY, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moveTetrominoDown();
                }
            });
            timer.start(); // Start the timer when the game field is created
        }

    }

    //method to start music when page loads
    public void startMusic() {
        if (musicThread == null || !musicThread.isAlive()) {
            musicThread = new Thread(musicPlayer); // Create a new Thread
            if(ConfigHandler.getMusicStatus()){ //checks config file to start music
                musicThread.start(); // Start the thread
                System.out.println(ConfigHandler.getMusicStatus() + "MUSIC STARTED FROM CONFIG");
            } else{
                System.out.println(ConfigHandler.getMusicStatus() + "MUSIC NOT STARTED FROM CONFIG");
            }
           System.out.println("Music thread started.");
        } else {
            System.out.println("Music thread is already running.");
        }
    }

    public void stopMusic(){
        if (musicThread != null && musicThread.isAlive()) {
            try {
                musicThread.join(); // Wait for the music thread to finish
            } catch (InterruptedException e) {
                System.out.println("Error while stopping the music thread: " + e.getMessage());
            }
        }
    }


    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    //update score level and rows cleared in observer for data display
    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.updateScore(score);
            observer.updateLevel(level);
            observer.updateRowsCleared(rowsCleared);
        }
    }
    //change status of game when page loads
    public void gameStart(){
        GAME_STATUS = GAME_STARTED;
        System.out.println("GAME STARTED. STATUS:"  + GAME_STATUS);
    }


    // Key binding setup for Player 1 (Arrows) and Player 2 (WASD)
    private void setupKeyBindings(boolean isPlayerOne) {
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();


        if (isPlayerOne) {
            //toggle music on/off
            inputMap.put(KeyStroke.getKeyStroke("M"), "toggleMusic");
            actionMap.put("toggleMusic", actions.get("toggleMusic"));
            actions.put("toggleMusic", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toggleMusic();
                }
            });

            //toggle sound effects on/off
            inputMap.put(KeyStroke.getKeyStroke("S"), "toggleSound");
            actionMap.put("toggleSound", actions.get("toggleSound"));
            actions.put("toggleSound", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toggleSound();
                }
            });


            //pause game
            inputMap.put(KeyStroke.getKeyStroke("P"), "pause");
            actionMap.put("pause", actions.get("pause"));
            actions.put("pause", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    togglePause();
                }
            });


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

    // Check if tetromino can move to new position
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
            soundManager.playSoundEffect("Sounds/place_tetromino.wav");
            repaint();  // Repaint the g
            // ame field after updating the grid
        }
    }

    //checks if rows are full and clears rows if they are
    //calculates score based on number of rows cleared
    public void clearFullRows() {
        int scoreIncrement = 0;
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
                scoreIncrement += 100;
                break;
            case 2:
                scoreIncrement += 300;
                break;
            case 3:
                scoreIncrement += 600;
                break;
            case 4:
                scoreIncrement += 1000;
                break;


        }
        if(scoreIncrement >0){
            score += scoreIncrement;
            soundManager.playSoundEffect("Sounds/clear_row_sound.wav");
            notifyObservers();
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


    //function to increase level
    private void levelUp() {
        level++;
        notifyObservers();
        soundManager.playSoundEffect("Sounds/level_up_sound.wav");
        System.out.println("Level Up! Now at Level " + level);
    }

    //clears row
    private void clearRow(int row) {
        for (int col = 0; col < cols; col++) {
            grid[row][col] = null;  // Clear the row by setting all cells to null
        }
        notifyObservers();
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

    //function that runs when game finishes
    private void gameOver() {
        GAME_STATUS = GAME_FINISHED;
        timer.stop();  // Stop the game loop
        System.out.println("Current Game Status: "+GAME_STATUS);
        soundManager.playSoundEffect("Sounds/game_over_sound.wav");
        JOptionPane.showMessageDialog(this, "Game Over! The grid is full.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        userScoreEntry();
        musicPlayer.stop(); // Stop the music
        stopMusic();
        clearGrid();   // Clear the entire grid
    }

    //checks if users score is within the top 10 in the Scores File
    private boolean isScoreInTopTen(int score) {
        if(score != 0) {
            List<UserScore> topScores = ScoreHandler.loadTopScores();
            return topScores.size() < 10 || score > topScores.get(topScores.size() - 1).score;
        } else {
            return false;

        }
    }

    // Function used to promt user to enter their name if score is within top 10
    private void userScoreEntry() {
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

    //resets playing field
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

    //add code here to enable and disable sounds and music
    public void toggleMusic(){
        System.out.println("Toggled Music");
        if (musicPlayer.isPaused()) {
            musicPlayer.resume();
        } else {
            musicPlayer.pause();
        }
    }
    public void toggleSound() {
        soundManager.toggleSound();
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


    public int getScore(){
        System.out.println("current Score is: " + score);
        return score;

    }

    public int getLevel(){
        System.out.println("current Level is: " + level);
        return level;
    }

    public int getRowsRemoved(){
        System.out.println("Number of rows cleared:" + rowsCleared);
        return rowsCleared;
    }

    public void getupdateGameData(){
        int score = getScore();
        int level = getLevel();
        int rowsCleared = getRowsRemoved();
        //GameDisplay.updateGameDataDisplay();
        //GameInfoDisplay.updateGameData(this.level, this.score, 1, this.rowsCleared);
    }




}

