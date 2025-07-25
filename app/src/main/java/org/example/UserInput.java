package org.example;

import java.util.Scanner;

public class UserInput {
    private final Scanner scanner;

    public UserInput() {
        scanner = new Scanner(System.in);
    }

    public int getValidMove(Board board, String playerSymbol) {
        while (true) {
            System.out.print("What is your move? ");
            String input = scanner.nextLine().trim(); // remove leading/trailing spaces
            System.out.println();

            try {
                int move = Integer.parseInt(input); // to convert input to num
                // check if move is btw 1-9 and cell isn't taken
                if (move >= 1 && move <= 9 && board.isCellAvailable(move)) {
                    return move;
                } else if (move >= 1 && move <= 9) {
                    System.out.println("That cell is already taken. Try again.");
                } else {
                    System.out.println("That is not a valid move! Try again.");
                }
            } catch (NumberFormatException ignored) {
                System.out.println("That is not a valid move! Try again.");
            }
        }
    }

    public boolean askPlayAgain() {
        while (true) {
            System.out.println();
            System.out.print("Would you like to play again (yes/no)? ");
            String input = scanner.nextLine().trim().toLowerCase(); // cleans input
            System.out.println();

            if (input.equals("yes"))
                return true;
            if (input.equals("no"))
                return false;

            System.out.println();
            System.out.println("That is not a valid move! Try again."); // retry again
        }
    }

    // to read user input for game class
    public Scanner getScanner() {
        return scanner;
    }
}
