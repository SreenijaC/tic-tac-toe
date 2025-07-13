package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class Game {
    private final Board board;
    private final Player playerX;
    private final Player playerO;
    private final UserInput userInput;

    private int xWins = 0;
    private int oWins = 0;
    private int ties = 0;

    // to keep track of order of players and who goes first
    private Player first;
    private Player second;

    public Game(UserInput userInput) {
        this.board = new Board();
        this.playerX = new Player("X");
        this.playerO = new Player("O");
        this.userInput = userInput;
        this.first = playerX;   // X always starts first game
        this.second = playerO;
    }

    public void start() {
        System.out.println();
        System.out.println("Welcome to Tic-Tac-Toe!");
        System.out.println();

        boolean playAgain;
        do {
            board.reset();        // reset the board
            board.display();      // show empty board
            String result = playOneGame(); // play one full round and get result ("X", "O", or "TIE")

            // update score based on result
            updateGameLog(result);

            // print current stats
            printGameLog();

            // rearrange turn order based on who lost
            if (result.equals("X")) {
                first = playerO;
                second = playerX;
            } else if (result.equals("O")) {
                first = playerX;
                second = playerO;
            } else { // tie: just swap turn order
                Player temp = first;
                first = second;
                second = temp;
            }

            playAgain = userInput.askPlayAgain();

        } while (playAgain);

        // when exiting, write stats to file
        System.out.println("Writing the game log to disk. Please see game.txt for the final statistics!");
        writeGameLogToFile();

        System.out.println();
        System.out.println("Thanks for playing!");
    }

    // play a single game round, return result
    private String playOneGame() {
        Player current = first;

        while (!board.isGameOver()) {
            int move = userInput.getValidMove(board, current.getSymbol());
            board.makeMove(move, current.getSymbol());
            board.display();

            if (board.hasWinner()) {
                System.out.println();
                System.out.println("Player " + current.getSymbol() + " wins!");
                return current.getSymbol();  // return "X" or "O"
            }

            current = (current == first) ? second : first;  // switch player
        }

        System.out.println();
        System.out.println("It's a draw!");
        return "TIE";
    }

    // increase counters
    private void updateGameLog(String result) {
        switch (result) {
            case "X" -> xWins++;
            case "O" -> oWins++;
            case "TIE" -> ties++;
        }
    }

    // print stats to screen
    private void printGameLog() {
        System.out.println();
        System.out.println("The current log is:\n");
        System.out.println("Player X Wins   " + xWins);
        System.out.println("Player O Wins   " + oWins);
        System.out.println("Ties            " + ties);
        System.out.println();
    }

    // write stats to file
    private void writeGameLogToFile() {
        try (FileWriter writer = new FileWriter("game.txt")) {
            writer.write("Final Game Log:\n");
            writer.write("Player X Wins   " + xWins + "\n");
            writer.write("Player O Wins   " + oWins + "\n");
            writer.write("Ties            " + ties + "\n");
        } catch (IOException e) {
            System.out.println("Error writing game log to file.");
        }
    }
}
