package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Tetromino {
    private Color color;
    private ArrayList<Point> blocks;
    private int x;
    private int y;

    public Tetromino(TetrominoShapeDefiner shape, int x, int y) {
        this.color = shape.getColor();
        this.blocks = new ArrayList<>();
        int[][] shapeArray = shape.getShape();
        for (int row = 0; row < shapeArray.length; row++) {
            for (int col = 0; col < shapeArray[row].length; col++) {
                if (shapeArray[row][col] == 1) {
                    blocks.add(new Point(col, row));
                }
            }
        }
        this.x = x;
        this.y = y;
    }


    //gets colour of tetromino when generating the shape
    public Color getColor() {
        return color;
    }

    //gets x coordinate of tetromino when generating
    public int getX() {
        return x;
    }
    //gets y coordinate of tetromino when generating
    public int getY() {
        return y;
    }

    //Sets x coordinate of tetromino
    public void setX(int x) {
        this.x = x;
    }
    //Sets y coordinate of tetromino
    public void setY(int y) {
        this.y = y;
    }

    // draws the tetromino with predetermined cell size to match grid size
    public void draw(Graphics g, int cellSize) {
        g.setColor(color);
        for (Point block : blocks) {
            int drawX = (x + block.x) * cellSize;
            int drawY = (y + block.y) * cellSize;
            g.fillRect(drawX, drawY, cellSize, cellSize);
        }
    }
}
