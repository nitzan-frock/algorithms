import java.util.ArrayList;

public class BruteCollinearPoints {
	private ArrayList<LineSegment> segments;
	
	public BruteCollinearPoints(Point [] points) {
		if (points == null) {
			throw new IllegalArgumentException ("Must have points.");
		}
		
		for (int p = 0; p < points.length; p++) {
			for (int q = 0; q < points.length; q++) {
				for (int r = 0; r < points.length; r++) {
					for (int s = 0; s < points.length; s++) {
						if (points[s] == null) throw new IllegalArgumentException ("Cannot have null points.");
						else if (s != r && points[s] == points[r]) throw new IllegalArgumentException ("Cannot have duplicate points.");
						
						if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r]) && 
								points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {
							
						}
					}
				}
			}
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
	}
	
	public int numberOfSegments() {
		return segments.size();
	}
	
	public LineSegment[] segments() {
		return segments;
	}
}
