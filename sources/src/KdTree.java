/**
 * Created by orps on 24.07.17.
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

    public KdTree() {

    }

    public boolean isEmpty() {
        return false;
    }

    public int size() {
        return 0;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        return false;
    }

    public void draw() {

    }

//    public Iterable<Point2D> range(RectHV rect) {
//        if (rect == null) {
//            throw new java.lang.IllegalArgumentException();
//        }
//
//    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        if (size() == 0) {
            return null;
        }

        return new Point2D(0,0);
    }

    public static void main(String[] args) {}
}