package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        // Clean up game.txt file if exists
        File logFile = new File("game.txt");
        if (logFile.exists()) {
            logFile.delete();
        }
    }

    @Test
    void testGameFlow_playerXWins_andGameLogWritten() throws IOException {
        // Mode 1 for Human vs Human; moves lead to X win; then no to quit
        String input = "1\n1\n4\n2\n5\n3\nno\n";
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
    }

    @Test
    void testGameFlow_draw_andGameLogWritten() throws IOException {
        // Mode 1; moves lead to draw; then no to quit
        String input = "1\n1\n2\n3\n5\n4\n6\n8\n7\n9\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UserInput userInput = new UserInput();
        Game game = new Game(userInput);
        game.start();

        File logFile = new File("game.txt");
        assertTrue(logFile.exists(), "Log file should be created");

        String content = Files.readString(logFile.toPath());
        assertTrue(content.contains("Ties            1"));
    }
}
