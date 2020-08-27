import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
		p.move(6);
		assertEquals("Move 6 from 1", p.getPosition(), 7);
	}
	@Test (expected = BoundaryException.class)
	public void moveNegative() throws BoundaryException {
		p.move(100);
		assertEquals("Out of boundary", p.getPosition(), 1);
	}
	
	@Test
	public void paralysePositive() throws BoundaryException {
		p.paralyse();
		assertTrue(p.isParalyse());
	}
	
	@Test
	public void paralyseNegative() throws BoundaryException {
		Piece p1 = new Piece();
		assertFalse(p1.isParalyse());
	}
	
}
