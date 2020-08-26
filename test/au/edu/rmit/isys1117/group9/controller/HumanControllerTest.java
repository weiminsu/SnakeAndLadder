package au.edu.rmit.isys1117.group9.controller;


import au.edu.rmit.isys1117.group9.model.Board;
import au.edu.rmit.isys1117.group9.model.Dice;
import au.edu.rmit.isys1117.group9.model.Trap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import static org.mockito.Mockito.*;

public class HumanControllerTest {

    private Board board;
    private Dice dice;
    private HumanController controller;

    @Before
    public void setUp() {
        board = Mockito.mock(Board.class);
        dice = Mockito.mock(Dice.class);
        controller = new HumanController(board);
        when(board.getDice()).thenReturn(dice);
    }

    @Test
    public void testRollDiceAndMoveSuccess() {
        Assert.assertNotNull(board);
        when(dice.roll()).thenReturn(5);
        Assert.assertTrue(controller.rollDiceAndMove());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testRollDiceAndMoveFailure() {
        Assert.assertNotNull(board);
        when(dice.roll()).thenThrow(new RuntimeException());
        controller.rollDiceAndMove();
    }

    @Test
    public void testPlaceSnakeGuardSuccess() {
        Assert.assertNotNull(board);
        doNothing().when(board).add(any(Trap.class));
        Assert.assertTrue(controller.placeSnakeGuard());
    }

    @Test
    public void testPlaceSnakeGuardFailure() {
        Assert.assertNotNull(board);
        doThrow(new RuntimeException()).when(board).add(any(Trap.class));
        Assert.assertFalse(controller.placeSnakeGuard());
    }

    @Test
    public void testMoveWithoutDiceSuccess() {
        Assert.assertNotNull(board);
        doNothing().when(board).setPiece(anyInt(),anyInt());
        Assert.assertTrue(controller.moveWithoutRollDice());
    }

    @Test
    public void testMoveWithoutDiceFailure() {
        Assert.assertNotNull(board);
        doThrow(new RuntimeException()).when(board).setPiece(anyInt(),anyInt());
        Assert.assertFalse(controller.moveWithoutRollDice());
    }
}

