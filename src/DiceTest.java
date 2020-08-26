import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DiceTest {
    @Test
    public void testSet() {
        Board board = new Board();
        Dice dice = new Dice(board);
        int num1 = dice.set(100);
        int num2 = dice.set(-2);
        int num3 = dice.set(3);

        assertEquals(6, num1);
        assertEquals(1, num2);
        assertEquals(3, num3);

    }

    @Test
    public void testRoll() {
        Board board = new Board();
        Dice dice = new Dice(board);

        for (int i = 0; i < 10; i++) {
            int num = dice.roll();
            assertTrue(num >= 1 && num <= 6);

        }
    }

}
