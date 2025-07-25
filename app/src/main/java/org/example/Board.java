package org.example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Board {
    private final String[] cells;
    private static final int Board_Size = 3;

    // all the possible winning combinations (using index positions in 1D array)
    private static final int[][] WIN_COMBINATIONS = {
            { 0, 1, 2 }, // top row
            { 3, 4, 5 }, // middle row
            { 6, 7, 8 }, // bottom row
            { 0, 3, 6 }, // left column
            { 1, 4, 7 }, // middle column
            { 2, 5, 8 }, // right column
            { 0, 4, 8 }, // diagonal from top-left to bottom-right
            { 2, 4, 6 } // diagonal from top-right to bottom-left
    };

    public Board() {
        cells = new String[Board_Size * Board_Size];
        reset();
    }

    // reset to put num 1 to 9 back into board every time new game starts.
    public void reset() {
        for (int index = 0; index < cells.length; index++) {
            cells[index] = String.valueOf(index + 1); // put values in each cell ex: "1" in cell 0, "2" in cell 1
        }
    }

    // check if given cell is still available
    public boolean isCellAvailable(int cellNumber) {
        int cellIndex = cellNumber - 1; // cellindex = 0 - 8, cellNumber = 1 - 9 num(user sees)

        if (cellIndex < 0 || cellIndex >= cells.length) {
            return false;
        }

        String val = cells[cellIndex];
        // checking if cell is not taken by either player.
        return !val.equals("X") && !val.equals("O");
    }

    // to place symbol in cell return true if successful.
    public boolean makeMove(int cellNumber, String symbol) {
        int cellIndex = cellNumber - 1; // convert to 0-based index

        if (cellIndex < 0 || cellIndex >= cells.length) {
            return false; // invalid cell number
        }

        // check if cell is available
        if (!cells[cellIndex].equals("X") && !cells[cellIndex].equals("O")) {
            cells[cellIndex] = symbol.toUpperCase(); // use uppercase symbol
            return true;
        }

        return false; // symbol placed in that cell or invalid

    }

    // helper method for temporary move simulation for computer player
    public void setCell(int cellNumber, String value) {
        int cellIndex = cellNumber - 1;
        if (cellIndex >= 0 && cellIndex < cells.length) {
            cells[cellIndex] = value;
        }
    }

    // check if any winning combination is achieved
    public boolean hasWinner() {
        for (int[] winningCombination : WIN_COMBINATIONS) {
            String first = cells[winningCombination[0]]; // symbol at 1st cell of this winning combo
            String second = cells[winningCombination[1]];
            String third = cells[winningCombination[2]];

            // put 3 cells into set to see if they all match
            Set<String> uniqueCells = new HashSet<>(Arrays.asList(first, second, third));

            // if all three are same, and that symbol is either "X" or "O", winner
            if (uniqueCells.size() == 1 && (first.equals("X") || first.equals("O"))) {
                return true;
            }
        }
        return false;
    }

    // to check if board is complete i.e no more valid moves
    public boolean isFull() {
        for (String cell : cells) {
            if (!cell.equals("X") && !cell.equals("O")) {
                return false; // found an empty cell, so board is not full
            }
        }
        return true; // all cells are filled
    }

    // determining game is over
    public boolean isGameOver() {
        return hasWinner() || isFull();
    }

    // Print the current board state in 3x3 format
    public void display() {
        System.out.println();
        for (int rowStart = 0; rowStart < cells.length; rowStart += Board_Size) {
            System.out.printf("  %s  |  %s  |  %s\n",
                    cells[rowStart], cells[rowStart + 1], cells[rowStart + 2]);

            if (rowStart < cells.length - Board_Size) {
                System.out.println("-----+-----+-----");
            }
        }
        System.out.println();
    }
}
