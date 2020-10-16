package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import exception.SnakePlacementException;
import exception.snakeMoveException;
import model.Board;
import model.Snake;

public class SnakeController {

	private Board board;

	private List <Snake> snakes = new ArrayList<Snake>();

	public SnakeController(Board board){
        this.board = board;
        snakes = board.getSnakes();
    }

    public boolean SnakeMove() throws IllegalArgumentException {

        return true;
    }

    public static int getRondomNumBetweenRange(int min, int max) {

    	int x = (int) ((Math.random()*((max - min)+1)) + min);

    	return x;
    }


    public Snake selectSnakeToMove() {
    	int snakeCount = board.getSnakeCounts();

    	int snakeNo = getRondomNumBetweenRange(0, snakeCount - 1);


    	return snakes.get(snakeNo);
    }


    public void moveall() throws snakeMoveException{

    	for(Snake s: snakes){
    		snakeRandomMove(s);
    	}

    }




    public void snakeRandomMove(Snake s) throws snakeMoveException {

    	try {

    		int head = s.getTop();
        	int tail = s.getBottom();
        	int length = head - tail;
        	int y = head % 10; //Column;
        	int x = head / 10; //row;
        	Direction direction = null;


        	//set for validate snake head
        	HashSet <Integer> bannedPositions = new HashSet<Integer>();
        	bannedPositions = board.getCriticalPosition();
        	bannedPositions.remove(head);


        	//set for validate snake tail
        	HashSet <Integer> headPositions = new HashSet<Integer>();
        	for(Snake i : snakes) {
        		headPositions.add(i.getTop());
        	}
        	headPositions.remove(head);


        	while(true) {

        		//random direction
            	int directionNo = getRondomNumBetweenRange(0, 3);

            	direction = Direction.values()[directionNo];

            	//split head position to 2 digits int;
            	// xy is the pos =====> pos = 10*x + y;
            	int pos = 0;


            	if ((y != 0) && (y != 1)) {

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

        		} else if (y == 0){

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


        		} else {

        			switch (direction) {

                	case topLeft:
                		pos = head + 18;

                		break;
                	case topRight:
                		pos = head + 18;

                		break;
                	case bottomLeft:
                		pos = head - 2;

                		break;
                	case bottomRight:
                		pos = head - 2;

                		break;
                	}

        		}


            	int tailPos = pos - length;

            	if (pos>=100|| pos<=1||tailPos<=1||tailPos>=100) {
					continue;
				} else if (bannedPositions.contains(pos)){

    				throw new snakeMoveException("Entity overlaps");

    			} else if (headPositions.contains(tailPos)){
    				throw new snakeMoveException("Entity overlaps");

    			} else {
    				board.setSnake(head, pos);
    	    		break;
    			}


        	}


		} catch (Exception e) {
			// TODO: handle exception
		}




    }


}
