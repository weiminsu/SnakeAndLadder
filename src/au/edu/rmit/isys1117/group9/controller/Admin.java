package au.edu.rmit.isys1117.group9.controller;

import au.edu.rmit.isys1117.group9.model.Board;
import au.edu.rmit.isys1117.group9.model.Ladder;
import au.edu.rmit.isys1117.group9.model.Snake;

import java.util.ArrayList;

public class Admin {
    private Board board;
    private IUserInput input;
    private ArrayList<Snake> snakes = new ArrayList<Snake>();
    private ArrayList<Ladder> ladders = new ArrayList<Ladder>();


    public Admin(Board board, IUserInput input) {
        this.board = board;
        this.input = input;
    }


    // Create one new snake
    public void putSnake() throws Exception {
        int head = input.getInt("Please enter the head position", 1, 100);
        int tail = input.getInt("Please enter the tail position", 1, 100);
        doAddSnake(head, tail);
    }

    public void doAddSnake(int head, int tail) throws Exception {

    		validateSnake(head, tail);
            Snake snake = new Snake(head, tail);
            snakes.add(snake);
            board.add(snake);
		    	
    }

    public void validateSnake(int head, int tail) throws Exception {

        if (head > 100 || tail < 1) {
            throw new Exception("Snake out of board");
        }

        if (snakes.size() >= 5) {
            throw new Exception("too many snakes");

        }
        if (head - tail <= 0 ) {
            throw new Exception("snake's head must be above tail");
        }
        if ( head - tail > 30) {

            throw new Exception("snake is too long");
        }

        if (head >= 81) {
            for (Snake s : snakes) {
                int tempHead = s.getHead();
                if (tempHead >= 81 && tempHead <= 100) {
                    throw new Exception("Only one snake head can be in 81-100");
                }
            }

        }
        for (Snake s : snakes) {
            int snakeHead = s.getHead();
            int snakeTail = s.getTail();
            if (head == snakeTail || tail == snakeHead) {
                throw new Exception("The snake heads can not connected to others tails");

            }
        }
    }


    public void putLadders() throws Exception {
        int head = input.getInt("Please enter the head position", 1, 100);
        int tail = input.getInt("Please enter the tail position", 1, 100);
        doAddLadder(head, tail);
    }

    public void doAddLadder(int top, int bottom) throws Exception {
        validateLadder(top, bottom);
        Ladder ladder = new Ladder(top, bottom);
        ladders.add(ladder);
        board.add(ladder);
    }

    public void validateLadder(int top, int bottom) throws Exception {

        if (top > 100 || bottom < 1) {
            throw new Exception("Ladder out of board");
        }

        if (ladders.size() >=5) {
            throw new Exception("Too many ladders");

        }

        if (top - bottom <= 0 || top - bottom > 30) {
            throw new Exception("Ladder is too long");
        }


        if (top == 100 || bottom == 1) {
            throw new Exception("Base cannot be 1 and top cannot be 100");

        }
        for (Snake s : snakes) {
            int snakeHead = s.getHead();
            if (top == snakeHead || bottom == snakeHead) {
                throw new Exception("The ladder cannot be connected to snakes head");

            }
        }
        for (Ladder l : ladders) {
            int ladderTop = l.getTop();
            int ladderBase = l.getBottom();
            if (ladderTop == bottom || ladderBase == top||ladderBase==bottom||ladderTop==top) {
                throw new Exception("The ladder heads can not connected to others ladders");

            }
        }
    }


    public void setUpBoard() throws Exception {
        input.plainMessage("please input 5 snakes");
        for (int i = 0; i < 5; i++) {
            putSnake();
        }
        input.plainMessage("please input 5 Ladders");
        for (int i = 0; i < 5; i++) {
            putLadders();
        }
    }
}