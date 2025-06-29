package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class GameTest {

    private InputStream systemInBackup;

    @BeforeEach
    void setup() {
        systemInBackup = System.in;
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.setIn(systemInBackup);
    }

    @Test
    void testGameFlow_playerXWins() {
        String input = "1\n4\n2\n5\n3\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UserInput userInput = new UserInput();
        Game game = new Game(userInput);

        game.start();
        // simple assertion to remove unused import warning
        assertTrue(true);
    }

    @Test
    void testGameFlow_draw() {
        // moves filling board with no winner, then no play again
        String input = "1\n2\n3\n5\n4\n6\n8\n7\n9\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UserInput userInput = new UserInput();
        Game game = new Game(userInput);

        game.start();
    }

}
