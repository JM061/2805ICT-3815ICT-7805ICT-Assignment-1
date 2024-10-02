import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.List;

public class ScoreHandlerTest {

    private File tempScoreFile;

    @Before
    public void setUp() throws Exception {
        // Create a temporary file for testing
        tempScoreFile = File.createTempFile("testScores", ".json");
        ScoreHandler.SCORE_FILE = tempScoreFile.getAbsolutePath(); // Override the path
    }

    @After
    public void tearDown() throws Exception {
        // Clean up by deleting the temporary file after each test
        if (tempScoreFile.exists()) {
            tempScoreFile.delete();
        }
    }

    @Test
    public void testLoadScoresWhenFileNotExists() {
        // If file does not exist, the method should create a new empty JsonObject
        JsonObject scores = ScoreHandler.loadScores();
        assertTrue(scores.isJsonObject());
        assertEquals(0, scores.size()); // Should be empty
    }

    @Test
    public void testAddNewScore() {
        // Add a new score and check if it's saved correctly
        String username = "TestUser";
        int score = 100;
        int level = 5;

        // Add the score
        ScoreHandler.addNewScore(username, score, level);

        // Load the top scores and check if the new score is added
        List<UserScore> topScores = ScoreHandler.loadTopScores();
        assertFalse(topScores.isEmpty());
        assertEquals(username, topScores.get(0).username);
        assertEquals(score, topScores.get(0).score);
        assertEquals(level, topScores.get(0).level);
    }

    @Test
    public void testSaveTopScores() {
        // Create a UserScore and save it
        UserScore userScore = new UserScore("Player1", 150, 3);
        List<UserScore> topScores = List.of(userScore);

        // Save the top scores
        ScoreHandler.saveTopScores(topScores);

        // Load them back to check if they were saved correctly
        List<UserScore> loadedScores = ScoreHandler.loadTopScores();
        assertEquals(1, loadedScores.size());
        assertEquals("Player1", loadedScores.get(0).username);
        assertEquals(150, loadedScores.get(0).score);
        assertEquals(3, loadedScores.get(0).level);
    }

    @Test
    public void testRemoveScores() {
        // Add a score
        ScoreHandler.addNewScore("PlayerToDelete", 200, 4);

        // Load the scores to confirm the score exists
        List<UserScore> scores = ScoreHandler.loadTopScores();
        assertEquals(1, scores.size());

        // Remove all scores
        ScoreHandler.removeScores();

        // Load the scores again to verify they are deleted
        scores = ScoreHandler.loadTopScores();
        assertTrue(scores.isEmpty());
    }

    @Test
    public void testTopScoreSorting() {
        // Add multiple scores in random order
        ScoreHandler.addNewScore("Player1", 200, 5);
        ScoreHandler.addNewScore("Player2", 100, 3);
        ScoreHandler.addNewScore("Player3", 150, 4);

        // Load and check if they are sorted by score
        List<UserScore> topScores = ScoreHandler.loadTopScores();
        assertEquals("Player1", topScores.get(0).username); // Highest score first
        assertEquals("Player3", topScores.get(1).username); // Second highest score
        assertEquals("Player2", topScores.get(2).username); // Lowest score last
    }
}
