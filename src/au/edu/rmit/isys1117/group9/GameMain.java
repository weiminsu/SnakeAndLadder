package au.edu.rmit.isys1117.group9;

import au.edu.rmit.isys1117.group9.controller.Admin;
import au.edu.rmit.isys1117.group9.controller.HumanController;
import au.edu.rmit.isys1117.group9.controller.IUserInput;
import au.edu.rmit.isys1117.group9.controller.SnakeController;
import au.edu.rmit.isys1117.group9.controller.UIWrapper;
import au.edu.rmit.isys1117.group9.exception.InvalidInputException;
import au.edu.rmit.isys1117.group9.model.Board;

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
    private boolean testMode;

    public GameMain(){
        this.board=new Board(4);
        this.humanController=new HumanController(this.board);
        this.snakeController=new SnakeController(this.board);
        this.admin=new Admin(this.board, this);
        stage = 1;
        turns = 0;
        uiWrapper = new UIWrapper(board);
        testMode = false;
    }

    public void enableTestMode() {
        testMode = true;
    }

    public void startGame() throws InvalidInputException {
        setUp();
        play();
    }

    public boolean setUp() {
        if (!testMode) {
            for (int i = 0; i < 5; i++) {
                plainMessage("Admin please put snake" + (i + 1));
                while (true) {
                    try {
                        admin.putSnake();
                        break;
                    } catch (Exception e) {
                        plainMessage(e.getMessage());
                    }
                }
            }
            for (int i = 0; i < 5; i++) {
                plainMessage("Admin please put ladder" + (i + 1));
                while (true) {
                    try {
                        admin.putLadders();
                        break;
                    } catch (Exception e) {
                        plainMessage(e.getMessage());
                    }
                }
            }
        } else {
            // placeholder
            for (int i = 0; i < 5; i++) {
                //plainMessage("Admin please put snake"+(i+1));
                try {
                    int head = ThreadLocalRandom.current().nextInt(0, 101);
                    int length = ThreadLocalRandom.current().nextInt(1, 31);
                    int tail = head - length;
                    admin.doAddSnake(head, tail);
                    //admin.putSnake();
                } catch (Exception e) {
                    //plainMessage(e.getMessage());
                }
            }
            for (int i = 0; i < 5; i++) {
                try {
                    int top = ThreadLocalRandom.current().nextInt(0, 100);
                    int length = ThreadLocalRandom.current().nextInt(1, 31);
                    int bottom = top - length;
                    admin.doAddLadder(top, bottom);
                } catch (Exception e) {
                }
            }
        }
        stage++;
        return true;
    }

    public boolean play() throws InvalidInputException {
        while(true) {
            // Human playing on even turns and snake playing on odd turns
            if (turns%2 == 0) {
                if (stage == 2) {
                    if (!humanController.placeSnakeGuard()) {
                        int n = humanController.rollDice();
                        int piece = humanController.choosePiece();
                        humanController.move(piece, n);
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

            // after a turn, see if any game rule is triggered, for example, if a human piece is paralyzed, or if a human piece is
            // moving up with a ladder, if the game should proceed to next stage, or if there is a winner of the game and so on
            applyGameRules();
        }
    }

    public void applyGameRules() {

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

}
