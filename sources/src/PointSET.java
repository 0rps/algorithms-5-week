/**
 * Created by orps on 24.07.17.
 */

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


import java.util.ArrayList;

public class PointSET {

    SET<Point2D> storage;


    public PointSET() {
        storage = new SET<>();
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }

    public int size() {
        return storage.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        storage.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        return storage.contains(p);
    }

    public void draw() {

        for (Point2D point: storage) {
            StdDraw.point(point.x(), point.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.IllegalArgumentException();
        }

        ArrayList<Point2D> list = new ArrayList<>();
        for (Point2D point: storage) {
            if (rect.contains(point)) {
                list.add(point);
            }
        }

        return list;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        if (isEmpty()) {
            return null;
        }

        Point2D result = storage.iterator().next();
        double resultDistance = p.distanceSquaredTo(result);

        for (Point2D current: storage) {
            double currentDistance = p.distanceSquaredTo(current);
            if (currentDistance < resultDistance) {
                result = current;
                resultDistance = currentDistance;
            }
        }

        return result;
    }

    public static void main(String[] args) {

//        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
//        StdDraw.enableDoubleBuffering();
//        PointSET set = new PointSET();
//        StdDraw.setPenRadius(0.02);
//        while (true) {
//            if (StdDraw.isMousePressed()) {
//                double x = StdDraw.mouseX();
//                double y = StdDraw.mouseY();
//                StdOut.printf("%8.6f %8.6f\n", x, y);
//                Point2D p = new Point2D(x, y);
//                if (rect.contains(p)) {
//                    StdOut.printf("%8.6f %8.6f\n", x, y);
//                    set.insert(p);
//                    StdDraw.clear();
//                    set.draw();
//                    StdDraw.show();
//                }
//            }
//            StdDraw.pause(50);
//        }

        String filename = args[0];
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        //KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            //kdtree.insert(p);
            brute.insert(p);
        }

        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            brute.nearest(query).draw();
            StdDraw.setPenRadius(0.02);

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenColor(StdDraw.BLUE);
            //kdtree.nearest(query).draw();
            StdDraw.show();
            StdDraw.pause(40);
        }
    }
}