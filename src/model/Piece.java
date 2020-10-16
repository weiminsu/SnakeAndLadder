package model;

import exception.BoundaryException;

public class Piece {

	private int position;
	private int paralyseDuration;
	private int ladderClimb;

	public Piece() {
		position = 1;
		paralyseDuration = 0;
		ladderClimb = 0;
	}

	public int getLadderClimb() {
		return ladderClimb;
	}

	public void addLadderClimb() {
		this.ladderClimb ++;
	}

	public void setPosition(int n) {
		position = n;
	}

	public int getPosition() {
		return position;
	}

	public void move(int n) throws BoundaryException {

		if ((position + n) > 100) {
			throw new BoundaryException();
		} else {
			position += n;
		}

	}

	public void paralyse() {
		paralyseDuration = 2;
	}
	

	
	
	public boolean isParalyse() {
		if (paralyseDuration != 0) {
			return true;
		} else {
			return false;
		}
		
	
	}

	public void decrementParalyseDuration(){
		if (paralyseDuration == 0) {
			return;
		}
		paralyseDuration--;
	}



}

