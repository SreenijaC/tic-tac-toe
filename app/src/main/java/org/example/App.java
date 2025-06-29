package org.example;

public class App {
    public static void main(String[] args) {
        UserInput userInput = new UserInput();
        Game game = new Game(userInput);
        game.start();
    }
}
