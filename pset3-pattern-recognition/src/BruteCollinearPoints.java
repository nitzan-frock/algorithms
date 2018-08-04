
public class BruteCollinearPoints {
	private LineSegment[] segments;
	private int segmentsIndex;
	private Point[] points;
	
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
	}
	
	private void createSegments() {
		int numOfCollinearPoints = 4;
		segments = new LineSegment[points.length/numOfCollinearPoints];
		
		for (int p = 0; p < points.length; p++) {
			for (int q = 0; q < points.length; q++) {
				for (int r = 0; r < points.length; r++) {
					for (int s = 0; s < points.length; s++) {				
						double pToq = points[p].slopeTo(points[q]);
						double pTor = points[p].slopeTo(points[r]);
						double pTos = points[p].slopeTo(points[s]);
						// check for collinearity
						if (pToq == pTor && pToq == pTos) {
							// get smallest and largest points.
							if (points[p].compareTo(points[q]) == -1 && 
									points[p].compareTo(points[r]) == -1 && 
									points[p].compareTo(points[s]) == -1){
								segments[segmentsIndex] = new LineSegment(points[p], points[s]);
								segmentsIndex++;
							}
						}
					}
				}
			}
		}
	}
	
	public int numberOfSegments() {
		return segmentsIndex+1;
	}
	
	public LineSegment[] segments() {
		createSegments();
		return segments;
	}
}
