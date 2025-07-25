package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComputerPlayerTest {

    private Board board;
    private ComputerPlayer computerX;
    private ComputerPlayer computerO;

    @BeforeEach
    void setup() {
        board = new Board();
        computerX = new ComputerPlayer("X", board);
        computerO = new ComputerPlayer("O", board);
    }

    @Test
    void test_firstMove_picksCorner_whenBoardEmpty() {
        board.reset();
        int move = computerX.getMove(1);
        assertTrue(move == 1 || move == 3 || move == 7 || move == 9,
                   "should pick a corner on first move");
    }

    @Test
    void test_secondMove_picksCenter_ifAvailable() {
        board.reset();
        int move = computerX.getMove(2);
        assertEquals(5, move, "should pick center on second move if available");
    }

    @Test
    void test_winningMove_takesWinningSpot() {
        board.reset();
        board.makeMove(1, "X");
        board.makeMove(2, "X");
        int move = computerX.getMove(3);
        assertEquals(3, move, "should take winning move at cell 3");
    }

    @Test
    void test_blockingMove_blocksOpponentWin() {
        board.reset();
        board.makeMove(1, "O");
        board.makeMove(2, "O");
        int move = computerX.getMove(3);
        assertEquals(3, move, "should block opponent's winning move at cell 3");
    }

    @Test
    void test_pickRandomAvailable_whenNoOtherRulesApply() {
        board.reset();
        // Fill corners and center, forcing computer to pick other spot
        board.makeMove(1, "X");
        board.makeMove(3, "O");
        board.makeMove(5, "X");
        board.makeMove(7, "O");
        board.makeMove(9, "X");
        int move = computerO.getMove(4);
        // allowed cells are 2,4,6,8
        assertTrue(move == 2 || move == 4 || move == 6 || move == 8,
                   "should pick any available non-corner spot randomly");
    }
}
