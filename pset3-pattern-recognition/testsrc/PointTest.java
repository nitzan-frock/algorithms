import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointTest {
	
	private Point p1;
	private Point p2;
	private Point p3;
	private Point p4;
	private Point p5;
	private Point p6;

	@BeforeEach
	void setUp() throws Exception {
		p1 = new Point(1, 1);
		p2 = new Point(2, 3);
		p3 = new Point(1, 3);
		p4 = new Point(4, 5);
		p5 = new Point(9000,32200);
		p6 = new Point(400, 800);
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}

	@Test
	void testSlopeTo() {
		assertAll("Slopes", 
				() -> {
					double result = (3.0-1.0)/(2.0-1.0);
					assertEquals(result, p1.slopeTo(p2), "The slope between (1, 1) and (2, 3)");
				},
				() -> {
					double result = (5.0-1.0)/(4.0-1.0);
					assertEquals(result, p1.slopeTo(p4), "The slope between (1, 1) and (4, 5)");
				},
				() -> {
					double result = Double.POSITIVE_INFINITY;
					assertEquals(result, p1.slopeTo(p3), "Vertical slope between (1, 1) and (1, 3)");
				},
				() -> {
					double result = +0.0;
					assertEquals(result, p2.slopeTo(p3), "Horizontal slope between (2, 3) and (1, 3)");
				},
				() -> {
					double result = Double.NEGATIVE_INFINITY;
					assertEquals(result, p2.slopeTo(p2), "Same point (2, 3)");
				},
				() -> {
					double result = (32200.0-800.0)/(9000.0-400.0);
					assertEquals(result, p5.slopeTo(p6), "slope of p5 to p6");
				}
		);
	}

	@Test
	void testCompareTo() {
		assertAll("Comparing points", 
				() -> {
					int result = -1;
					assertEquals(result, p1.compareTo(p2), "(1, 1) < (2, 3)");
				},
				() -> {
					int result = 1;
					assertEquals(result, p2.compareTo(p3), "(2, 3) > (1, 3)");
				},
				() -> {
					int result = 0;
					assertEquals(result, p2.compareTo(p2), "Same point (2, 3)");
				}
		);
	}

	@Test
	void testSlopeOrder() {
		assertAll("Comparing slopes", 
				() -> {
					int result = 1;
					assertEquals(result, p1.slopeOrder().compare(p2, p4), "slope of (2, 3) to (1, 1) < slope of (4, 5) to (1, 1)");
				},
				() -> {
					int result = -1;
					assertEquals(result, p1.slopeOrder().compare(p4, p2), "slope of (4, 5) to (1, 1) < slope of (2, 3) to (1, 1)");
				},
				() -> {
					int result = 0;
					assertEquals(result, p1.slopeOrder().compare(p2, p2), "slope of (4, 5) to (1, 1) < slope of (2, 3) to (1, 1)");
				}
		);
	}

}
