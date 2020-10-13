package au.edu.rmit.isys1117.group9;

import au.edu.rmit.isys1117.group9.controller.Admin;
import au.edu.rmit.isys1117.group9.controller.HumanController;
import au.edu.rmit.isys1117.group9.controller.IUserInput;
import au.edu.rmit.isys1117.group9.controller.SnakeController;
import au.edu.rmit.isys1117.group9.controller.UIWrapper;
import au.edu.rmit.isys1117.group9.exception.BoundaryException;
import au.edu.rmit.isys1117.group9.exception.InvalidInputException;
import au.edu.rmit.isys1117.group9.exception.NoSuchPieceException;
import au.edu.rmit.isys1117.group9.model.Board;
import au.edu.rmit.isys1117.group9.model.Ladder;
import au.edu.rmit.isys1117.group9.model.Piece;
import au.edu.rmit.isys1117.group9.model.Snake;
import au.edu.rmit.isys1117.group9.model.Square;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class GameMain implements IUserInput {
    private HumanController humanController;
    private Board board;
    private SnakeController snakeController;
    private Admin admin;
    private int stage;
    private int turns;
    private boolean humanWins;
    private boolean snakeWins;
    private UIWrapper uiWrapper;
    private boolean testMode;

    public GameMain(){
        this.board=new Board(4);
        this.humanController=new HumanController(this.board);
        this.snakeController=new SnakeController(this.board);
        this.admin=new Admin(this.board, this);
        stage = 1;
        turns = 0;
        humanWins = false;
        snakeWins = false;
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
                boolean success = false;
                while (!success) {
                    try {
                        int head = ThreadLocalRandom.current().nextInt(0, 101);
                        int length = ThreadLocalRandom.current().nextInt(1, 31);
                        int tail = head - length;
                        admin.doAddSnake(head, tail);
                        success = true;
                        //admin.putSnake();
                    } catch (Exception e) {
                        //plainMessage(e.getMessage());
                    }
                }
            }
            for (int i = 0; i < 5; i++) {
                boolean success = false;
                while (!success) {
                    try {
                        int top = ThreadLocalRandom.current().nextInt(0, 100);
                        int length = ThreadLocalRandom.current().nextInt(1, 31);
                        int bottom = top - length;
                        admin.doAddLadder(top, bottom);
                        success = true;
                    } catch (Exception e) {
                        success = false;
                    }
                }
            }
        }
        stage++;
        return true;
    }

    public void play() throws InvalidInputException {
        while(!humanWins && !snakeWins) {
            // Human playing on even turns and snake playing on odd turns
            if (turns%2 == 0) {
                if (stage == 2) {
                    int snakeGuardPos = humanController.chooseSnakeGuardLocation();
                    if (snakeGuardPos == -1) {
                        int n = 0;
                        if (testMode) {
                            n = Integer.parseInt(uiWrapper.promptForInput("Test Mode. Enter steps to move (instead of rolling dice)"));
                        } else {
                            n = humanController.rollDice();
                        }
                        int piece = humanController.choosePiece();
                        humanController.move(piece, n);
                    } else {
                        humanController.placeSnakeGuard(snakeGuardPos);
                    }
                } else if (stage == 3) {
                    int piece = humanController.choosePiece();
                    int destination = humanController.chooseDestination(piece);
                    humanController.moveTo(piece, destination);
                }
            } else {
                // placeholder
                snakeController.snakeRandomMove(snakeController.selectSnakeToMove());
            }
            turns++;
            // after a turn, see if any game rule is triggered, for example, if a human piece is paralyzed, or if a human piece is
            // moving up with a ladder, if the game should proceed to next stage, or if there is a winner of the game and so on
            try {
                applyGameRules();
            } catch (BoundaryException | NoSuchPieceException e) {
                throw new InvalidInputException();
            }
        }
        if (humanWins) {
            uiWrapper.showInfoMessage("Human Wins!");
        } else {
            uiWrapper.showInfoMessage("Snake Wins!");
        }
    }

    public void applyHumanPieceOnSnakeHeadRule(int pieceNumber, int position) throws BoundaryException, NoSuchPieceException {
        Square square = board.getSquare(position);
        if (square.isSnakeHead()) {
            if (this.stage == 2) {
                board.movePieceTo(pieceNumber, square.getSnake().getTail());
            } else if (this.stage == 3) {
                snakeWins = true;
                board.addMessage("Snake eats human!");
            }
        }
    }

    public void applyHumanPieceOnSnakeTailRule(int position) {
        if (stage == 3) {
            Square square = board.getSquare(position);
            if (square.isSnakeTail()) {
                square.removeSnake();
                board.removeSnake(square.getSnake());
                board.addMessage("Human kills snake!");
                if (board.getSnakeCounts() == 0) {
                    humanWins = true;
                    board.addMessage("All snakes killed!");
                }
            }
        }
    }

    public void applyHumanPieceOnLadderBottomRule(int pieceNumber, int position) throws BoundaryException, NoSuchPieceException{
        Square square = board.getSquare(position);
        if (square.isLadderBottom()) {
            Ladder ladder = square.getLadder();
            board.movePieceTo(pieceNumber, square.getLadder().getTop());
            board.getPiece(pieceNumber).addLadderClimbed(ladder);
        }
    }

    public void applyHumanPieceReachedDestinationRule(Piece piece) {
        if (this.stage == 2 && piece.getPosition() == 100) {
            if (piece.getDistinctLaddersClimbed() >= 3) {
                this.stage = 3;
                board.addMessage("Stage 3 Starts");
            }
        }
    }

