package Game;

public interface GameObserver {
    void updateScore(int score);
    void updateLevel(int level);
    void updateRowsCleared(int rowsCleared);

}