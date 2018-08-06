

public class BruteCollinearPoints {
	private LineSegment[] segments;
	private int segmentsIndex = 0;
	private Point[] points;
	private Point[] pointsInSegments;
	
	public BruteCollinearPoints(Point [] points) {
		if (points == null) {
			throw new IllegalArgumentException ("Must have points.");
		}
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points.length; j++) {
				if (points[j] == null) {
					throw new IllegalArgumentException ("Cannot have null points.");
				} else if (i != j && points[i] == points[j]) {
					throw new IllegalArgumentException ("Cannot have duplicate points.");
				}
			}
		}
		this.points = points;
		createSegments();
	}
	
	private void createSegments() {
		segments = new LineSegment[points.length];
		pointsInSegments = new Point[points.length];
		int segmentPointsIndex = 0;
		Point min = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
		Point max = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
		
		for (int p = 0; p < points.length; p++) {
			for (int q = 0; q < points.length; q++) {
				for (int r = 0; r < points.length; r++) {
					for (int s = 0; s < points.length; s++) {
						// check that the points are not the same
						if (p == q || p == r || p == s || q == r || q == s || r == s) {
							continue;
						} else {
							double pToq = points[p].slopeTo(points[q]);
							double pTor = points[p].slopeTo(points[r]);
							double pTos = points[p].slopeTo(points[s]);
							// check for collinearity
							if (pToq == pTor && pToq == pTos) {
								// get smallest and largest points.
								if (points[p].compareTo(min) == -1) {
									min = points[p];
								}
								if (points[q].compareTo(min) == -1) {
									min = points[q];
								}
								if (points[r].compareTo(min) == -1) {
									min = points[r];
								}
								if (points[s].compareTo(min) == -1) {
									min = points[s];
								}
								if (points[p].compareTo(max) == 1) {
									max = points[p];
								}
								if (points[q].compareTo(max) == 1) {
									max = points[q];
								}
								if (points[r].compareTo(max) == 1) {
									max = points[r];
								}
								if (points[s].compareTo(max) == 1) {
									max = points[s];
								}
								
								if (points[p].compareTo(min) == 0 && points[s].compareTo(max) == 0) {
									if (segmentPointsIndex == 0 ) {
										pointsInSegments[segmentPointsIndex++] = min;
										pointsInSegments[segmentPointsIndex++] = max;
										segments[segmentsIndex] = new LineSegment(min, max);
										System.out.println(segments[segmentsIndex]);	
										segmentsIndex++;
																			
									} else if (!pointSegmentExists(min, max)){
										pointsInSegments[segmentPointsIndex++] = min;
										pointsInSegments[segmentPointsIndex++] = max;
										segments[segmentsIndex] = new LineSegment(min, max);
										System.out.println(segments[segmentsIndex]);	
										segmentsIndex++;
									}
								}
							}
						}
					}
				}
			}
		}
	}
		
	private boolean pointSegmentExists(Point min, Point max) {
		boolean minExists = false;
		boolean maxExists = false;
		for (int i = 0; i < pointsInSegments.length; i++) {
			if (min == pointsInSegments[i]) {
				minExists = true;
			} else if (max == pointsInSegments[i]) {
				maxExists = true;
			}
		}
		if (minExists && maxExists) {
			return true;
		}
		return false;
	}
	
	public int numberOfSegments() {
		return segmentsIndex;
	}
	
	public LineSegment[] segments() {
		return segments;
	}
}
