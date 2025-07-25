package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class UserInputTest {

    private InputStream systemInBackup;

    @BeforeEach
    void setUp() {
        // backup original System.in
        systemInBackup = System.in;
    }

    @AfterEach
    void tearDown() {
        // restore original System.in
        System.setIn(systemInBackup);
    }

    @Test
    void testGetValidMove_validInput() {
        String simulatedInput = "3\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        UserInput userInput = new UserInput();
        Board board = new Board();

        int move = userInput.getValidMove(board, "X");
        assertEquals(3, move);
    }

    @Test
    void testGetValidMove_invalidThenValidInput() {
        String simulatedInput = "abc\n4\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        UserInput userInput = new UserInput();
        Board board = new Board();

        int move = userInput.getValidMove(board, "O");
        assertEquals(4, move);
    }

    @Test
    void testAskPlayAgain_yes() {
        String simulatedInput = "yes\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        UserInput userInput = new UserInput();
        assertTrue(userInput.askPlayAgain());
    }

    @Test
    void testAskPlayAgain_no() {
        String simulatedInput = "no\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        UserInput userInput = new UserInput();
        assertFalse(userInput.askPlayAgain());
    }

    @Test
    void testAskPlayAgain_invalidThenYes() {
        String simulatedInput = "maybe\n yes\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        UserInput userInput = new UserInput();
        assertTrue(userInput.askPlayAgain());
    }

    @Test
    void testGetValidMove_takenThenValid() {
        String simulatedInput = "5\n2\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        UserInput userInput = new UserInput();
        Board board = new Board();
        board.makeMove(5, "X"); // occupy cell 5

        int move = userInput.getValidMove(board, "O");
        assertEquals(2, move);
    }

}
