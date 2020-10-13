package au.edu.rmit.isys1117.group9.controller;

import au.edu.rmit.isys1117.group9.model.Board;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class AdminTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private Board board;
    private Admin admin;
    private IUserInput iUserInput;

    @Before
    public void setUp() {
        this.iUserInput = Mockito.mock(IUserInput.class);
        this.board = new Board();
        this.admin = new Admin(this.board, this.iUserInput);
    }

    @Test
    public void testPutSnakeTooLongNegative() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("snake is too long");
        admin.doAddSnake(99, 1);
    }

    @Test
    public void testPutSnakeTooLong1()throws Exception{
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("snake is too long");
        when(iUserInput.getInt(anyString(),anyInt(),anyInt())).thenReturn(32).thenReturn(1);
        admin.putSnake();
    }

    @Test
    public void testPutSnakeTooLongPositive() throws Exception {
        admin.doAddSnake(26, 1);

    }


    @Test
    public void testNoMoreFiveSnakesNegative1() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("too many snakes");
        admin.doAddSnake(50, 20);
        admin.doAddSnake(31, 1);
        admin.doAddSnake(70, 52);
        admin.doAddSnake(98, 90);
        admin.doAddSnake(75, 67);
        Assert.assertEquals(5, board.getSnakeCounts());
        admin.doAddSnake(98, 90);
        admin.doAddSnake(25, 12);
    }

    @Test
    public void testNoMoreFiveSnakesNegative2() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("too many snakes");
        admin.doAddSnake(50, 33);
        admin.doAddSnake(51, 31);
        admin.doAddSnake(60, 52);
        admin.doAddSnake(78, 55);
        admin.doAddSnake(95, 87);
        Assert.assertEquals(5, board.getSnakeCounts());
        admin.doAddSnake(15, 2);
    }

    @Test
    public void testNoMoreFiveSnakesPositive1() throws Exception {
        admin.doAddSnake(50, 20);
        admin.doAddSnake(31, 1);
        admin.doAddSnake(70, 52);
        admin.doAddSnake(98, 90);
        admin.doAddSnake(75, 67);
        Assert.assertEquals(5, board.getSnakeCounts());

    }

    @Test
    public void testNoMoreFiveSnakesPositive2() throws Exception {
        admin.doAddSnake(50, 20);
        admin.doAddSnake(31, 1);
        admin.doAddSnake(70, 52);
        admin.doAddSnake(98, 90);
        admin.doAddSnake(75, 67);
        Assert.assertEquals(5, board.getSnakeCounts());

    }

    @Test
    public void testNoCircleSnakes() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("The snake heads can not connected to others tails");
        admin.doAddSnake(50, 20);
        admin.doAddSnake(31, 1);
        Assert.assertEquals(2, board.getSnakeCounts());
        admin.doAddSnake(70, 50);

    }

    @Test
    public void testOneSnakeInSpecificArea() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("Only one snake head can be in 81-100");
        admin.doAddSnake(85, 71);
        Assert.assertEquals(1, board.getSnakeCounts());
        admin.doAddSnake(83, 71);

    }

    @Test
    public void testPutLadderTooLong() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("Ladder is too long");
        admin.doAddLadder(99, 1);
    }

    @Test
    public void testNoMoreFiveLadders() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("Too many ladders");
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
        admin.doAddLadder(7, 2);
        admin.doAddLadder(7, 5);

    }

    @Test
    public void testOneLadderInSpecificArea() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("Base cannot be 1 and top cannot be 100");
        admin.doAddLadder(100, 91);
        admin.doAddLadder(20, 1);

    }

    @Test
    public void testLadderCannotPutSnakeHead() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("The ladder cannot be connected to snakes head");
        admin.doAddSnake(45, 23);
        admin.doAddLadder(45, 23);
        admin.doAddLadder(54, 45);

    }
}
