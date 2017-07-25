/**
 * Created by orps on 24.07.17.
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {

    private class Node implements Comparable<Point2D> {

        private Point2D point;
        private boolean isVertical;
        private int maxX;

        private Node parent = null;
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
                left.parent = this;
                return left;
            } else {
                right = new Node(point, !isVertical);
                right.parent = this;
                return right;
            }
        }
    }

    private int count = 0;
    private Node root = null;

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

        /// update parent maxX

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

        return new Point2D(0,0);
    }

    public static void main(String[] args) {}
}