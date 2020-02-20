package com.maximo.giordano.gol;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

import static org.junit.Assert.assertArrayEquals;

public class GameTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGameWithNullBoard() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("board is null");

        new Game(null);
    }

    @Test
    public void testGameWithZeroRows() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("rows is 0");

        new Game(new boolean[0][0]);
    }

    @Test
    public void testGameWithNullRow() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("row 1 is null");

        new Game(new boolean[][]{{false, false, false}, null, {false, false, false}});
    }

    @Test
    public void testGameWithZeroCols() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("cols is 0");

        new Game(new boolean[3][0]);
    }

    @Test
    public void testGameWithInvalidNumberOfColumns() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("the number of columns of row 1 is not 3");

        new Game(new boolean[][]{{false, false, false}, {false, false}, {false, false, false}});
    }

    @Test
    public void testCyclicPatterns() {
        Constants.CYCLIC_PATTERNS.entrySet().forEach(this::testPattern);
    }

    private void testPattern(Map.Entry<String, boolean[][][]> pattern) {
        String patternName = pattern.getKey();
        boolean[][] initialBoard = pattern.getValue()[0];
        Game game = new Game(initialBoard);
        int numberOfSteps = 100;

        for (int i = 1; i <= numberOfSteps; i++) {
            game.next();
            boolean[][] expectedBoard = pattern.getValue()[i % pattern.getValue().length];
            assertArrayEquals(patternName + " failed at step " + i, expectedBoard, game.getBoard());
        }
    }
}
