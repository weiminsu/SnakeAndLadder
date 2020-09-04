package au.edu.rmit.isys1117.group9.controller;
import au.edu.rmit.isys1117.group9.model.Board;
import au.edu.rmit.isys1117.group9.model.Dice;
import au.edu.rmit.isys1117.group9.model.SnakeGuard;

public class HumanController {
    private Board board;

    public HumanController(Board board){
        this.board = board;
    }

    public boolean rollDiceAndMove() throws IllegalArgumentException {
        // placeholder only, replace with real implementations
        Dice dice = board.getDice();
        try {
            return dice.roll() >= 1 && dice.roll() <= 6;
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid input");
        }
    }

    public boolean placeSnakeGuard(){
        // placeholder only, replace with real implementations
    	SnakeGuard sg = new SnakeGuard(1,2);
        try {
            board.add(sg);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean moveWithoutRollDice(){
        // placeholder only, replace with real implementations
        return true;
    }



}
