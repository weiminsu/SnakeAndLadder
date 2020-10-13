
package controller;

import exception.*;
import model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class HumanController {
    private Board board;
    private UIWrapper uiWrapper;
    private boolean testMode;

    public HumanController(Board board){
        this.board = board;
        uiWrapper =  new UIWrapper(board);
        testMode = false;
    }

    public void enableTestMode() {
        testMode = true;
    }

    // for test purpose
    public void setUiWrapper(UIWrapper uiWrapper) {
        this.uiWrapper = uiWrapper;
    }

    public int rollDice() {
        uiWrapper.showInfoMessage("Click to confirm to roll the dice");
        Dice dice = board.getDice();
        return dice.roll();
    }

    public int choosePiece() throws InvalidInputException {
        while (true) {
            String input = uiWrapper.promptForInput("Please choose the piece to move");
            try {
                int pieceToMove = Integer.valueOf(input)-1;
                if (pieceToMove < 0 || pieceToMove >= board.getPieceCounts())
                    throw new AssertionError();
                return pieceToMove;
            } catch (NumberFormatException | AssertionError e) {
                uiWrapper.showErrorMessage(input + " is not a valid piece number!");
                if(testMode) throw new InvalidInputException();
            }
        }
    }

    public int chooseDestination(int pieceNumber) throws InvalidInputException {
        while (true) {
            String input = uiWrapper.promptForInput("Please choose the position to move to");
            try {
                int destination = Integer.parseInt(input);
                if(!validateDestination(board.getPiece(pieceNumber).getPosition(), destination))
                    throw new AssertionError();
                return destination;
            } catch (NumberFormatException e) {
                uiWrapper.showErrorMessage("Not a valid input. Must be an integer!");
                if(testMode) throw new InvalidInputException();
            } catch (AssertionError e) {
                uiWrapper.showErrorMessage("Not a valid move. Can only be a diagonal or knight move!");
                if(testMode) throw new InvalidInputException();
            }
        }
    }

    private boolean validateDestination(int currentPosition, int destination) {
        int i = currentPosition/10 + 1; // current row number
        int j = i%2==0 ? i*10-currentPosition+1 : currentPosition%10; // current column number
        Set<Integer> validDest = new HashSet<>();
        for(int k=-2; k<=2; k++){
            if (k!=0){
                int destRow = i+k;
                if(destRow>=1 && destRow<=10){
                    int squareUnder = destRow%2 == 0 ? 10*destRow-j+1 : 10*(destRow-1)+j;
                    int lowerBoundary = 10*(destRow-1);
                    int upperBoundary = 10*destRow;
                    if (k==-2 || k==2) {
                        int t = squareUnder+1;
                        if (t <= upperBoundary) {
                            validDest.add(t);
                        }
                        t = squareUnder-1;
                        if (t > lowerBoundary) {
                            validDest.add(t);
                        }
                    } else {
                        for (int t=squareUnder+1; t<=squareUnder+2; t++) {
                            if (t <= upperBoundary) {
                                validDest.add(t);
                            }
                        }
                        for (int t=squareUnder-2; t<=squareUnder-1; t++) {
                            if (t > lowerBoundary) {
                                validDest.add(t);
                            }
                        }
                    }
                }
            }
        }
        return validDest.contains(destination);
    }



    public void move(int pieceToMove, int n) throws IllegalArgumentException {
        try {
            board.movePiece(pieceToMove, n);
        } catch (BoundaryException e) {
            String errorMessage = "You can not move piece " + pieceToMove + " with " + n +
                    " steps. It will be out of boundary.\nPlease select which piece to move again";
            uiWrapper.showErrorMessage(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        } catch (NoSuchPieceException e) {
            String errorMessage = "Invalid piece!";
            uiWrapper.showErrorMessage(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public void moveTo(int pieceToMove, int destination) throws IllegalArgumentException {
        try {
            board.movePieceTo(pieceToMove, destination);
        } catch (BoundaryException e) {
            String errorMessage = "You can not move piece " + pieceToMove + " to  square " + destination +
                    ". It is out of boundary.\nPlease select which piece to move again";
            uiWrapper.showErrorMessage(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        } catch (NoSuchPieceException e) {
            String errorMessage = "Invalid piece!";
            uiWrapper.showErrorMessage(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public Integer chooseSnakeGuardLocation() throws InvalidInputException {
        while (true) {
            String input = uiWrapper.promptForInput("Please choose the position to place a snake OOOuard");
            if (input == null || input.isEmpty()) break;
            try {
                int location = Integer.valueOf(input);
                if(!validateSnakeGuardLocation(location))
                    throw new AssertionError();
                return location;
            } catch (NumberFormatException | AssertionError e) {
                uiWrapper.showErrorMessage("You can not place a snake guard here.");
                if(testMode) throw new InvalidInputException();
            }
        }
        return -1;
    }

    private boolean validateSnakeGuardLocation(int location) {
        Square s = board.getSquare(location);
        return s == null || s.getSnake() == null;
    }

    public boolean placeSnakeGuard(int position) {
        try {
            SnakeGuard sg = new SnakeGuard(position,2);
            board.add(sg);
            return true;
        } catch (SnakeGuardPlacementException e) {
            uiWrapper.showErrorMessage("You can not place a snake guard here: " + e.getMessage());
        }
    	return false;
    }





    public String validatePieceLcations() throws BoundaryException{

    	String message = "";
    	//extract all the snake head and ladder bottom position
    	HashSet<Integer> shp = board.getSnakeheadPos();
    	HashSet<Integer> lbp = board.getLadderbottomPos();

    	//extract all the piece location
    	ArrayList<Integer> pl = board.getPieceLocation();

    	Set <Integer> gonnaAddclimbLadderTime = new LinkedHashSet<Integer>();
    	//compare overlapping
    	for (int i = 0; i < pl.size(); i++) {
    		//drop from snake
			if (shp.contains(pl.get(i))) {
				for (Snake j: board.getSnakes()) {
					if (j.getHead() == pl.get(i)) {
						//move to j.getBottom()

						board.movePieveFromAtoB(pl.get(i), j.getTail());
						message += "Move piece " + pl.get(i) + " to " + j.getTail() + "\n";
					}
				}

			}
    		//climb ladder
			if (lbp.contains(pl.get(i))) {
				for (Ladder m: board.getLadder()) {
					if (m.getBottom() == pl.get(i)) {
						//move to m.getTop()
						gonnaAddclimbLadderTime.add(m.getTop());
						board.movePieveFromAtoB(pl.get(i), m.getTop());
						message += "Move piece " + pl.get(i) + " to " + m.getTop() + "\n";
					}

				}
			}
		}

    	for(int n: gonnaAddclimbLadderTime){
    		board.getPieceByLocation(n);
    		for(Piece i: board.getPieceByLocation(n)){
    			i.addLadderClimb();
    		}
    	}


    	return message;
    }



















}