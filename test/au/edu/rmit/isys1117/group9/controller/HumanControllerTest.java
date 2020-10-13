package au.edu.rmit.isys1117.group9.controller;


import au.edu.rmit.isys1117.group9.exception.InvalidInputException;
import au.edu.rmit.isys1117.group9.model.Board;
import au.edu.rmit.isys1117.group9.model.Dice;
import au.edu.rmit.isys1117.group9.model.Piece;
import au.edu.rmit.isys1117.group9.model.SnakeGuard;
import au.edu.rmit.isys1117.group9.exception.SnakeGuardPlacementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import static org.mockito.Mockito.*;

public class HumanControllerTest {

    private Board board;
    private Dice dice;
    private UIWrapper uiWrapper;
    private HumanController controller;

    @Before
    public void setUp() throws Exception {
        board = new Board();
        dice = Mockito.mock(Dice.class);
        board.setDice(dice);
        uiWrapper = Mockito.mock(UIWrapper.class);
        controller = new HumanController(board);
        controller.enableTestMode();
        controller.setUiWrapper(uiWrapper);
        doNothing().when(uiWrapper).showInfoMessage(anyString());
        doNothing().when(uiWrapper).showErrorMessage(anyString());
    }

    @Test
    public void testRollDiceSuccess() {
        doReturn(3).when(dice).roll();
        Assert.assertEquals(3, controller.rollDice());
    }

    @Test
    public void testChoosePieceSuccess() throws Exception {
        doReturn("2").when(uiWrapper).promptForInput(anyString());
        Assert.assertEquals(1, controller.choosePiece());
    }

    @Test(expected = InvalidInputException.class)
    public void testChoosePieceInvalidPiece() throws Exception {
        doReturn("5").when(uiWrapper).promptForInput(anyString());
        controller.choosePiece();
    }

    @Test(expected = InvalidInputException.class)
    public void testChoosePieceInvalidInput() throws Exception {
        doReturn("asdgaw").when(uiWrapper).promptForInput(anyString());
        controller.choosePiece();
    }

    @Test
    public void testMoveSuccess() {
        Piece before = board.getPiece(0);
        Assert.assertNotNull(before);
        Assert.assertEquals(1, before.getPosition());
        controller.move(0, 3);
        Piece after = board.getPiece(0);
        Assert.assertNotNull(after);
        Assert.assertEquals(4, after.getPosition());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveFailureOutOfBoundary() {
        Piece before = board.getPiece(0);
        Assert.assertNotNull(before);
        Assert.assertEquals(1, before.getPosition());
        controller.move(0, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveFailureInvalidPiece() {
        Piece before = board.getPiece(0);
        Assert.assertNotNull(before);
        Assert.assertEquals(1, before.getPosition());
        controller.move(6, 5);
    }

    @Test
    public void testChooseDestinationValidDiagonalMove() throws Exception {
        Piece piece = board.getPiece(0);
        board.movePieceTo(0, 25); // move the piece to a position convenient for test
        Assert.assertEquals(25, piece.getPosition());
        doReturn("35").when(uiWrapper).promptForInput(anyString());
        Assert.assertEquals(35, controller.chooseDestination(0));
    }

    @Test
    public void testChooseDestinationValidKnightMove() throws Exception {
        Piece piece = board.getPiece(0);
        board.movePieceTo(0, 25); // move the piece to a position convenient for test
        Assert.assertEquals(25, piece.getPosition());
        doReturn("46").when(uiWrapper).promptForInput(anyString());
        Assert.assertEquals(46, controller.chooseDestination(0));
    }

    @Test(expected = InvalidInputException.class)
    public void testChooseDestinationInvalidInput() throws Exception {
        Piece piece = board.getPiece(0);
        board.movePieceTo(0, 25); // move the piece to a position convenient for test
        Assert.assertEquals(25, piece.getPosition());
        doReturn("abc").when(uiWrapper).promptForInput(anyString());
        controller.chooseDestination(0);
        verify(uiWrapper, times(1)).showErrorMessage("Not a valid input. Must be an integer!");
    }

    @Test(expected = InvalidInputException.class)
    public void testChooseDestinationInvalidlMove() throws Exception {
        Piece piece = board.getPiece(0);
        board.movePieceTo(0, 25); // move the piece to a position convenient for test
        Assert.assertEquals(25, piece.getPosition());
        doReturn("75").when(uiWrapper).promptForInput(anyString());
        controller.chooseDestination(0);
        verify(uiWrapper, times(1)).showErrorMessage("Not a valid move. Can only be a diagonal or knight move!");
    }

    @Test(expected = InvalidInputException.class)
    public void testChooseDestinationInvalidDiagonalMove() throws Exception {
        Piece piece = board.getPiece(0);
        board.movePieceTo(0, 31); // move the piece to a position convenient for test
        Assert.assertEquals(31, piece.getPosition());
        doReturn("51").when(uiWrapper).promptForInput(anyString());
        controller.chooseDestination(0);
        verify(uiWrapper, times(1)).showErrorMessage("Not a valid move. Can only be a diagonal or knight move!");
    }

    @Test(expected = InvalidInputException.class)
    public void testChooseDestinationInvalidKnightMove() throws Exception {
        Piece piece = board.getPiece(0);
        board.movePieceTo(0, 82); // move the piece to a position convenient for test
        Assert.assertEquals(82, piece.getPosition());
        doReturn("101").when(uiWrapper).promptForInput(anyString());
        controller.chooseDestination(0);
        verify(uiWrapper, times(1)).showErrorMessage("Not a valid move. Can only be a diagonal or knight move!");
    }

}

