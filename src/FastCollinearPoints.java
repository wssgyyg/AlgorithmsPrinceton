import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

    private List<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        checkArguments(points);
        segments = new ArrayList<>();
        Arrays.sort(points);

        Point previousMax = null;
        double previousSlope = 0.0;
        for (int i = 0; i < points.length - 3; i++) {
            Arrays.sort(points, i, points.length);
            Arrays.sort(points, i, points.length, points[i].slopeOrder());
            int left = i + 1;
            int j = i + 2;

            while (j < points.length) {
                double targetSlope = points[i].slopeTo(points[left]);
                while (j < points.length && points[i].slopeTo(points[j]) == targetSlope) {
                    j++;
                }
                if (j - left > 2) {
                    if (segments.size() == 0 || (points[j - 1].compareTo(previousMax) != 0 || targetSlope != previousSlope)) {
                        segments.add(new LineSegment(points[i], points[j - 1]));
                        previousMax = points[j - 1];
                        previousSlope = targetSlope;
                    }
                }
                left = j;
                j = left + 1;
            }
        }

    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] lineSegments = new LineSegment[segments.size()];
        return segments.toArray(lineSegments);
    }

    private void checkArguments(Point[] points) {
        checkNullArgument(points);
        checkNullPoint(points);
        checkRepeatedPoint(points);
    }

    private void checkNullArgument(Point[] points) {
        if (null == points) {
            throw new IllegalArgumentException();
        }
    }

    private void checkNullPoint(Point[] points) {
        for (Point point : points) {
            if (null == point) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void checkRepeatedPoint(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }


}
