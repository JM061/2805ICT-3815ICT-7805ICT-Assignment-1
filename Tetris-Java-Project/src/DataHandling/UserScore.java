package DataHandling;

public class UserScore implements Comparable<UserScore> {
    public String username;
    public int score;
    public int level;

    public UserScore(String username, int score, int level) {
        this.username = username;
        this.score = score;
        this.level = level;
    }


    public String getUsername() {
        return username; // Optional: Add a getter for username if needed
    }

    public int getScore() {
        return score; // This method should exist
    }

    public int getLevel() {
        return level; // This method should exist
    }


    @Override
    public int compareTo(UserScore other) {
        return Integer.compare(other.score, this.score); // Descending order
    }

    @Override
    public String toString() {
        return username + ": " + score + "Points, Level" + level;
    }
}
