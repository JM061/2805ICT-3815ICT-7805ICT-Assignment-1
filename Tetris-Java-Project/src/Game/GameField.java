package Game;

import javax.swing.*;
import java.awt.*;

public class GameField extends JPanel {
    private int[][] grid;
    private int rows;
    private int cols;

    public GameField(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new int[rows][cols];
        this.setPreferredSize(new Dimension(250, 500)); // Set preferred size
        this.setBackground(Color.WHITE);
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setBlock(int row, int col, int value) {
        grid[row][col] = value;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

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