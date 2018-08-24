import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FastCollinearPointsTest {
	Point[] points;
	FastCollinearPoints fast;
	
	@BeforeEach
	void setUp() throws Exception {
		System.out.println("Setup...");
		Scanner sc = new Scanner(new File("./testsrc/testData/input100.txt"));
		int i = 0;
		int n = sc.nextInt();
		points = new Point[n];
		while (sc.hasNextInt()) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			points[i] = new Point(x, y);
			i++;
		}
		sc.close();
		fast = new FastCollinearPoints(points);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testNumberOfSegments() {
		int result = 4;
		assertEquals(result, fast.numberOfSegments(), "Shows number of segments.");
	}

	@Test
	void testSegments() {
		fail("Not yet implemented");
	}

}
