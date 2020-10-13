package au.edu.rmit.isys1117.group9.controller;

import controller.Admin;
import model.Board;

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
        expectedEx.expectMessage("The snake heads can not connected to others tails");
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


    @Test
    public void testPutLadderTooLong() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("Ladder is too long");
        Admin admin = new Admin(new Board(), null);
        admin.doAddLadder(99, 1);
    }

    @Test
    public void testNoMoreFiveLadders() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("Too many ladders");
        Admin admin = new Admin(new Board(), null);
        admin.doAddLadder(50, 20);
        admin.doAddLadder(31, 6);
        admin.doAddLadder(70, 52);
        admin.doAddLadder(98, 90);
        admin.doAddLadder(75, 67);
        admin.doAddLadder(23, 12);

    }

    @Test
    public void testNoCircleLadders() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("The ladder heads can not connected to others ladders");
        Admin admin = new Admin(new Board(), null);
        admin.doAddLadder(7, 2);
        admin.doAddLadder(7, 5);

    }
    @Test
    public void testOneLadderInSpecificArea() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("Base cannot be 1 and top cannot be 100");
        Admin admin = new Admin(new Board(), null);
        admin.doAddLadder(100, 91);
        admin.doAddLadder(20, 1);

    }
    @Test
    public void testLadderCannotPutSnakeHead() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("The ladder cannot be connected to snakes head");
        Admin admin = new Admin(new Board(), null);
        admin.doAddSnake(45,23);
        admin.doAddLadder(45, 23);
        admin.doAddLadder(54, 45);

    }
}
