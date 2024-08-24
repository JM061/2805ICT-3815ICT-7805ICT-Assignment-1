package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GameField extends JPanel {
    private int[][] grid;
    private int rows;
    private int cols;
    private final int GAME_STARTED = 1;
    private final int GAME_PAUSED = 2;
    private final int GAME_FINISHED = 3;
    public int GAME_STATUS = GAME_STARTED;


    public GameField(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new int[rows][cols];
        this.setPreferredSize(new Dimension(251, 501)); // Set preferred size
        this.setBackground(Color.WHITE);

        setFocusable(true);
        requestFocusInWindow();
        setupKeyListner();


    }

    private void setupKeyListner(){
        addKeyListener(new KeyListener() {


            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_P) {
                    togglePause();
                }else if (GAME_STATUS == GAME_STARTED){
                    switch (key){
                        case KeyEvent.VK_LEFT:
                            moveTetrominoLeft();
                            break;
                        case KeyEvent.VK_RIGHT:
                            moveTetrominoRight();
                            break;
                        case KeyEvent.VK_DOWN:
                            moveTetrominoDonw();
                            break;
                    }
                }

            }
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }




    public int[][] getGrid() {
        return grid;
    }

    public void setBlock(int row, int col, int value) {
        grid[row][col] = value;
    }

    public void moveTetrominoLeft(){
        System.out.println("LEFT KEY PRESSED");
    }

    public void moveTetrominoRight(){
        System.out.println("RIGHT KEY PRESSED");
    }

    public void moveTetrominoDonw(){
        System.out.println("DONW KEY PRESSED");
    }




    // private int checkFullRows(){}
    //
    //
    //
    //

    //private boolean checkGameOver(){}
    //if currentShape position == x=0 y=0 a
    //switch status to GAME_END
    //
    //

    private void togglePause() {
        if (GAME_STATUS == GAME_STARTED) {
            GAME_STATUS = GAME_PAUSED;
            System.out.println("GAME PAUSED" + GAME_STATUS);
        } else if (GAME_STATUS == GAME_PAUSED) {
            GAME_STATUS = GAME_STARTED;
            System.out.println("GAME RUNNNING" + GAME_STATUS);
        }
    }




    //change status to paused























    // function to change state to game over
    //


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        if (GAME_STATUS == 1){
            generateCells(g);
            System.out.println("GAME STATUS:" + " " + GAME_STATUS);
        }





    }

    private void generateCells(Graphics g) {
        // Calculate cell size based on the component's current size
        int cellSize = Math.min(getWidth() / cols, getHeight() / rows);

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                // Draw grid lines
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    }
}