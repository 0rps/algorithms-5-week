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
        private boolean isHorizontal;
        private int maxX;

        private Node parent = null;
        private Node left;
        private Node right;

        private Node(Point2D point, boolean isHorizontal) {

            this.point = point;
            this.isHorizontal = isHorizontal;
        }

        @Override
        public int compareTo(Point2D other) {
            double a;
            double b;
            if (isHorizontal) {
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
                left = new Node(point, !isHorizontal);
                left.parent = this;
                return left;
            } else {
                right = new Node(point, !isHorizontal);
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
        return 0;
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

        root.addChild(p);
        Node parent = root;
        Node currentNode = root;
        while(currentNode != null) {
            parent = currentNode;
            currentNode = currentNode.nodeToInsert(p);
        }

        /// update parent maxX

        parent.addChild(p);

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

        ArrayList<Point2D> points = null;
        return points;
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