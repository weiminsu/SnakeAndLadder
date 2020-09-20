package au.edu.rmit.isys1117.group9.controller;

import java.util.ArrayList;
import java.util.List;

import au.edu.rmit.isys1117.group9.model.Board;
import au.edu.rmit.isys1117.group9.model.Dice;
import au.edu.rmit.isys1117.group9.model.Snake;

public class SnakeController {

	private Board board;

	private List <Snake> snakes = new ArrayList<Snake>();

	/*public SnakeController(Board board){
        this.board = board;
        snakes = board.getSnakes();
    }*/

    public boolean SnakeMove() throws IllegalArgumentException {

        return true;
    }

    public static int getRondomNumBetweenRange(int min, int max) {

    	int x = (int) ((Math.random()*((max - min)+1)) + min);

    	return x;
    }



    public void snakeRandomMove() {
    	//1stly return snake count in the board.
    	int snakeCount = board.getSnakeCounts();

    	int snakeNo = getRondomNumBetweenRange(0, snakeCount - 1);

    	Snake snake = snakes.get(snakeNo);

    	int head = snake.getHead();
    	int tail = snake.getTail();
    	final int length = head - tail;
    	int y = head % 10;
    	int x = head / 10;
    	Direction direction = null;
    	int pos = 0;

    	while(true) {

    		//random direction
        	int directionNo = getRondomNumBetweenRange(0, 3);

        	direction = Direction.values()[directionNo];

        	//split head position to 2 digits int;
        	// xy is the pos =====> pos = 10*x + y;

        	pos = 0;


        	if (y != 0) {

        		switch (direction) {

            	case topLeft:
            		pos = 10 * (x + 1) + (10 - y);

            		break;
            	case topRight:
            		pos = 10 * (x + 1) + (10 - y) + 2;

            		break;
            	case bottomLeft:
            		pos = x * 10 - y;

            		break;
            	case bottomRight:
            		pos = x * 10 - y + 2;

            		break;
            	}

    		} else {

    			switch (direction) {

            	case topLeft:
            		pos = head + 2;

            		break;
            	case topRight:
            		pos = head + 2;

            		break;
            	case bottomLeft:
            		pos = head - 18;

            		break;
            	case bottomRight:
            		pos = head - 18;

            		break;
            	}


    		}

        	int tailPos = pos - length;

        	if (pos < 0) {
				continue;
			} else if (pos > 100) {
        		continue;
        	} else if (tailPos < 0) {
				continue;
			} else {
				board.setSnake(head, pos);
	    		break;
			}

    	}







    }













}
