package com.bala.algs.week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

	private static final boolean HORIZONTAL = Boolean.FALSE;

	private static final boolean VERTICAL = Boolean.TRUE;

	private final class Node {
		Point2D value;
		Node left;
		Node right;
		boolean aligment;

		Node(Point2D value, boolean alignment) {
			this.value = value;
			this.aligment = alignment;
		}

	}

	private Node root = null;

	private int size;

	private boolean isVertical(Node node) {
		return node.aligment == VERTICAL;
	}

	private boolean isHorizontal(Node node) {
		return node.aligment == HORIZONTAL;
	}

	public KdTree() {
		// construct an empty set of points
		this.size = 0;
	}

	public boolean isEmpty() {
		// is the set empty?
		return root == null;
	}

	public int size() {
		// number of points in the set
		return size;
	}

	public void insert(Point2D p) {
		// add the point to the set (if it is not already in the set)
		if (p == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		root = insert(root, p, root != null ? root.aligment : !VERTICAL);
	}

	private Node insert(Node node, Point2D p, boolean isVertical) {
		if (node == null) {
			size++;
			return new Node(p, !isVertical);
		}

		// double cmp = isVertical(node) ? node.value.y() - p.y() : node.value.x() -
		// p.x();
		// if (cmp > 0) {
		// node.left = insert(node.left, p, node.aligment);
		// } else if (cmp < 0) {
		// node.right = insert(node.right, p, node.aligment);
		// } else {
		// if (p.compareTo(node.value) == 0)
		// node.value = p;
		// else
		// node.right = insert(node.right, p, node.aligment);
		// }

		double cmpHo = node.value.y() - p.y();
		double cmpVe = node.value.x() - p.x();

		if (isVertical(node)) {
			if (cmpVe > 0) {
				node.left = insert(node.left, p, node.aligment);
			} else if (cmpVe < 0) {
				node.right = insert(node.right, p, node.aligment);
			} else {
				if (node.value.compareTo(p) == 0) {
					node.value = p;
				} else {
					node.right = insert(node.right, p, node.aligment);
				}
			}
		} else if (isHorizontal(node)) {
			if (cmpHo > 0) {
				node.left = insert(node.left, p, node.aligment);
			} else if (cmpHo < 0) {
				node.right = insert(node.right, p, node.aligment);
			} else {
				if (node.value.compareTo(p) == 0) {
					node.value = p;
				} else {
					node.right = insert(node.right, p, node.aligment);
				}
			}
		}
		return node;
	}

	public boolean contains(Point2D p) {
		// does the set contain point p?
		if (p == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		return contains(root, p);
	}

	private boolean contains(Node node, Point2D p) {
		if (node != null && node.value.equals(p)) {
			return true;
		} else if (node != null) {
			double cmpHo = node.value.y() - p.y();
			double cmpVe = node.value.x() - p.x();

			if (isVertical(node)) {
				if (cmpVe > 0) {
					return contains(node.left, p);
				} else if (cmpVe < 0) {
					return contains(node.right, p);
				} else {
					if (node.value.compareTo(p) == 0) {
						return true;
					} else {
						return contains(node.right, p);
					}
				}
			} else if (isHorizontal(node)) {
				if (cmpHo > 0) {
					return contains(node.left, p);
				} else if (cmpHo < 0) {
					return contains(node.right, p);
				} else {
					if (node.value.compareTo(p) == 0) {
						return true;
					} else {
						return contains(node.right, p);
					}
				}
			}
		}
		return false;
	}

	public void draw() {
		// draw all points to standard draw
		draw(root, 0, 0, 1, 1);
	}

	private void draw(Node node, double minX, double minY, double maxX, double maxY) {
		if (node != null) {
			StdDraw.setPenRadius(0.02);
			StdDraw.setPenColor(StdDraw.BLACK);
			node.value.draw();
			StdDraw.show();
			if (isVertical(node)) {
				StdDraw.setPenRadius(0.001);
				StdDraw.setPenColor(StdDraw.RED);
				StdDraw.line(node.value.x(), minY, node.value.x(), maxY);
				StdDraw.show();
				draw(node.left, minX, minY, node.value.x(), maxY);
				draw(node.right, node.value.x(), minY, maxX, maxY);
			} else if (isHorizontal(node)) {
				StdDraw.setPenRadius(0.001);
				StdDraw.setPenColor(StdDraw.BLUE);
				StdDraw.line(minX, node.value.y(), maxX, node.value.y());
				StdDraw.show();
				draw(node.left, minX, minY, maxX, node.value.y());
				draw(node.right, minX, node.value.y(), maxX, maxY);

			}
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		// all points that are inside the rectangle (or on the boundary)
		if (rect == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		Queue<Point2D> points = new Queue<>();
		range(root, rect, new RectHV(0, 0, 1, 1), points);
		return points;
	}

	private void range(Node node, RectHV rect, RectHV inRangeRect, Queue<Point2D> points) {
		if (node == null)
			return;

		if (rect.intersects(inRangeRect)) {
			if (rect.contains(node.value)) {
				points.enqueue(node.value);
			}

			if (isVertical(node)) {
				RectHV left = new RectHV(inRangeRect.xmin(), inRangeRect.ymin(), node.value.x(), inRangeRect.ymax());
				RectHV right = new RectHV(node.value.x(), inRangeRect.ymin(), inRangeRect.xmax(), inRangeRect.ymax());
				range(node.left, rect, left, points);
				range(node.right, rect, right, points);
			} else if (isHorizontal(node)) {
				RectHV left = new RectHV(inRangeRect.xmin(), inRangeRect.ymin(), inRangeRect.xmax(), node.value.y());
				RectHV right = new RectHV(inRangeRect.xmin(), node.value.y(), inRangeRect.xmax(), inRangeRect.ymax());
				range(node.left, rect, left, points);
				range(node.right, rect, right, points);
			}
		}
	}

	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to point p; null if the set is empty
		if (p == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		if (isEmpty())
			return null;
		return nearest(root, root.value, new RectHV(0, 0, 1, 1), p, Double.MAX_VALUE);
	}

	private Point2D nearest(Node node, Point2D nearest, RectHV nodeRect, Point2D point, double nearestDis) {

		if (node == null) {
			return nearest;
		}

		if (nearestDis > nodeRect.distanceSquaredTo(point)) {
			double dist = point.distanceSquaredTo(node.value);
			if (dist < nearestDis) {
				nearest = node.value;
			}

			double cmpHo = node.value.y() - point.y();
			double cmpVe = node.value.x() - point.x();

			if (isVertical(node)) {
				RectHV left = new RectHV(nodeRect.xmin(), nodeRect.ymin(), node.value.x(), nodeRect.ymax());
				RectHV right = new RectHV(node.value.x(), nodeRect.ymin(), nodeRect.xmax(), nodeRect.ymax());
				if (cmpVe > 0) {
					nearest = nearest(node.left, nearest, left, point, nearestDis);
					nearest = nearest(node.right, nearest, right, point, nearestDis);
				} else {
					nearest = nearest(node.right, nearest, right, point, nearestDis);
					nearest = nearest(node.left, nearest, left, point, nearestDis);
				}
			} else if (isHorizontal(node)) {
				RectHV left = new RectHV(nodeRect.xmin(), nodeRect.ymin(), nodeRect.xmax(), node.value.y());
				RectHV right = new RectHV(nodeRect.xmin(), node.value.y(), nodeRect.xmax(), nodeRect.ymax());
				if (cmpHo > 0) {
					nearest = nearest(node.left, nearest, left, point, nearestDis);
					nearest = nearest(node.right, nearest, right, point, nearestDis);
				} else {
					nearest = nearest(node.right, nearest, right, point, nearestDis);
					nearest = nearest(node.left, nearest, left, point, nearestDis);
				}
			}
		}

		// if (node == null) {
		// return nearest;
		// }
		// double distance = p.distanceTo(node.value);
		// if (nearestDis > distance) {
		// nearest = node.value;
		// nearestDis = distance;
		// }

		// if (node.left != null && nearestDis > node.left.value.distanceTo(p)) {
		// nearest = nearest(node.left, nearest, point, nearestDis);
		// nearestDis = nearest.distanceTo(point);
		// }
		//
		// if (node.right != null && nearestDis > node.right.value.distanceTo(p)) {
		// nearest = nearest(node.right, nearest, point, nearestDis);
		// }

		return nearest;
	}

	public static void main(String[] args) {
		// unit testing of the methods (optional)
	}
}