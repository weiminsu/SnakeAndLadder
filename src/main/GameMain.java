
package main;


import controller.*;
import exception.*;
import model.*;
import exception.*;

import javax.swing.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class GameMain implements IUserInput {
    private HumanController humanController;
    private Board board;
    private SnakeController snakeController;
    private Admin admin;
    static private int stage;
    static boolean godMode = false;
    private int turns;
    private UIWrapper uiWrapper;

    public GameMain() {

    	int numPieces = getInt("How many player want to join this game", 2, 4);

        board = new Board(numPieces);
        this.humanController = new HumanController(this.board);
        this.snakeController = new SnakeController(this.board);
        this.admin = new Admin(this.board, this);
        stage = 1;
        turns = 0;
        uiWrapper = new UIWrapper(board);
    }


    public void startGame() throws InvalidInputException {
        setUp();

    }

    public void setUp() {
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

    public void secondStage() throws SnakeGuardPlacementException, BoundaryException, snakeMoveException  {
    	Boolean ifwin = false;
    	List <Piece> pieces = board.getPiece();


    	//loop 50 rounds;
    	for (int rounds = 0; rounds<50; rounds++){
    		//piece turn;
    		uiWrapper.showInfoMessage("Round " + (rounds + 1));
    		board.clearMessages();
    		for (int i = 0; i < pieces.size(); i++) {
    			uiWrapper.showInfoMessage("Player " + (i + 1) + "'s turn.");
    			if (humanController.ifPlaceGuard()) {
					continue;
				}


    			if (pieces.get(i).isParalyse()) {
    				uiWrapper.showInfoMessage("Player " + (i + 1) + " is paralysed!");
    				board.addMessage("Player " + (i + 1) + " is paralysed!");
    				pieces.get(i).decrementParalyseDuration();
					continue;
				}

        		humanController.movePieceByDice(i);
        		humanController.stage2validatePieceLcations();
        		board.addMessage("Player " + (i+1) + " ladder: " + pieces.get(i).getLadderClimb());
        		if (pieces.get(i).getPosition() == 100) {
        			if (pieces.get(i).getLadderClimb() >= 3) {
        				ifwin = true;
    					break;
					} else {
						uiWrapper.showInfoMessage("You have start from 1!");
						pieces.get(i).setPosition(1);
					}

				}
			}

    		if (ifwin == true) {
				break;
			}
    		//snake turn;
    		snakeController.moveall();
    		humanController.stage2validatePieceLcations();

    	}

    	if (ifwin == true) {
			uiWrapper.showInfoMessage("Stage 2 wins!");
			stage = 3;
		} else {
			uiWrapper.showInfoMessage("Sorry you lost!");
		}



    }

    public void secondStageDeveloper() throws SnakeGuardPlacementException, BoundaryException, snakeMoveException  {


    	Boolean ifwin = false;
    	List <Piece> pieces = board.getPiece();


    	//loop 50 rounds;
    	for (int rounds = 0; rounds<50; rounds++){
    		//piece turn;
    		uiWrapper.showInfoMessage("Round " + (rounds + 1));
    		board.clearMessages();
    		for (int i = 0; i < pieces.size(); i++) {
    			uiWrapper.showInfoMessage("Player " + (i + 1) + "'s turn.");
    			if (humanController.ifPlaceGuard()) {
					continue;
				}


    			if (pieces.get(i).isParalyse()) {
    				uiWrapper.showInfoMessage("Player " + (i + 1) + " is paralysed!");
    				board.addMessage("Player " + (i + 1) + " is paralysed!");
    				pieces.get(i).decrementParalyseDuration();
					continue;
				}

        		humanController.godMove(i);
        		humanController.stage2validatePieceLcations();
        		board.addMessage("Player " + (i+1) + " ladder: " + pieces.get(i).getLadderClimb());
        		if (pieces.get(i).getPosition() == 100) {
        			if (pieces.get(i).getLadderClimb() >= 3) {
        				ifwin = true;
    					break;
					} else {
						uiWrapper.showInfoMessage("You have start from 1!");
						pieces.get(i).setPosition(1);
					}

				}
			}

    		if (ifwin == true) {
				break;
			}
    		//snake turn;
    		snakeController.moveall();
    		humanController.stage2validatePieceLcations();

    	}

    	if (ifwin == true) {
			uiWrapper.showInfoMessage("Stage 2 wins!");
			stage = 3;
		} else {
			uiWrapper.showInfoMessage("Sorry you lost!");
		}


    }

    public void thirdStage() throws InvalidInputException, snakeMoveException{
    	board.clearLadder();
    	board.clearSnakeGaurd();
    	List<Piece> pieces = board.getPiece();
    	for (int rounds = 0; rounds < 20; rounds++) {
    		uiWrapper.showInfoMessage("Round " + (rounds + 1));
    		for (int j = 0; j < pieces.size(); j++) {
    			uiWrapper.showInfoMessage("Player " + (j + 1) + "'s turn.");
				humanController.knightMove(j);
				if (humanController.stage3validatePieceLcations() == false) {
					uiWrapper.showInfoMessage("Sorry! you lost!");
					return;
				}
			}

    		snakeController.moveall();
    		if (humanController.stage3validatePieceLcations() == false) {
				uiWrapper.showInfoMessage("Sorry! you lost!");
				return;
			}

		}

    	uiWrapper.showInfoMessage("Sorry! you lost!");
		return;

    }

    public void thirdStagedeveloper() throws InvalidInputException, snakeMoveException{
    	board.clearLadder();
    	board.clearSnakeGaurd();
    	List<Piece> pieces = board.getPiece();
    	for (int rounds = 0; rounds < 20; rounds++) {
    		uiWrapper.showInfoMessage("Round " + (rounds + 1));
    		for (int j = 0; j < pieces.size(); j++) {
    			uiWrapper.showInfoMessage("Player " + (j + 1) + "'s turn.");
				humanController.godMove(j);
				if (humanController.stage3validatePieceLcations() == false) {
					uiWrapper.showInfoMessage("Sorry! you lost!");
					return;
				}
			}

    		snakeController.moveall();
    		if (humanController.stage3validatePieceLcations() == false) {
				uiWrapper.showInfoMessage("Sorry! you lost!");
				return;
			}

		}

    	uiWrapper.showInfoMessage("Sorry! you lost!");
		return;

    }

    public void ifdeveloper(){
    	int n =uiWrapper.showComfirmDialog("Developer mode?");
    	if (n==0) {
			godMode = true;
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

    public void godMode(){
    	int n = uiWrapper.showComfirmDialog("developer mode?");
    	if (n == 0) {
			godMode = true;
		}

    }



    // This method constructs a SLGame object and calls its control method
    public static void main(String args[]) throws Exception {
        GameMain gameMain = new GameMain();
        gameMain.startGame();

        gameMain.godMode();

        if (godMode == false) {
        	gameMain.secondStage();
        	if (stage == 3) {
    			gameMain.thirdStage();
    		}
		} else {
			gameMain.secondStageDeveloper();
			if (stage == 3) {
    			gameMain.thirdStagedeveloper();
    		}
		}




    }
}