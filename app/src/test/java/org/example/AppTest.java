package org.example;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;

public class AppTest {
    @Test
    void testMain_runsWithoutError() {
        // Moves: player X -> 1, player O -> 4, player X -> 2, player O -> 5, player X
        // -> 3 (X wins)
        // then 'no' to quit
        String input = "1\n1\n4\n2\n5\n3\nno\n"; // corrected input sequence
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String[] args = {};
        App.main(args);
    }
}
