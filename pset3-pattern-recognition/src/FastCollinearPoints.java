
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author nitzanf
 */
public class FastCollinearPoints {

    private final ArrayList<LineSegment> segments = new ArrayList<>();
    private final ArrayList<Point[]> pointsOfSegments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        checkForNull(points);
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);
        checkForDuplicates(pointsCopy);

        Point[] pointsBySlope = Arrays.copyOf(pointsCopy, pointsCopy.length);

        for (Point origin : pointsCopy) {
            // sort the points based on the slope from the origin.
            Arrays.sort(pointsBySlope, origin.slopeOrder());

            ArrayList<Point> pointsInSegment = new ArrayList<>();
            double refSlope = origin.slopeTo(pointsBySlope[1]);
            pointsInSegment.add(origin);
            pointsInSegment.add(pointsBySlope[1]); //ref point
            int pointCount = 2; // count origin, ref point

            for (int j = 2; j < pointsCopy.length; j++) {

                Point targetPoint = pointsBySlope[j];
                double targetSlope = origin.slopeTo(targetPoint);

                // check if the reference slope is equal to the target slope, if it is not, then
                // check if there are enough points (4 or more) with the same slope to create a segment.
                // Else clear the points in the segment, add the origin and the target point as the reference.
                if (Double.compare(refSlope, targetSlope) == 0) {
                    // add point to the list of points in the segment
                    pointsInSegment.add(targetPoint);
                    pointCount++;
                    if (pointCount > 3 && j == pointsCopy.length - 1) {
                        addSegment(pointsInSegment.toArray(new Point[pointsInSegment.size()]));
                    }
                } else if (pointCount > 3) {
                    addSegment(pointsInSegment.toArray(new Point[pointsInSegment.size()]));
                    break;
                } else {
                    refSlope = targetSlope;
                    pointsInSegment.clear();
                    pointsInSegment.add(origin);
                    pointsInSegment.add(targetPoint);
                    pointCount = 2;
                }
            }
        }
    }

    private void addSegment(Point[] points) {
        Arrays.sort(points);
        Point start = points[0];
        Point end = points[points.length - 1];
        LineSegment line = new LineSegment(start, end);

        for (Point[] pointsFromSeg : pointsOfSegments) {
            if (pointsFromSeg[0] == start && pointsFromSeg[1] == end) {
                return;
            }
        }
        Point[] seg = new Point[]{start, end};
        pointsOfSegments.add(seg);
        segments.add(line);
    }

    private void checkForNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points cannot be empty.");
        }
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("No null points.");
            }
        }
    }

    private void checkForDuplicates(Point[] sorted) {
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i].compareTo(sorted[i - 1]) == 0) {
                throw new IllegalArgumentException("No duplicate points.");
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] segments_arr = segments.toArray(new LineSegment[segments.size()]);
        return segments_arr;
    }

    public static void main(String[] args) {}
}
