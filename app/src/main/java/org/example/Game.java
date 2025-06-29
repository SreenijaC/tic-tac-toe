package org.example;

public class Game {
    private final Board board;
    private final Player playerX;
    private final Player playerO;
    private final UserInput userInput;
    private Player currentPlayer;

    public Game(UserInput userInput) {
        this.board = new Board();
        this.playerX = new Player("X");
        this.playerO = new Player("O");
        this.userInput = userInput;
    }

    public void start() {
        System.out.println();
        System.out.println("Welcome to Tic-Tac-Toe!");
        System.out.println();

        boolean playAgain;
        do {
            board.reset(); // clear board for new game
            currentPlayer = playerX; // x always starts
            board.display(); // show empty board
            playOneGame(); // play until win or draw
            playAgain = userInput.askPlayAgain(); // ask if want to play again
        } while (playAgain);

        System.out.println();
        System.out.println("Goodbye!");
    }

    private void playOneGame() {
        while (!board.isGameOver()) {
            int move = userInput.getValidMove(board, currentPlayer.getSymbol());
            board.makeMove(move, currentPlayer.getSymbol());
            board.display();

            if (board.hasWinner()) {
                System.out.println();
                System.out.println("Player " + currentPlayer.getSymbol() + " wins!");
                return;
            }
            switchPlayer();
        }
        System.out.println();
        System.out.println("It's a draw!");
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }
}
