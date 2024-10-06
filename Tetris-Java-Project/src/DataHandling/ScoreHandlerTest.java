package DataHandling;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreHandlerTest {

    private static final String TEMP_SCORE_FILE = "tempGameScores.json"; // Temporary file for testing

    @BeforeEach
    void setUp() {
        // Create an empty file for testing
        try (FileWriter writer = new FileWriter(TEMP_SCORE_FILE)) {
            writer.write("{}");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Replace the SCORE_FILE path in ScoreHandler to use the temp file
        ScoreHandler.SCORE_FILE = TEMP_SCORE_FILE;
    }

    @AfterEach
    void tearDown() {
        // Clean up the temp file after each test
        File file = new File(TEMP_SCORE_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    //Should Save Scores Correctly (In Descending Order, No negative numbers)
    @Test
    void saveTopScores_Correctly() {
        // Prepare test data
        List<UserScore> topScores = new ArrayList<>();
        topScores.add(new UserScore("Player1", 500, 5));
        topScores.add(new UserScore("Player2", 300, 3));
        topScores.add(new UserScore("Player5", 800, 5));

        // Save the top scores
        ScoreHandler.saveTopScores(topScores);

        // Reload the scores and verify
        List<UserScore> loadedScores = ScoreHandler.loadTopScores();
        assertEquals(3, loadedScores.size(), "Loaded scores should match the saved scores count");

        // Verify the correct order
        assertEquals("Player5", loadedScores.get(0).username, "First place should be Player5");
        assertEquals(800, loadedScores.get(0).score, "First place score should be 800");
        assertEquals(5, loadedScores.get(0).level, "First place level should be 5");

        assertEquals("Player1", loadedScores.get(1).username, "Second place should be Player1");
        assertEquals(500, loadedScores.get(1).score, "Second place score should be 500");
        assertEquals(5, loadedScores.get(1).level, "Second place level should be 5");

        assertEquals("Player2", loadedScores.get(2).username, "Third place should be Player2");
        assertEquals(300, loadedScores.get(2).score, "Third place score should be 300");
        assertEquals(3, loadedScores.get(2).level, "Third place level should be 3");

        System.out.println(loadedScores);
        // Check for non-negative scores
        for (UserScore score : loadedScores) {
            assertTrue(score.score >= 0, "All scores should be non-negative");
        }
    }


    //Should Load Scores In Descending Order
    @Test
    void loadTopScores_Descending() {
        // Prepare test data
        List<UserScore> topScores = new ArrayList<>();
        topScores.add(new UserScore("Player1", 500, 5));
        topScores.add(new UserScore("Player2", 700, 7));
        topScores.add(new UserScore("Player3", 300, 3));
        topScores.add(new UserScore("Player4", 500, 3));
        // Save the test scores
        ScoreHandler.saveTopScores(topScores);

        // Load the top scores
        List<UserScore> loadedScores = ScoreHandler.loadTopScores();

        // Verify the scores are loaded and sorted in descending order
        assertEquals(4, loadedScores.size());
        assertEquals("Player2", loadedScores.get(0).username); // Highest score first
        assertEquals(700, loadedScores.get(0).score);
    }

}
