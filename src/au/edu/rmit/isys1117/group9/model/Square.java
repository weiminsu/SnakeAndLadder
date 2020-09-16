package au.edu.rmit.isys1117.group9.model;

import java.util.List;

public class Square {
    int index;
    List<Piece> pieces;
    SnakeGuard snakeGuard;
    Snake snake; // if not null, this square contains snake head
    Ladder ladder; // if not null, this square contains ladder bottom

    public Square(int index) {
        this.index = index;
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

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Ladder getLadder() {
        return ladder;
    }

    public void setLadder(Ladder ladder) {
        this.ladder = ladder;
    }

    public boolean isSnakeHead() {
        return snake != null;
    }

    public boolean isLadderBottom() {
        return ladder != null;
    }
}
