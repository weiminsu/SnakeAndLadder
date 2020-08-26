package au.edu.rmit.isys1117.group9.controller;

import au.edu.rmit.isys1117.group9.model.Board;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class AdminTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testPutSnakeTooLong() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("snake is too long");
        Admin admin = new Admin(new Board(), null);
        admin.doAddSnake(99, 1);
    }

    @Test
    public void testNoMoreFiveSnakes() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("too many snakes");
        Admin admin = new Admin(new Board(), null);
        admin.doAddSnake(50, 20);
        admin.doAddSnake(31, 1);
        admin.doAddSnake(70, 52);
        admin.doAddSnake(98, 90);
        admin.doAddSnake(75, 67);
        admin.doAddSnake(23, 12);

    }

    @Test
    public void testNoCircleSnakes() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("The snake heads can not connected others tails");
        Admin admin = new Admin(new Board(), null);
        admin.doAddSnake(50, 20);
        admin.doAddSnake(31, 1);
        admin.doAddSnake(70, 50);

    }
    @Test
    public void testOneSnakeInSpecificArea() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("Only one snake head can be in 81-100");
        Admin admin = new Admin(new Board(), null);
        admin.doAddSnake(85, 71);
        admin.doAddSnake(83, 71);

    }
}
