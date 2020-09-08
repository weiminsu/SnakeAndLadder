package au.edu.rmit.isys1117.group9;

import au.edu.rmit.isys1117.group9.controller.Admin;
import au.edu.rmit.isys1117.group9.controller.HumanController;
import au.edu.rmit.isys1117.group9.controller.IUserInput;
import au.edu.rmit.isys1117.group9.controller.SnakeController;
import au.edu.rmit.isys1117.group9.model.Board;

import javax.swing.*;

public class GameMain implements IUserInput{
    private HumanController humanController;
    private Board board;
    private SnakeController snakeController;
    private Admin admin;
    private int stage;
    private int steps;

    public GameMain(){
        this.board=new Board(4);
        this.humanController=new HumanController(this.board);
        this.snakeController=new SnakeController(this.board);
        this.admin=new Admin(this.board, this) ;{

        }
    }

    public void startGame(){
        setUp();
    }

    public boolean setUp() {
        // placeholder
        for (int i = 0; i < 5; i++) {
            plainMessage("Admin please put snake"+(i+1));
            while(true){
                try{
                    admin.putSnake();
                    break;
                }catch (Exception e){
                    plainMessage(e.getMessage());
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            plainMessage("Admin please put ladder"+(i+1));
            while(true){
                try{
                    admin.putLadders();
                    break;
                }catch (Exception e){
                    plainMessage(e.getMessage());
                }
            }
        }

        return true;
    }

    public boolean play(){
        // placeHolder;
        return true;
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
