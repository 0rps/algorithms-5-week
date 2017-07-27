/**
 * Created by orps on 24.07.17.
 */

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.In;
// import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class KdTree {

    private class Node implements Comparable<Point2D> {

        private final Point2D point;
        private final boolean isVertical;

//        private Node parent = null;
        private Node left;
        private Node right;

        private Node(Point2D point, boolean isVertical) {

            this.point = point;
            this.isVertical = isVertical;
        }

        @Override
        public int compareTo(Point2D other) {
            double a;
            double b;
            if (isVertical) {
                a = point.x();
                b = other.x();
            } else {
                a = point.y();
                b = other.y();
            }

            if (a < b) {
                return -1;
            } else if (a > b) {
                return 1;
            }

            return 0;
        }

        private Node nodeToInsert(Point2D point) {
            if (this.compareTo(point) > 0) {
                return left;
            } else {
                return right;
            }
        }

        private Node addChild(Point2D point) {
            if (this.compareTo(point) > 0) {
                left = new Node(point, !isVertical);
                // left.parent = this;
                return left;
            } else {
                right = new Node(point, !isVertical);
                // right.parent = this;
                return right;
            }
        }
    }

    private int count = 0;
    private Node root = null;
    private Point2D nPoint = null;

    public KdTree() { }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return count;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        if (root == null) {
            root = new Node(p, true);
            return;
        }

        if (contains(p)) {
            return;
        }

        Node parent = root;
        Node currentNode = root;
        while(currentNode != null) {
            parent = currentNode;
            currentNode = currentNode.nodeToInsert(p);
        }

        parent.addChild(p);
        count++;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        if (root == null) {
            return false;
        }

        Node nextNode = root;
        while(nextNode != null) {
            final int compare = nextNode.compareTo(p);
            if (compare < 0) {
                nextNode = nextNode.left;
            } else if (compare > 0) {
                nextNode = nextNode.right;
            } else {
                return true;
            }
        }

        return false;
    }

//    public void check() {
//        check(root);
//    }
//    private void check(Node node) {
//        if (node == null) {
//            return;
//        }
//
//        if (node.left != null) {
//            if (node.isVertical) {
//                if (node.point.x() < node.left.point.x()) {
//                    StdOut.println("Wrong: left is higher than node: " + node.point.x() + ", " + node.point.x());
//                }
//            } else {
//                if (node.point.y() < node.left.point.y()) {
//                    StdOut.println("Wrong: left is higher than node: " + node.point.x() + ", " + node.point.x());
//                }
//            }
//            check(node.left);
//        }
//
//        if (node.right != null) {
//            if (node.isVertical) {
//                if (node.point.x() > node.right.point.x()) {
//                    StdOut.println("Wrong: right is lower than node: " + node.point.x() + ", " + node.point.x());
//                }
//            } else {
//                if (node.point.y() > node.right.point.y()) {
//                    StdOut.println("Wrong: right is lower than node: " + node.point.x() + ", " + node.point.x());
//                }
//            }
//            check(node.right);
//        }
//    }

    public void draw() {
        if (root != null) {
            draw(root);
        }
    }

    private void draw(Node node) {
        StdDraw.point(node.point.x(), node.point.y());
        if (node.left != null) { draw(node.left); }
        if (node.right != null ) { draw(node.right); }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.IllegalArgumentException();
        }

        ArrayList<Point2D> points = new ArrayList<>();
        range(rect, root, points);
        return points;
    }

    private void range(RectHV rect, Node node, ArrayList<Point2D> result) {
        if (node == null) {
            return;
        }

        if (rect.contains(node.point)) {
            result.add(node.point);
        }

        double value;
        double a,b;

        if (node.isVertical) {
            a = rect.xmin();
            b = rect.xmax();
            value = node.point.x();
        } else {
            a = rect.ymin();
            b = rect.ymax();
            value = node.point.y();
        }

        if (value <= b) {
            range(rect, node.left, result);
        }

        if (value >= a) {
            range(rect, node.right, result);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        if (size() == 0) {
            return null;
        }

        nPoint = root.point;
        double distance = p.distanceSquaredTo(nPoint) + 1;
        nearest(p, root, distance);

        return nPoint;
    }

    private void nearest(Point2D p, Node candidate, double distance) {
        if (candidate == null) {
            return;
        }

        double current = p.distanceSquaredTo(candidate.point);
        if (current < distance) {
            distance = current;
            nPoint = candidate.point;
        }

        double pMetric;
        double candidateMetric;
        if (candidate.isVertical) {
            pMetric = p.x();
            candidateMetric = candidate.point.x();
        } else {
            pMetric = p.y();
            candidateMetric = candidate.point.y();
        }

        if (pMetric < candidateMetric) {
            nearest(p, candidate.left,  distance);
            double delta = pMetric - candidateMetric;
            distance = p.distanceSquaredTo(nPoint);
            if (delta*delta < distance) {
                nearest(p, candidate.right,  distance);
            }
        } else {
            nearest(p, candidate.right, distance);
            distance = p.distanceSquaredTo(nPoint);
            double delta = pMetric - candidateMetric;
            if (delta*delta < distance) {
                nearest(p, candidate.left, distance);
            }
        }
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // initialize the two data structures with point from standard input
        KdTree brute = new KdTree();
        //KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            //kdtree.insert(p);
            brute.insert(p);
        }

        //brute.check();

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
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.RED);
            brute.nearest(query).draw();
            StdDraw.setPenRadius(0.02);

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenColor(StdDraw.BLUE);
            //kdtree.nearest(query).draw();
            StdDraw.show();

            StdDraw.pause(50);
        }
    }
}