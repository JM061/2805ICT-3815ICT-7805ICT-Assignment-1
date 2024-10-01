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

    // Implement the compareTo method to sort by score in descending order
    @Override
    public int compareTo(UserScore other) {
        return Integer.compare(other.score, this.score); // Descending order
    }

    @Override
    public String toString() {
        return username + ": " + score + "Points, Level" + level;
    }
}
