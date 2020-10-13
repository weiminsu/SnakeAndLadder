package model;

import java.util.ArrayList;
import java.util.List;

public class Square {
    int index;
    List<Piece> pieces;
    SnakeGuard snakeGuard;
    Snake snake; // if not null, this square contains either snake head or snake tail
    boolean isSnakeHead; // if true, this square contains snake head, if false and snake is not null then it contains snake tail
    Ladder ladder; // if not null, this square contains ladder bottom

    public Square(int index) {
        this.index = index;
        pieces = new ArrayList<>();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void addPiece(Piece piece) {
        this.pieces.add(piece);
    }

    public void removePiece(Piece piece) {
        this.pieces.remove(piece);
    }

    public SnakeGuard getSnakeGuard() {
        return snakeGuard;
    }

    public void setSnakeGuard(SnakeGuard snakeGuard) {
        this.snakeGuard = snakeGuard;
    }

    public void removeSnakeGuard() {
        this.snakeGuard = null;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void removeSnake() {
        snake = null;
        isSnakeHead = false;
    }

    public Ladder getLadder() {
        return ladder;
    }

    public void setLadder(Ladder ladder) {
        this.ladder = ladder;
    }

    public boolean isSnakeHead() {
        return isSnakeHead;
    }

    public boolean isSnakeTail() {
        return snake != null && !isSnakeHead;
    }

    public boolean isLadderBottom() {
        return ladder != null;
    }
}