package au.edu.rmit.isys1117.group9.controller;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import au.edu.rmit.isys1117.group9.model.Board;
import au.edu.rmit.isys1117.group9.model.Ladder;
import au.edu.rmit.isys1117.group9.model.LadderPlacementException;
import au.edu.rmit.isys1117.group9.model.Snake;
import au.edu.rmit.isys1117.group9.model.SnakeGuard;
import au.edu.rmit.isys1117.group9.model.SnakeGuardPlacementException;
import au.edu.rmit.isys1117.group9.model.SnakePlacementException;
import au.edu.rmit.isys1117.group9.model.snakeMoveException;

public class SnakeControllerTest2 {
	
	private Board b;
	private SnakeController sc;
	private List <Snake> snakes;

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("====================");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("====================");
		b = new Board();
		sc = new SnakeController(b);
		snakes = b.getSnakes();
		
	}
	

	@After
	public void tearDown() throws Exception {
		System.out.println("Test finishes\n");
	}

	@Test
	public void positive1() throws Exception {
		System.out.println("positive test 1");
		assertEquals("An empty board", b.getSnakeCounts(), 0);
		
		Snake s = new Snake(91, 71);
		assertEquals("The snake length is 20",s.getHead() - s.getTail(), 20);
		b.add(s);
		
		//selectSnakeToMove will randomly choose one snake
		//as there is only one snake, s1 and s should refer to the same snake
		Snake s1 = sc.selectSnakeToMove();
		assertTrue("There is only one snake to choose", s == s1);
		
		sc.snakeRandomMove(s1);
		assertEquals("New head position", snakes.get(0).getHead(), 89);
		assertEquals("New tail position", snakes.get(0).getTail(), 69);
		assertEquals("The snake length remains 20",
				snakes.get(0).getHead() - snakes.get(0).getTail(), 20);
	}
	
	@Test
	public void positive2() throws Exception {
		System.out.println("positive test 2");
		assertEquals("An empty board", b.getSnakeCounts(), 0);
		
		Snake s = new Snake(30, 5);
		
		assertEquals("The snake length is 25",s.getHead() - s.getTail(), 25);
		b.add(s);
		
		//select snake is similar to positive test 1
		sc.snakeRandomMove(sc.selectSnakeToMove());

		assertEquals("New head position", snakes.get(0).getHead(), 32);
		assertEquals("New tail position", snakes.get(0).getTail(), 7);
		assertEquals("The snake length remains 25",
				snakes.get(0).getHead() - snakes.get(0).getTail(), 25);
		
	}
	
	@Test (expected = snakeMoveException.class)
	public void negative1() throws Exception {
		System.out.println("negative test 1");
		assertEquals("An empty board", b.getSnakeCounts(), 0);
		
		Snake s = new Snake(45, 25);
		Snake s1 = new Snake(57, 43);   
		Snake s2 = new Snake(37, 23);
		SnakeGuard sg1 = new SnakeGuard(55,3);
		Ladder l1 = new Ladder(53, 35);
		
		b.add(s);
		b.add(s1);
		b.add(s2);
		b.add(sg1);
		b.add(l1);
		System.out.println("Before the exception");
		sc.snakeRandomMove(snakes.get(0));
		System.out.println("This line will never be printed");
		
	}
	
	@Test (expected = snakeMoveException.class)
	public void negative2() throws Exception {
		System.out.println("negative test 2");
		assertEquals("An empty board", b.getSnakeCounts(), 0);
		
		Snake s1 = new Snake(91, 67);
		Snake s2 = new Snake(65, 43);
		
		b.add(s1);
		b.add(s2);
		
		System.out.println("Before the exception");
		sc.snakeRandomMove(snakes.get(0));
		System.out.println("This line will never be printed");
		
	}

}
