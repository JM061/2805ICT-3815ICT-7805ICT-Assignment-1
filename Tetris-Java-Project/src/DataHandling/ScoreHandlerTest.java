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

    //Should Return Empty Json Object When No FileExists
    @Test
    void loadScores_ReturnEmptyIfNoFile() {
        // Ensure the temp file doesn't exist for this test
        File file = new File(TEMP_SCORE_FILE);
        file.delete();

        // Test the loadScores method
        JsonObject scores = ScoreHandler.loadScores();
        assertTrue(scores.keySet().isEmpty(), "Scores should be empty when the file doesn't exist");
    }

    //Should Save Scores Correctly
    @Test
    void saveTopScores_Correctly() {
        // Prepare test data
        List<UserScore> topScores = new ArrayList<>();
        topScores.add(new UserScore("Player1", 500, 5));
        topScores.add(new UserScore("Player2", 300, 3));
        topScores.add(new UserScore("Player5", 300, 3));

        // Save the top scores
        ScoreHandler.saveTopScores(topScores);

        // Reload the scores and verify
        List<UserScore> loadedScores = ScoreHandler.loadTopScores();
        assertEquals(3, loadedScores.size());
        assertEquals("Player1", loadedScores.get(0).username);
        assertEquals(500, loadedScores.get(0).score);
        assertEquals(5, loadedScores.get(0).level);
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

    @Test
    void addNewScore_ShouldAddAndSaveScoresCorrectly() {
        // Initially save a single score
        List<UserScore> initialScores = new ArrayList<>();
        initialScores.add(new UserScore("Player1", 100, 1));
        ScoreHandler.saveTopScores(initialScores);

        // Add a new score
        ScoreHandler.addNewScore("Player2", 500, 5);

        // Reload the top scores and verify
        List<UserScore> loadedScores = ScoreHandler.loadTopScores();
        assertEquals(2, loadedScores.size());
        assertEquals("Player2", loadedScores.get(0).username); // Should be at the top
        assertEquals(500, loadedScores.get(0).score);
    }

    @Test
    void removeScoresWithoutPrompt_ShouldClearAllScores() {
        // Prepare test data with some initial scores
        List<UserScore> initialScores = new ArrayList<>();
        initialScores.add(new UserScore("Player1", 500, 5));
        initialScores.add(new UserScore("Player2", 300, 3));
        ScoreHandler.saveTopScores(initialScores);

        // Verify scores were saved
        List<UserScore> savedScores = ScoreHandler.loadTopScores();
        assertFalse(savedScores.isEmpty(), "Scores should not be empty before removal");

        // Remove scores without prompting
        ScoreHandler.removeScoresWithoutPrompt();

        // Verify scores are cleared
        List<UserScore> clearedScores = ScoreHandler.loadTopScores();
        assertTrue(clearedScores.isEmpty(), "Scores should be empty after removal");
    }

}
