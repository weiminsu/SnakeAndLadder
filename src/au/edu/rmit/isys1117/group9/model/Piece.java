package au.edu.rmit.isys1117.group9.model;

import au.edu.rmit.isys1117.group9.exception.BoundaryException;

import java.util.HashSet;
import java.util.Set;

public class Piece {

	private int position;
	private int paralyseDuration;
	private Set<Ladder> laddersClimbed;

	public Piece() {
		position = 1;
		paralyseDuration = 0;
		laddersClimbed = new HashSet<>();
	}

	public int getDistinctLaddersClimbed() {
		return laddersClimbed.size();
	}

	public void addLadderClimbed(Ladder ladder) {
		this.laddersClimbed.add(ladder);
	}
	
	public void setPosition(int n) {
		position = n;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void move(int n) throws BoundaryException {
		
		if ((position + n) > 100 || (position + n) < 0) {
			throw new BoundaryException();
		} else {
			position += n;
		}
		
	}
	
	public void paralyse() {
		paralyseDuration = 1;
	}
	
	public boolean isParalyse() {
		return paralyseDuration != 0;
	}
	
	public void decrementParalyseDuration(){
		paralyseDuration--;
	}
	
	
	
}

