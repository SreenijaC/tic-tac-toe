package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {
    private Board board;

    @BeforeEach
    void setup() {
        board = new Board();
    }

    @Test
    void testReset_initializesCellsCorrectly() {
        board.reset();
        for (int i = 1; i <= 9; i++) {
            assertTrue(board.isCellAvailable(i), "cell " + i + " should be available after reset");
        }
    }

    @Test
    void testIsCellAvailable_outOfBounds() {
        assertFalse(board.isCellAvailable(0), "cell 0 is invalid");
        assertFalse(board.isCellAvailable(10), "cell 10 is invalid");
    }

    @Test
    void testMakeMove_validAndInvalid() {
        assertTrue(board.makeMove(1, "X"), "should allow move in cell 1");
        assertFalse(board.makeMove(1, "O"), "should not allow move in taken cell");
        assertFalse(board.makeMove(0, "X"), "cell 0 is invalid");
        assertFalse(board.makeMove(10, "O"), "cell 10 is invalid");
    }

    @Test
    void testHasWinner_detectsWinner() {
        // set top row to X
        board.makeMove(1, "X");
        board.makeMove(2, "X");
        board.makeMove(3, "X");
        assertTrue(board.hasWinner(), "should detect winner for top row");

        board.reset();
        // set diagonal to O
        board.makeMove(1, "O");
        board.makeMove(5, "O");
        board.makeMove(9, "O");
        assertTrue(board.hasWinner(), "should detect winner for diagonal");
    }

    @Test
    void testIsFull_andIsGameOver() {
        // fill board without winner
        for (int i = 1; i <= 9; i++) {
            board.makeMove(i, (i % 2 == 0) ? "X" : "O");
        }
        assertTrue(board.isFull(), "board should be full");
        assertTrue(board.isGameOver(), "game should be over if board full");

        board.reset();
        assertFalse(board.isFull(), "board should not be full after reset");
        assertFalse(board.isGameOver(), "game should not be over after reset");
    }
}
