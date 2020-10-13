package au.edu.rmit.isys1117.group9.model;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import exception.LadderPlacementException;
import exception.SnakeGuardPlacementException;
import exception.SnakePlacementException;
import model.Board;
import model.Ladder;
import model.Snake;
import model.SnakeGuard;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class BoardTest {
	
	Board b;
	Snake s1,s2,s3;
	
	Ladder l1,l2,l3;
	SnakeGuard sn1,sn2,sn3;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {	
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {	
	}

	@Before
	public void setUp() throws Exception {
		
		
		b = new Board();
		s1 = Mockito.mock(Snake.class);
		s2 = Mockito.mock(Snake.class);
		s3 = Mockito.mock(Snake.class);
		when(s1.getHead()).thenReturn(6);
		when(s2.getHead()).thenReturn(7);
		when(s3.getHead()).thenReturn(6);
		l1 = Mockito.mock(Ladder.class);
		l2 = Mockito.mock(Ladder.class);
		l3 = Mockito.mock(Ladder.class);
		when(l1.getBottom()).thenReturn(10);
		when(l2.getBottom()).thenReturn(60);
		when(l3.getBottom()).thenReturn(60);
		sn1 = new SnakeGuard(15, 0);
		sn2 = new SnakeGuard(20, 0);
		sn3 = new SnakeGuard(20, 0);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void addSnakePositive() throws SnakePlacementException {
		assertEquals("An empty board", b.getSnakeCounts(), 0);
		b.add(s1);
		assertEquals("Add 1st snake with head 6", b.getSnakeCounts(), 1);
		b.add(s2);
		assertEquals("Add 2nd snake with head 7", b.getSnakeCounts(), 2);
	}
	
	@Test (expected = SnakePlacementException.class)
	public void addSnakeNegative() throws SnakePlacementException {
		assertEquals("An empty board", b.getSnakeCounts(), 0);
		b.add(s1);
		assertEquals("Add 1st snake with head 6", b.getSnakeCounts(), 1);
		b.add(s3);
		assertEquals("Add 2nd snake with head 6 but crashed", b.getSnakeCounts(), 1);
	}
	
	@Test
	public void addLadderPositive() throws LadderPlacementException  {
		assertEquals("An empty board", b.getLadderCounts(), 0);
		b.add(l1);
		assertEquals("Add 1st ladder with base 10", b.getLadderCounts(), 1);
		b.add(l2);
		assertEquals("Add 2nd lader with base 60", b.getLadderCounts(), 2);
	}
	
	@Test (expected = LadderPlacementException.class)
	public void addLadderNegative() throws LadderPlacementException {
		assertEquals("An empty board", b.getLadderCounts(), 0);
		b.add(l2);
		assertEquals("Add 1st ladder with base 60", b.getLadderCounts(), 1);
		b.add(l3);
		assertEquals("Add 2nd ladder with base 60 but crashed", b.getLadderCounts(), 1);
		
	}
	
	@Test
	public void addGuardPositive() throws SnakeGuardPlacementException {
		
		assertEquals("An empty board", b.getSnakeGaurdCounts(), 0);
		b.add(sn1);
		assertEquals("Add 1st guard with base 15", b.getSnakeGaurdCounts(), 1);
		b.add(sn2);
		assertEquals("Add 2nd guard with base 20", b.getSnakeGaurdCounts(), 2);
		
	}
	
	@Test (expected = SnakeGuardPlacementException.class)
	public void addGuardNegative() throws SnakeGuardPlacementException {
		
		assertEquals("An empty board", b.getSnakeGaurdCounts(), 0);
		b.add(sn2);
		assertEquals("Add 1st guard with base 20", b.getSnakeGaurdCounts(), 1);
		b.add(sn3);
		assertEquals("Add 2nd guard with base 20 but crashed", b.getSnakeGaurdCounts(), 1);
		
	}
	
	
	
}
