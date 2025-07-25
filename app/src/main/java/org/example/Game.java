package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class Game {
    private final Board board;
    private Player playerX;
    private Player playerO;
    private final UserInput userInput;

    private int xWins = 0;
    private int oWins = 0;
    private int ties = 0;

    // to keep track of order of players and who goes first
    private Player first;
    private Player second;

    public Game(UserInput userInput) {
        this.board = new Board();
        this.userInput = userInput;
        this.playerX = new Player("X");
        this.playerO = new Player("O");
        this.first = playerX; // X always starts first game by default
        this.second = playerO;
    }

    public void start() {
        System.out.println();
        System.out.println("Welcome to Tic-Tac-Toe!");
        System.out.println();

        boolean playAgain;
        do {
            // prompt the user to select what game mode
            int gameMode = askGameMode();

            setupPlayers(gameMode); // setting up players based on game mode

            board.reset(); // reset board
            board.display(); // displays empty board
            String result = playOneGame(); // playing one full round with result ("X", "O", or "TIE")

            // update score based on result
            updateGameLog(result);

            // print current stats
            printGameLog();

            // rearrange turn order based on result
            rearrangeTurnOrder(result);

            playAgain = userInput.askPlayAgain();

        } while (playAgain);

        // write stats to file when exiting
        System.out.println("Writing the game log to disk. Please see game.txt for the final statistics!");
        writeGameLogToFile();

        System.out.println();
        System.out.println("Thanks for playing!");
    }

    // method for game mode selection
    private int askGameMode() {
        while (true) {
            System.out.println("What kind of game would you like to play?");
            System.out.println("1. Human vs. Human");
            System.out.println("2. Human vs. Computer");
            System.out.println("3. Computer vs. Human");
            System.out.print("\nWhat is your selection? ");

            String input = userInput.getScanner().nextLine().trim();

            if (input.equals("1") || input.equals("2") || input.equals("3")) {
                return Integer.parseInt(input);
            }
            System.out.println("Invalid selection. Please enter 1, 2, or 3.\n");
        }
    }

    // setting playerX and playerO according to mode and who goes first
    private void setupPlayers(int mode) {
        switch (mode) {
            case 1 -> { // human vs human
                playerX = new Player("X");
                playerO = new Player("O");
                first = playerX;
                second = playerO;
                System.out.println("You chose Human vs. Human.");
            }
            case 2 -> { // human vs computer
                playerX = new Player("X");
                playerO = new ComputerPlayer("O", board);
                first = playerX; // human always first in this mode
                second = playerO;
                System.out.println("You chose Human vs. Computer. Human goes first.");
            }
            case 3 -> { // computer vs human
                playerX = new ComputerPlayer("X", board);
                playerO = new Player("O");
                first = playerX; // computer goes first in this mode
                second = playerO;
                System.out.println("You chose Computer vs. Human. Computer goes first.");
            }
        }
        System.out.println();
    }

    // play single game round, return result
    private String playOneGame() {
        Player current = first;
        int moveNumber = 1; // count moves to help computer decide 1st/2nd move

        while (!board.isGameOver()) {
            int move;

            if (current instanceof ComputerPlayer) {
                // comp player decides move based on board and move number
                move = ((ComputerPlayer) current).getMove(moveNumber);
                // comp may use the current move number to decide strategy
                System.out.println("computer (" + current.getSymbol() + ") chooses: " + move);
                System.out.println();
            } else {
                // human player inputs move validating input & availability(checks if cell is
                // free)
                move = userInput.getValidMove(board, current.getSymbol());
            }
            // to make the move on the board, returns true if successful
            boolean moveSuccess = board.makeMove(move, current.getSymbol());

            if (!moveSuccess) {
                // defensive check: if move failed (cell already taken), notifies user to retry
                System.out.println("That cell is already taken. Try again.");
                continue; // asking for move again without switching player or increasing moveNumber
            }

            board.display();

            if (board.hasWinner()) {
                System.out.println();
                System.out.println("Player " + current.getSymbol() + " wins!");
                return current.getSymbol(); // return winner symbol by ending game
            }

            if (board.isFull()) {
                System.out.println();
                System.out.println("It's a draw!");
                return "TIE";
            }

            // switching turns
            current = (current == first) ? second : first;
            moveNumber++;
        }

        // should never be reached as checks above present
        return "TIE";
    }

    // increases counters based on result string
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

    // rearrange turn order based on who lost/tie
    private void rearrangeTurnOrder(String result) {
        if (result.equals("X")) {
            // if X won, loser goes first next game (O)
            first = playerO;
            second = playerX;
        } else if (result.equals("O")) {
            // if O won, loser goes first next game (X)
            first = playerX;
            second = playerO;
        } else {
            // tie = just swap order
            Player temp = first;
            first = second;
            second = temp;
        }
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