package model;

public class SnakeGuard {
	private int position;
	private int duration;
	
	
	public SnakeGuard(int position, int duration) {
		this.position = position;
		this.duration = duration;
	}
	
	public int getPosition() {		
		return position;
	}

	
	public int getDuration() {		
		return duration;
	}

}
