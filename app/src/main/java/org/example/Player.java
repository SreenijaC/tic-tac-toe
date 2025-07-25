package org.example;

public class Player {
    private final String symbol; // player symbol x or o

    public Player(String symbol) {
        // to make sure symbol is x or o, if else throw error
        if (symbol == null || (!symbol.equalsIgnoreCase("x") && !symbol.equalsIgnoreCase("o"))) {
            throw new IllegalArgumentException("player symbol must be 'x' or 'o'");
        }
        this.symbol = symbol.toUpperCase(); 
    }

    public String getSymbol() {
        return symbol;
    }
}
