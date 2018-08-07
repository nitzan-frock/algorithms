import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	private LineSegment[] segments;
	
	public BruteCollinearPoints(Point [] points) {
		if (points == null) {
			throw new IllegalArgumentException ("Must have points.");
		}

		ArrayList<LineSegment> segmentsList = new ArrayList<>();
		Arrays.sort(points);
		
		for (int p = 0; p < points.length-3; p++) {
			for (int q = p+1; q < points.length-2; q++) {
				for (int r = q+1; r < points.length-1; r++) {
					for (int s = r+1; s < points.length; s++) {
						
						if (points[s] == null) throw new IllegalArgumentException ("Cannot have null points.");
						else if (s != r && points[s] == points[r]) throw new IllegalArgumentException ("Cannot have duplicate points.");
						
						if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r]) && 
								points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {
							LineSegment segment = new LineSegment(points[p], points[s]);
							if (!segmentsList.contains(segment)) {
								System.out.println(segment);
								segmentsList.add(segment);
							}
						}
					}
				}
			}
		}
		
		segments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
	}
	
	public int numberOfSegments() {
		return segments.length;
	}
	
	public LineSegment[] segments() {
		return segments;
	}
}
