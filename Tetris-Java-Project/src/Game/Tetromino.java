package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Tetromino {
    private final Color color;
    private final ArrayList<Point> blocks;
    private int[][] shape;
    private int x;
    private int y;


    public Color getColor(){
        return color;
    }
    //initializes tetromino shape and x,y location
    public Tetromino(TetrominoShapeDefiner shape, int x, int y) {
        this.color = shape.getColor();
        this.shape = shape.getShape();
        this.blocks = new ArrayList<>();
        this.x = x;
        this.y = y;

        updateBlockFromShape();
    }



    public void rotate() {
        int[][] rotatedShape = new int[shape[0].length][shape.length];

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                rotatedShape[col][shape.length - 1 - row] = shape[row][col];
            }
        }

        shape = rotatedShape;
        updateBlockFromShape();
    }

    public void updateBlockFromShape(){
        blocks.clear();
        for(int row = 0; row < shape.length; row++) {
            for(int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] == 1) {
                    blocks.add(new Point(col, row));

                }
            }
        }
    }
    // Rotate the Tetromino 90 degrees counterclockwise (undo rotation)
    public void rotateBack() {
        // Rotate three more times to undo a 90-degree clockwise rotation
        rotate();
        rotate();
        rotate();
    }


    // Getter for x-coordinate
    public int getX() {
        return x;
    }

    // Setter for x-coordinate
    public void setX(int x) {
        this.x = x;
    }

    // Getter for y-coordinate
    public int getY() {
        return y;
    }

    // Setter for y-coordinate
    public void setY(int y) {
        this.y = y;
    }

    // Getter for blocks
    public ArrayList<Point> getBlocks() {
        return blocks;
    }
    public void moveDown(){
        this.y++;
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