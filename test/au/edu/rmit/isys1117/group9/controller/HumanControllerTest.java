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
        // add two human pieces for testing
        board.add(new Piece());
        board.add(new Piece());
        dice = Mockito.mock(Dice.class);
        uiWrapper = Mockito.mock(UIWrapper.class);
        controller = new HumanController(board);
        controller.enableTestMode();
        when(board.getDice()).thenReturn(dice);
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
    public void testChooseDestinationSuccess() throws Exception {
        doReturn("50").when(uiWrapper).promptForInput(anyString());
        Assert.assertEquals(50, controller.chooseDestination());
    }

    @Test(expected = InvalidInputException.class)
    public void testChooseDestinationFailure() throws Exception {
        doReturn("101").when(uiWrapper).promptForInput(anyString());
        controller.chooseDestination();
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
    public void testPlaceSnakeGuardSuccess() throws SnakeGuardPlacementException {
        Assert.assertNotNull(board);
        doNothing().when(board).add(any(SnakeGuard.class));
        controller.placeSnakeGuard();
    }

    @Test
    public void testPlaceSnakeGuardFailure() throws SnakeGuardPlacementException {
        Assert.assertNotNull(board);
        doThrow(new RuntimeException()).when(board).add(any(SnakeGuard.class));
        controller.placeSnakeGuard();
    }

    @Test
    public void testMoveWithoutDiceSuccess() throws Exception {
        Assert.assertNotNull(board);
        doNothing().when(board).add(any(Piece.class));
        controller.moveTo(anyInt(), anyInt());
    }

    @Test
    public void testMoveWithoutDiceFailure() throws Exception {
        Assert.assertNotNull(board);
        doThrow(new RuntimeException()).when(board).add(any(Piece.class));
        controller.moveTo(anyInt(), anyInt());
    }
}

