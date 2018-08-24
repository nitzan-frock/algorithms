
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> segmentsList = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Must have points.");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("No null points.");
            }
        }
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);
        for (int i = 1; i < pointsCopy.length; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i - 1]) == 0) {
                throw new IllegalArgumentException("No duplicate points.");
            }
        }

        for (int p = 0; p < pointsCopy.length - 3; p++) {
            for (int q = p + 1; q < pointsCopy.length - 2; q++) {
                for (int r = q + 1; r < pointsCopy.length - 1; r++) {
                    for (int s = r + 1; s < pointsCopy.length; s++) {

                        if (pointsCopy[p].slopeTo(pointsCopy[q]) == pointsCopy[p].slopeTo(pointsCopy[r])
                                && pointsCopy[p].slopeTo(pointsCopy[q]) == pointsCopy[p].slopeTo(pointsCopy[s])) {
                            LineSegment segment = new LineSegment(pointsCopy[p], pointsCopy[s]);
                            if (!segmentsList.contains(segment)) {
                                segmentsList.add(segment);
                            }
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segmentsList.size();
    }

    public LineSegment[] segments() {
        LineSegment[] segments_arr = segmentsList.toArray(new LineSegment[segmentsList.size()]);
        return segments_arr;
    }
}
