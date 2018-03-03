import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BruteCollinearPoints {

    private List<LineSegment> segments;

    /**
     * Using brute force to search for all line segments containing exactly 4 points.
     * @param points points array.
     */
    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        checkArguments(points);
        segments = new ArrayList<>();
        Arrays.sort(points);

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Point pointI = points[i];
                        Point pointJ = points[j];
                        Point pointK = points[k];
                        Point pointL = points[l];
                        if (pointI.slopeTo(pointJ) == pointI.slopeTo(pointK) && pointI.slopeTo(pointK) == pointI.slopeTo(pointL)) {
                            segments.add(new LineSegment(pointI, pointL));
                        }
                    }
                }
            }
        }
    }

    /**
     * Get number of line segments.
     * @return number of line segments.
     */
    public int numberOfSegments() {
        // the number of line segments
        return segments.size();
    }

    /**
     * Get line segments array.
     * @return Line segments array.
     */
    public LineSegment[] segments() {
        // the line segments
        LineSegment[] segmentsArray = new LineSegment[segments.size()];
        return segments.toArray(segmentsArray);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}