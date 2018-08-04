import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.In;

class BruteCollinearPointsTest {

	@BeforeEach
	void setUp(String[] args) throws Exception {
		In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testNumberOfSegments() {
		fail("Not yet implemented");
	}

	@Test
	void testSegments() {
		fail("Not yet implemented");
	}

}
