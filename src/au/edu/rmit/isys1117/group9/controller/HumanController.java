package au.edu.rmit.isys1117.group9.controller;
import au.edu.rmit.isys1117.group9.exception.InvalidInputException;
import au.edu.rmit.isys1117.group9.exception.NoSuchPieceException;
import au.edu.rmit.isys1117.group9.model.Board;
import au.edu.rmit.isys1117.group9.exception.BoundaryException;
import au.edu.rmit.isys1117.group9.model.Dice;
import au.edu.rmit.isys1117.group9.model.SnakeGuard;
import au.edu.rmit.isys1117.group9.exception.SnakeGuardPlacementException;

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
                if (pieceToMove <= 0 && pieceToMove > board.getPieceCounts())
                    throw new AssertionError();
                return pieceToMove;
            } catch (NumberFormatException | AssertionError e) {
                uiWrapper.showErrorMessage(input + " is not a valid piece number!");
                if(testMode) throw new InvalidInputException();
            }
        }
    }

    public int chooseDestination() throws InvalidInputException {
        while (true) {
            String input = uiWrapper.promptForInput("Please choose the position to move to");
            try {
                int destination = Integer.valueOf(input);
                if(!validateDestination(destination))
                    throw new AssertionError();
                return destination;
            } catch (NumberFormatException | AssertionError e) {
                uiWrapper.showErrorMessage("You can only move within the board");
                if(testMode) throw new InvalidInputException();
            }
        }
    }

    private boolean validateDestination(int destination) {
        if (destination < 0 || destination >= 100)
            return false;
        return true;
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

    public Integer chooseSnakeGuardLocation() {
        return 0;
    }

    public boolean placeSnakeGuard() {
    	while (true) {
            String snakeGuardInput = uiWrapper.promptForInput(
                    "Do you want to place a snake guard this turn?\nEnter the position here, otherwise cancel to continue");
            if (snakeGuardInput == null || snakeGuardInput.isEmpty())
                return false;
            try {
                int position = Integer.valueOf(snakeGuardInput);
                SnakeGuard sg = new SnakeGuard(position,2);
                board.add(sg);
                return true;
            } catch (NumberFormatException | SnakeGuardPlacementException e) {
                uiWrapper.showErrorMessage("You can not place a snake guard here: " + e.getMessage());
                if (testMode) break;
            }
        }
    	return false;
    }

}
