package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class GameTest {

    private InputStream systemInBackup;

    @BeforeEach
    void setup() {
        systemInBackup = System.in;
    }

    @AfterEach
    void tearDown() {
        System.setIn(systemInBackup);
    }

    @Test
    void testGameFlow_playerXWins_andGameLogWritten() throws IOException {
        // X wins by taking positions 1, 2, 3; then "no" to not replay
        String input = "1\n4\n2\n5\n3\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UserInput userInput = new UserInput();
        Game game = new Game(userInput);
        game.start();

        File logFile = new File("game.txt");
        assertTrue(logFile.exists(), "Log file should be created");

        String content = Files.readString(logFile.toPath());
        assertTrue(content.contains("Player X Wins   1"));
        assertTrue(content.contains("Player O Wins   0"));
        assertTrue(content.contains("Ties            0"));

        // Clean up after test
        logFile.delete();
    }

    @Test
    void testGameFlow_draw_andGameLogWritten() throws IOException {
        // Force a draw with the given sequence; then exit
        String input = "1\n2\n3\n5\n4\n6\n8\n7\n9\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UserInput userInput = new UserInput();
        Game game = new Game(userInput);
        game.start();

        File logFile = new File("game.txt");
        assertTrue(logFile.exists(), "Log file should be created");

        String content = Files.readString(logFile.toPath());
        assertTrue(content.contains("Ties            1"));

        // Clean up after test
        logFile.delete();
    }
}
