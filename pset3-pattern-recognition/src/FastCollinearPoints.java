import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author nitzanf
 */
public class FastCollinearPoints {
    ArrayList<LineSegment> segments = new ArrayList<>();
    
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Points cannot be empty.");
        Arrays.sort(points);
        for (int i = 1; i < points.length; i++){
            if (points[i].compareTo(points[i-1]) == 0) throw new IllegalArgumentException("No duplicate points.");
        }

        Point[] pointsBySlope = Arrays.copyOf(points, points.length);
        
        for (int i = 0; i < points.length; i++){
            System.out.println("group: " + i);
            Point origin = points[i];
            // sort the points based on the slope from the origin.
            Arrays.sort(pointsBySlope, origin.slopeOrder());
            
            ArrayList<Point> pointsInSegment = new ArrayList<>();
            double refSlope = origin.slopeTo(pointsBySlope[1]);
            pointsInSegment.add(origin);
            pointsInSegment.add(pointsBySlope[1]); //ref point
            int pointCount = 2; // count origin, ref point
            
            for (int j = 2; j < points.length; j++){
                Point nextPoint = pointsBySlope[j];
                double targetSlope = origin.slopeTo(nextPoint);
                // check if the reference is equal to the target, if it is not then
                // check if there are enough points (4 or more) with the same slope to create a segment.
                // Then move the reference to the target.
                if (refSlope == targetSlope){
                	// add point to the list of points in the segment
                	pointsInSegment.add(nextPoint);
                	pointCount++;
                } else if (pointCount > 3){
                        addSegment(pointsInSegment.toArray(new Point[pointsInSegment.size()]));
                        break;
                 } else {
                    refSlope = targetSlope;
                    pointsInSegment.clear();
                    pointsInSegment.add(origin);
                    pointsInSegment.add(nextPoint);
                    pointCount = 2;
                 }
            }
        }
    }
    
    private void addSegment(Point[] points) {
    	Arrays.sort(points);
    	LineSegment line = new LineSegment(points[0], points[points.length-1]);
    	LineSegment[] segmentsArr = segments();
    	
    	for(LineSegment segment : segmentsArr) {
    		if (segment.toString().equals(line.toString())){
    			// segment exists
    			return;
    		}
    	}
    	segments.add(line);
    }
    
    public int numberOfSegments(){
        return segments.size();
    }
    
    public LineSegment[] segments(){
        return segments.toArray(new LineSegment[segments.size()]);
    }
    
    public static void main(String[] args){
        Point[] points = new Point[8];
        points[0] = new Point(10000, 0);
        points[1] = new Point(0, 10000);
        points[2] = new Point(3000, 7000);
        points[3] = new Point(7000, 3000);
        points[4] = new Point(20000, 21000);
        points[5] = new Point(3000, 4000);
        points[6] = new Point(14000, 15000);
        points[7] = new Point(6000, 7000);
        FastCollinearPoints FCP = new FastCollinearPoints(points);
        
        LineSegment[] segments = FCP.segments();
        for(LineSegment segment : segments) {
        	System.out.println(segment);
        }
        
    }
}