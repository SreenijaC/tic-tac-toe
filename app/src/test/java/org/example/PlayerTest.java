package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    @Test
    void testPlayerCreation_validSymbols() {
        Player xPlayer = new Player("X");
        Player oPlayer = new Player("O");
        assertEquals("X", xPlayer.getSymbol());
        assertEquals("O", oPlayer.getSymbol());
    }

    @Test
    void testPlayerCreation_invalidSymbol_throws() {
        assertThrows(IllegalArgumentException.class, () -> new Player("A"));
        assertThrows(IllegalArgumentException.class, () -> new Player(""));
        assertThrows(IllegalArgumentException.class, () -> new Player(null));
    }
}
