package org.example;

public class Player {
    private final String symbol; // player symbol: "X" or "O"

    public Player(String symbol) {
        if (symbol == null || (!symbol.equals("X") && !symbol.equals("O"))) {
            throw new IllegalArgumentException("Player symbol must be 'X' or 'O'");
        }
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
