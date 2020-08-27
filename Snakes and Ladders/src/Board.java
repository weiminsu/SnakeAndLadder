import java.util.ArrayList;
import java.util.List;

public class Board {
	
	private List <Snake> snakes;
	private List <Ladder> ladders;
	private List<Piece> pieces;
	private List<SnakeGuard> snakeGuards;
	private Dice dice;

	
	public Board() {
		
		
		snakes = new ArrayList<Snake>();
		ladders = new ArrayList<Ladder>();
		pieces = new ArrayList<Piece>();
		snakeGuards = new ArrayList<SnakeGuard>();
		dice = new Dice();
		
	}
	

	public void add (Snake s) throws SnakePlacementException {
		
		
		if (snakes.size() < 10) {
	
			for (Snake i : snakes) {
				if (s.getHead().equals(i.getHead())) {
					throw new SnakePlacementException();	
				}
			}	
			snakes.add(s);
			
		} else {
			throw new SnakePlacementException();
		}
		
	
	}
	
	public int getSnakeCounts () {
		return snakes.size();
	}
	
	public void add (Ladder l) throws LadderPlacementException {
		
		if (ladders.size() < 10) {
			
			for (Ladder i : ladders) {
				if (l.getBase().equals(i.getBase())) {
					throw new LadderPlacementException();	
				}
			}	
			ladders.add(l);
			
		} else {
			throw new LadderPlacementException();
		}
		
	}
	
	public void add (SnakeGuard sg) throws SnakeGuardPlacementException {
		
		if (snakeGuards.size() < 10) {
			
			for (SnakeGuard i : snakeGuards) {
				if (sg.getPosition() == i.getPosition()) {
					throw new SnakeGuardPlacementException();	
				}
			}	
			snakeGuards.add(sg);
			
		} else {
			throw new SnakeGuardPlacementException();
		}
		
	}
	
	public int getLadderCounts() {
		return ladders.size();
	}
	
	public int getSnakeGaurdCounts() {
		return snakeGuards.size();
	}
	
	public void drawBoard() {
		
	}
	
	public void drawLadder() {
		
	}
	
	public void drawSnake() {
		
	}
	
	public void drawPiece() {
		
	}

	
	
	
	
	
	
}
