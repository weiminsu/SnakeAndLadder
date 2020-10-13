package au.edu.rmit.isys1117.group9.model;

public class Piece {

	private int position;
	private int paralyseDuration;

	public Piece() {
		position = 1;
		paralyseDuration = 0;
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
		paralyseDuration = 1;
	}
	
	public boolean isParalyse() {
		return paralyseDuration != 0? true: false;

	}
	
	public void decrementParalyseDuration(){
		paralyseDuration++;
	}
	
	
	
}

