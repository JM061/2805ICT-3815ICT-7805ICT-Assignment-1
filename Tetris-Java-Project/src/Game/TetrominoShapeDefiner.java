package Game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum TetrominoShapeDefiner {
    //used to create each tetromino shape
    //Shape of tetromino (I,K,L,O,S,T,Z)
    //Colour of each tetromino
    //
    I(Color.CYAN, new int[][]{{1, 1, 1, 1}}),
    J(Color.BLUE, new int[][]{{0, 0, 1}, {1, 1, 1}}),
    L(Color.ORANGE, new int[][]{{1, 0, 0}, {1, 1, 1}}),
    O(Color.YELLOW, new int[][]{{1, 1}, {1, 1}}),
    S(Color.GREEN, new int[][]{{0, 1, 1}, {1, 1, 0}}),
    T(Color.PINK, new int[][]{{0, 1, 0}, {1, 1, 1}}),
    Z(Color.RED, new int[][]{{1, 1, 0}, {0, 1, 1}});

    private static List<Integer> shapes;
    private final Color color;
    private final int[][] shape;
    private static Random random = new Random();

    TetrominoShapeDefiner(Color color, int[][] shape) {
        this.color = color;
        this.shape = shape;
    }

    public Color getColor() {
        return color;
    }

    public int[][] getShape() {
        return shape;
    }

    public static TetrominoShapeDefiner getRandomShape() {
        return values()[random.nextInt(values().length)];
    }

    private static synchronized void generateShapes() {
        if (shapes == null) {
            shapes = new ArrayList<>();
        }
        for (int i = 0; i < 10; ++i) { //randomly selects tetromino to generate
            shapes.add(random.nextInt(values().length));
        }
    }
}