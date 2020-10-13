package au.edu.rmit.isys1117.group9.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import au.edu.rmit.isys1117.group9.exception.SnakeMoveException;
import au.edu.rmit.isys1117.group9.model.Board;
import au.edu.rmit.isys1117.group9.model.Snake;

public class SnakeController {

    private enum Direction {
        topLeft,
        topRight,
        bottomLeft,
        bottomRight
    }

    private Board board;

    private List <Snake> snakes = new ArrayList<Snake>();

    public SnakeController(Board board){
        this.board = board;
        snakes = board.getSnakes();
    }

    public boolean SnakeMove() throws IllegalArgumentException {

        return true;
    }

    public static int getRandomNumBetweenRange(int min, int max) {

        int x = (int) ((Math.random()*((max - min)+1)) + min);

        return x;
    }


    public Snake selectSnakeToMove() {
        int snakeCount = board.getSnakeCounts();

        int snakeNo = getRandomNumBetweenRange(0, snakeCount - 1);


        return snakes.get(snakeNo);
    }


    public void snakeRandomMove(Snake s) {

        try {
            int head = s.getHead();
            int tail = s.getTail();
            final int length = head - tail;
            int y = head % 10;
            int x = head / 10;
            Direction direction = null;


            //set for validate snake head
            HashSet <Integer> bannedPositions = new HashSet<Integer>();
            bannedPositions = board.getCriticalPosition();
            bannedPositions.remove(head);


            //set for validate snake tail
            HashSet <Integer> headPositions = new HashSet<Integer>();
            for(Snake i : snakes) {
                headPositions.add(i.getHead());
            }
            headPositions.remove(head);


            while(true) {

                //random direction
                int directionNo = getRandomNumBetweenRange(0, 3);

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

                if (pos < 0) {
                    continue;
                } else if (pos > 100) {
                    continue;
                } else if (tailPos < 0) {
                    continue;
                } else if (bannedPositions.contains(pos)){

                    throw new SnakeMoveException("Entity overlaps");

                } else if (headPositions.contains(tailPos)){
                    throw new SnakeMoveException("Entity overlaps");

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