//    public String validatePieceLocations() throws BoundaryException, NoSuchPieceException {
//
//        String message = "";
//        //extract all the snake head and ladder bottom position
//        HashSet<Integer> shp = board.getSnakeheadPos();
//        HashSet<Integer> lbp = board.getLadderbottomPos();
//
//        //extract all the piece location
//        ArrayList<Integer> pl = board.getPieceLocation();
//
//        Set<Integer> gonnaAddclimbLadderTime = new LinkedHashSet<>();
//        //compare overlapping
//        for (int i = 0; i < pl.size(); i++) {
//            //drop from snake
//            if (shp.contains(pl.get(i))) {
//                for (Snake j: board.getSnakes()) {
//                    if (j.getHead() == pl.get(i)) {
//                        //move to j.getBottom()
//
//                        board.movePieveFromAtoB(pl.get(i), j.getTail());
//                        message += "Move piece " + pl.get(i) + " to " + j.getTail() + "\n";
//                    }
//                }
//
//            }
//            //climb ladder
//            if (lbp.contains(pl.get(i))) {
//                for (Ladder m: board.getLadders()) {
//                    if (m.getBottom() == pl.get(i)) {
//                        //move to m.getTop()
//                        gonnaAddclimbLadderTime.add(m.getTop());
//                        board.movePieveFromAtoB(pl.get(i), m.getTop());
//                        message += "Move piece " + pl.get(i) + " to " + m.getTop() + "\n";
//                    }
//
//                }
//            }
//        }
//
//        for(int n: gonnaAddclimbLadderTime){
//            board.getPieceByLocation(n);
//            for(Piece i: board.getPieceByLocation(n)){
//                i.addLadderClimb();
//            }
//        }
//
//        return message;
//    }

    public void applyMaxTurnsRule() {
        if (this.stage == 2) {
            if (turns >= 99)
                snakeWins = true;
        } else {
            if (turns >= 139) {
                if (!snakeWins) {
                    humanWins = true;
                }
            }
        }
    }

    public void applyGameRules() throws BoundaryException, NoSuchPieceException{
        for (int pieceNumber=0; pieceNumber<board.getPieceCounts(); pieceNumber++) {
            Piece p = board.getPiece(pieceNumber);
            applyHumanPieceOnSnakeHeadRule(pieceNumber, p.getPosition());
            applyHumanPieceOnSnakeTailRule(p.getPosition());
            applyHumanPieceOnLadderBottomRule(pieceNumber, p.getPosition());
            applyHumanPieceReachedDestinationRule(p);
        }
        applyMaxTurnsRule();
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
