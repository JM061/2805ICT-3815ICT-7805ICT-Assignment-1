package screens;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import DataHandling.UserScore;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HighScoreScreenTest {

    private TetrisApp app;  // Declare TetrisApp instance
    private HighScoreScreen highScoreScreen; // Declare HighScoreScreen instance

    @BeforeEach
    void setUp() {
        app = new TetrisApp(); // Initialize the TetrisApp instance
        highScoreScreen = new HighScoreScreen(app); // Pass it to HighScoreScreen
    }

    @Test
    void testDisplay() {
        // Test the displayScores method to ensure scores are displayed correctly
        List<UserScore> scores = highScoreScreen.loadUserScores();

        System.out.println(scores);
        assertNotNull(scores, "Scores list should not be null.");
    }

    @Test
    void testLoad() {
        // Test loadUserScores method to ensure it loads scores correctly,
        // checking for non-negative values and proper sorting
        List<UserScore> scores = highScoreScreen.loadUserScores();

        assertNotNull(scores, "Scores list should not be null.");

        // Check that there are no negative scores or levels
        for (UserScore score : scores) {
            assertTrue(score.getScore() >= 0, "Score should be non-negative.");
            assertTrue(score.getLevel() >= 0, "Level should be non-negative.");
        }

        // Check that scores are in descending order
        for (int i = 0; i < scores.size() - 1; i++) {
            assertTrue(scores.get(i).getScore() >= scores.get(i + 1).getScore(),
                    "Scores should be in descending order.");
        }
    }
}
