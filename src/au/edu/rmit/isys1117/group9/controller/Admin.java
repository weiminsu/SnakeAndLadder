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

        if (snakes.size() >= 5) {
            throw new Exception("too many snakes");

        }
        if (head - tail <= 0 || head - tail > 30) {
            throw new Exception("snake is too long");
        }


        if (head >= 81 && head <= 100) {
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
                throw new Exception("The snake heads can not connected others tails");

            }
        }
    }


    public void setUpBoard() throws Exception {
        input.plainMessage("please input 5 snakes");
        for (int i = 0; i < 5; i++) {
            putSnake();

        }
    }
}
