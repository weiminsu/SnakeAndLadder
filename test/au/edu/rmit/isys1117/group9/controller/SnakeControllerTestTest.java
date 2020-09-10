package au.edu.rmit.isys1117.group9.controller;

import au.edu.rmit.isys1117.group9.model.Board;
import au.edu.rmit.isys1117.group9.model.Snake;

import static org.junit.jupiter.api.Assertions.*;

class SnakeControllerTestTest {
    @Before
    public void setUp() {
        board = Mockito.mock(Board.class);
        controller = new SnakeController(board);

    }
    @Test
    public void testSnakeMoveSuccess() {
        Assert.assertNotNull(board);
        doNothing().when(board).add(any(Snake.class));
        Assert.assertTrue(controller.move());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testSnakeMoveFailture() {

        Assert.assertNotNull(board);
        doThrow(new RuntimeException()).when(board).add(any(Snake.class));
        Assert.assertFalse(controller.move());

    }

}