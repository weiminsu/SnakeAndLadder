package au.edu.rmit.isys1117.group9.main;

import au.edu.rmit.isys1117.group9.controller.*;
import au.edu.rmit.isys1117.group9.exception.InvalidInputException;
import au.edu.rmit.isys1117.group9.model.Board;
import au.edu.rmit.isys1117.group9.model.Piece;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameMain implements IUserInput {
    private HumanController humanController;
    private Board board;
    private SnakeController snakeController;
    private Admin admin;
    private int stage;
    private int turns;
    private UIWrapper uiWrapper;

    public GameMain() {
        this.board = new Board(0);
        this.humanController = new HumanController(this.board);
        this.snakeController = new SnakeController(this.board);
        this.admin = new Admin(this.board, this);
        stage = 1;
        turns = 0;
        uiWrapper = new UIWrapper(board);
    }


    public void startGame() throws InvalidInputException {
        setUp();
        play();
    }

    public void setUp() {

        int numPieces = getInt("How many player want to join this game", 2, 4);
        try {
            for (int i = 0; i < numPieces; i++) {
                this.board.add(new Piece());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // placeholder
        for (int i = 0; i < 5; i++) {
            //plainMessage("Admin please put snake" + (i + 1));
            while (true) {
                try {
                    int head = ThreadLocalRandom.current().nextInt(0, 101);
                    int length = ThreadLocalRandom.current().nextInt(1, 31);
                    int tail = head - length;
                    admin.doAddSnake(head, tail);
                    break;
                } catch (Exception e) {
                    //   plainMessage(e.getMessage());
                }
            }
        }


        for (int i = 0; i < 5; i++) {
            while (true) {
                try {
                    int top = ThreadLocalRandom.current().nextInt(0, 100);
                    int length = ThreadLocalRandom.current().nextInt(1, 31);
                    int bottom = top - length;
                    admin.doAddLadder(top, bottom);
                    break;
                } catch (Exception e) {
                    //  plainMessage(e.getMessage());
                }
            }
        }
        stage = 2;
    }

    public boolean play() throws InvalidInputException {
        while (true) {
            // Human playing on even turns and snake playing on odd turns
            if (turns % 2 == 0) {
                if (stage == 2) {
                    int snakeGuardPos = humanController.chooseSnakeGuardLocation();
                    if (snakeGuardPos == -1) {
                        int n = humanController.rollDice();
                        int piece = humanController.choosePiece();
                        humanController.move(piece, n);
                    } else {
                        humanController.placeSnakeGuard(snakeGuardPos);
                    }

                } else if (stage == 3) {
                    int piece = humanController.choosePiece();
                    int destination = humanController.chooseDestination();
                    humanController.moveTo(piece, destination);
                }
            } else {
                // placeholder
                snakeController.move();
            }
        }
    }

    // A method to print a message and to read an int value in the range specified
    @Override
    public int getInt(String message, int from, int to) {
        String s;
        int n = 0;
        boolean invalid;
        do {
            invalid = false;
            s = (String) JOptionPane.showInputDialog(
                    this.board, message, "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE);
            try {
                n = Integer.parseInt(s);
                if (n < from || n > to)
                    plainMessage("Re-enter: Input not in range " + from + " to " + to);
            } catch (NumberFormatException nfe) {
                plainMessage("Re-enter: Invalid number");
                invalid = true;
            }
        } while (invalid || n < from || n > to);
        return n;
    }

    // A method to print a message and to read a String
    public String getString(String message) {
        String s = (String) JOptionPane.showInputDialog(
                this.board, message, "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE);
        return s;
    }

    // A method to print a message
    public void plainMessage(String message) {
        JOptionPane.showMessageDialog(this.board,
                message, "A prompt message",
                JOptionPane.PLAIN_MESSAGE);
    }


    // This method constructs a SLGame object and calls its control method
    public static void main(String args[]) throws Exception {
        GameMain gameMain = new GameMain();
        gameMain.startGame();

    }
}

