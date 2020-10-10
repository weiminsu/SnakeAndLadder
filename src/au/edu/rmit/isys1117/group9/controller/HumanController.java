package au.edu.rmit.isys1117.group9.controller;
import java.util.ArrayList;
import java.util.HashSet;

import au.edu.rmit.isys1117.group9.model.*;


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


    public String validatePieceLcations(){

    	String message = "";
    	//extract all the snake head and ladder bottom position
    	HashSet<Integer> shp = board.getSnakeheadPos();
    	HashSet<Integer> lbp = board.getLadderbottomPos();

    	//extract all the piece location
    	ArrayList<Integer> pl = board.getPieceLocation();

    	//compare overlapping
    	for (int i = 0; i < pl.size(); i++) {
    		//drop from snake
			if (shp.contains(pl.get(i))) {
				for (Snake j: board.getSnakes()) {
					if (j.getHead() == pl.get(i)) {
						//move to j.getBottom()
						//?
						message += "Move piece " + pl.get(i) + " to " + j.getTail() + "\n";
					}
				}

			}
    		//climb ladder
			if (lbp.contains(pl.get(i))) {
				for (Ladder m: board.getLadder()) {
					if (m.getBottom() == pl.get(i)) {
						//move to m.getTop()
						//?
						message += "Move piece " + pl.get(i) + " to " + m.getTop() + "\n";
					}

				}
			}



		}

    	return message;


    }

}
