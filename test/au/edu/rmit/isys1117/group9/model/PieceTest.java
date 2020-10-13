package au.edu.rmit.isys1117.group9.model;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exception.BoundaryException;
import model.Piece;

public class PieceTest {
	Piece p;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		p = new Piece();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void movePositive() throws BoundaryException {
		p.setPosition(6);
		assertEquals("set position to 6", p.getPosition(), 6);
		p.move(6);
		assertEquals("Move 6 from 6", p.getPosition(), 12);
	}
	@Test (expected = BoundaryException.class)
	public void moveNegative() throws BoundaryException {
		assertEquals("default piece position is 1", p.getPosition(), 1);
		p.move(100);
		assertEquals("Out of boundary, no action to the piecee", p.getPosition(), 1);
	}
	
	@Test
	public void paralyseNegative() throws BoundaryException {
		Piece piece = new Piece();
		assertFalse("Default pie is unparalysed", piece.isParalyse());
	}
	
	
	@Test
	public void paralysePositive() throws BoundaryException {
		assertFalse("Default pie is unparalysed", p.isParalyse());
		p.paralyse();
		assertTrue(p.isParalyse());
	}
	
	
	
}
