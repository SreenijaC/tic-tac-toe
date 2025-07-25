package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerPlayer extends Player {
    private final Board board;
    private final String opponentSymbol;
    private final Random random; // used to pick moves randomly by comp when multiple choices present

    public ComputerPlayer(String symbol, Board board) {
        super(symbol);
        this.board = board;
         // determines opponent's symbol based on player's symbol
        this.opponentSymbol = symbol.equalsIgnoreCase("X") ? "O" : "X";
        this.random = new Random();
    }

    public int getMove(int moveNumber) {
        // 1. first move: pick random corner
        if (moveNumber == 1) {
            return pickRandomCorner();
        }

        // 2. second move: if center available, take it
        if (moveNumber == 2 && board.isCellAvailable(5)) {
            return 5;
        }

        // 3. if comp can win, take winning move
        Integer winMove = findWinningMove(getSymbol());
        if (winMove != null) {
            return winMove;
        }

        // 4. if opponent can win next move, block it
        Integer blockMove = findWinningMove(opponentSymbol);
        if (blockMove != null) {
            return blockMove;
        }

        // 5. pick random available cell if nothing available
        return pickRandomAvailable();
    }

    // picks random corner cell (1, 3, 7, 9) from available corners
    private int pickRandomCorner() {
        int[] corners = {1, 3, 7, 9};
        List<Integer> availableCorners = new ArrayList<>(); // list to store corners (1, 3, 7, 9) which are free
        for (int c : corners) {
            if (board.isCellAvailable(c)) {
                availableCorners.add(c); // adding free corner to list of possible choices
            }
        }
        if (!availableCorners.isEmpty()) {  // if available corners, pick one randomly
            return availableCorners.get(random.nextInt(availableCorners.size()));
        }
        return pickRandomAvailable();  // if no corners absent, pick any random available cell

    }

        // checks if there's winning move for given symbol
    private Integer findWinningMove(String symbol) {
        for (int cell = 1; cell <= 9; cell++) {
                     // checking empty cells
            if (board.isCellAvailable(cell)) {
                board.setCell(cell, symbol); // to make move temporarily
                boolean wins = board.hasWinner();  // checking if it can win
                board.setCell(cell, String.valueOf(cell)); // revert cell
                if (wins) {
                    return cell;
                }
            }
        }
        return null;
    }

    // selecting random cell from all available cells 
    private int pickRandomAvailable() {
        List<Integer> available = new ArrayList<>();

        // gathering all available cells
        for (int i = 1; i <= 9; i++) {
            if (board.isCellAvailable(i)) {
                available.add(i);
            }
        }
        //  check if no moves left , game should be over
        if (available.isEmpty()) {
            throw new IllegalStateException("No moves left");
        }
          // return random available cell
        return available.get(random.nextInt(available.size()));
    }
}
