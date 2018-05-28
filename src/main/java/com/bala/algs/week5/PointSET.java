package com.bala.algs.week5;

import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {

	private Set<Point2D> points = new TreeSet<>();

	public PointSET() {
		// construct an empty set of points
	}

	public boolean isEmpty() {
		// is the set empty?
		return points.isEmpty();
	}

	public int size() {
		// number of points in the set
		return points.size();
	}

	public void insert(Point2D p) {
		// add the point to the set (if it is not already in the set)
		if (p == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		if (!points.contains(p)) {
			points.add(p);
		}

	}

	public boolean contains(Point2D p) {
		// does the set contain point p?
		if (p == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		return points.contains(p);
	}

	public void draw() {
		// draw all points to standard draw
		StdDraw.setPenRadius(0.02);
		StdDraw.setPenColor(StdDraw.BLACK);
		for (Point2D point : points) {
			point.draw();
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		// all points that are inside the rectangle (or on the boundary)
		if (rect == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		Queue<Point2D> queues = new Queue<>();
		for (Point2D point : points) {
			if (rect.contains(point)) {
				queues.enqueue(point);
			}
		}
		return queues;
	}

	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to point p; null if the set is empty
		if (p == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		double minValue = Double.MAX_VALUE;
		Point2D minPoint = null;
		for (Point2D point : points) {
			double distance = p.distanceTo(point);
			if (distance < minValue) {
				minValue = distance;
				minPoint = point;
			}
		}
		return minPoint;
	}

	public static void main(String[] args) {
		// unit testing of the methods (optional)
	}
}