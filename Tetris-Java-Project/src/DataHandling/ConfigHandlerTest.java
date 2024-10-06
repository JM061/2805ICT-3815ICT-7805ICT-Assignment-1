package DataHandling;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import DataHandling.ConfigHandler;

class ConfigHandlerTest {

    private static final String TEST_CONFIG_FILE = "test_config.json";

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary test config file with default values
        String initialConfig = "{ \"fieldWidth\": 10, \"fieldHeight\": 20 }";
        Files.write(new File(TEST_CONFIG_FILE).toPath(), initialConfig.getBytes());
        // Set the config file to be used in the ConfigHandler
        ConfigHandler.setConfigFile(TEST_CONFIG_FILE);
    }

    @AfterEach
    void tearDown() {
        // Delete the temporary config file after each test
        new File(TEST_CONFIG_FILE).delete();
    }

    @Test

    //tests setting of valid field height values
    void setFieldHeight_Valid() {
        ConfigHandler.setFieldHeight(15); // Valid height
        assertEquals(15, ConfigHandler.getFieldHeight(), "Field height should be set to 15");
    }

    @Test
    //tests setting of invalid field height values
    void setFieldHeight_Invalid() {
        // Testing below valid range
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ConfigHandler.setFieldHeight(5); // Invalid height
        });
        assertEquals("fieldHeight must be between 10 and 30", exception.getMessage());

        // Testing above valid range
        exception = assertThrows(IllegalArgumentException.class, () -> {
            ConfigHandler.setFieldHeight(35); // Invalid height
        });
        assertEquals("fieldHeight must be between 10 and 30", exception.getMessage());
    }


    @Test
    //tests setting of valid field width values
    void setFieldWidth_Valid() {
        ConfigHandler.setFieldWidth(15); // Valid width
        assertEquals(15, ConfigHandler.getFieldWidth(), "Field width should be set to 15");
    }

    @Test
    //tests setting of invalid field width values
    void setFieldWidth_Invalid() {
        // Testing below valid range
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ConfigHandler.setFieldWidth(4); // Invalid width
        });
        assertEquals("fieldWidth must be between 5 and 20", exception.getMessage());

        // Testing above valid range
        exception = assertThrows(IllegalArgumentException.class, () -> {
            ConfigHandler.setFieldWidth(25); // Invalid width
        });
        assertEquals("fieldWidth must be between 5 and 20", exception.getMessage());
    }
}
