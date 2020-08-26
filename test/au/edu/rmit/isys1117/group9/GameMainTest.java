package au.edu.rmit.isys1117.group9;

import au.edu.rmit.isys1117.group9.controller.Admin;
import au.edu.rmit.isys1117.group9.controller.HumanController;
import au.edu.rmit.isys1117.group9.controller.SnakeController;
import au.edu.rmit.isys1117.group9.model.Board;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class GameMainTest {
    private HumanController humanController;
    private Board board;
    private SnakeController snakeController;
    private Admin admin;
    private GameMain gameMain;

    @Before
    public void setUp() {
        humanController = Mockito.mock(HumanController.class);
        board = Mockito.mock(Board.class);
        snakeController = Mockito.mock(SnakeController.class);
        admin = Mockito.mock(Admin.class);
        gameMain = new GameMain();
    }

    @Test
    public void testSetUpSuccess() throws Exception {
        Assert.assertNotNull(admin);
        doNothing().when(admin).setUpBoard();
        Assert.assertTrue(gameMain.setUp());
    }

    @Test
    public void testSetUpFailure() throws Exception {
        Assert.assertNotNull(admin);
        doThrow(new RuntimeException()).when(admin).setUpBoard();
        Assert.assertFalse(gameMain.setUp());
    }

    @Test
    public void testPlaySuccess(){
        Assert.assertNotNull(humanController);
        Assert.assertNotNull(snakeController);
        doReturn(true).when(humanController).moveWithoutRollDice();
        doReturn(true).when(humanController).placeSnakeGuard();
        doReturn(true).when(humanController).rollDiceAndMove();
        Assert.assertTrue(gameMain.play());
    }

    @Test
    public void testPlayFailure(){
        Assert.assertNotNull(humanController);
        Assert.assertNotNull(snakeController);
        doReturn(false).when(humanController).moveWithoutRollDice();
        Assert.assertFalse(gameMain.play());
    }
}
