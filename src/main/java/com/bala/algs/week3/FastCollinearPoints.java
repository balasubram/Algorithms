package com.bala.algs.week3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

	private class LocalSegment {
		Point a;
		Point b;

		LocalSegment(Point a, Point b) {
			this.a = a;
			this.b = b;
		}

		double getSlope() {
			return a.slopeTo(b);
		}

	}

	private List<LocalSegment> segments = new ArrayList<LocalSegment>();
	
	public FastCollinearPoints(Point[] points) {
		// finds all line segments containing 4 or more points
		if (points == null || hasNullValues(points) || hasDuplicates(points)) {
			throw new IllegalArgumentException("Argument is null");
		}

		Point[] temp = Arrays.copyOf(points, points.length);

		for (Point point : points) {
			Arrays.sort(temp, point.slopeOrder());
			List<Point> slopePoints = new ArrayList<>();
			slopePoints.add(point);
			for (int i = 0; i < temp.length - 1; i++) {
				if (Double.compare(point.slopeTo(temp[i]), point.slopeTo(temp[i + 1])) == 0) {
					if (!slopePoints.contains(temp[i])) {
						slopePoints.add(temp[i]);
					}
					if (!slopePoints.contains(temp[i + 1])) {
						slopePoints.add(temp[i + 1]);
					}
				} else {
					addLineSegment(slopePoints);
					slopePoints.clear();
					slopePoints.add(point);
				}
			}
			if (!slopePoints.isEmpty()) {
				addLineSegment(slopePoints);
			}

		}

	}

	private boolean hasDuplicates(Point[] points) {
		boolean result = false;
		Point[] tempPoints = Arrays.copyOf(points, points.length);
		Arrays.sort(tempPoints);
		for (int i = 0; i < tempPoints.length - 1; i++) {
			if (tempPoints[i].compareTo(tempPoints[i + 1]) == 0) {
				result = true;
				break;
			}
		}
		return result;
	}

	private boolean hasNullValues(Point[] points) {
		boolean result = false;
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) {
				result = true;
				break;
			}
		}
		return result;
	}

	private void addLineSegment(List<Point> slopePoints) {
		if (slopePoints.size() < 4) {
			return;
		} else {
			Point[] points = slopePoints.toArray(new Point[0]);
			Arrays.sort(points);
			boolean canBeAdded = true;
			double slope1 = points[0].slopeTo(points[points.length - 1]);
			for (LocalSegment localSegment : segments) {
				if (Double.compare(slope1, localSegment.getSlope()) == 0
						&& (Double.compare(slope1, points[0].slopeTo(localSegment.a)) == 0 || Double.compare(slope1, points[0].slopeTo(localSegment.b)) == 0)) {
					points[1] = localSegment.a;
					points[2] = localSegment.b;
					Arrays.sort(points);
					localSegment.a = points[0];
					localSegment.b = points[points.length - 1];
					canBeAdded = false;
					break;
				}
			}
			if (canBeAdded) {
				segments.add(new LocalSegment(points[0], points[points.length - 1]));
			}
		}
	}

	public int numberOfSegments() {
		// the number of line segments
		return segments.size();
	}

	public LineSegment[] segments() {
		// the line segments
		LineSegment[] lineSegments = new LineSegment[segments.size()];
		for (int i = 0; i < segments.size(); i++) {
			LocalSegment segment = segments.get(i);
			lineSegments[i] = new LineSegment(segment.a, segment.b);

		}
		return lineSegments;
	}

	public static void main(String[] args) throws Exception {

		try (BufferedReader bufferedReader = new BufferedReader(
				new FileReader("/Users/bj186016/EclipseWorkspace/Algorithms/src/main/resources/collinear/input20.txt"))) {
			String line = bufferedReader.readLine();
			Point[] points = new Point[(Integer.valueOf(line != null ? line : "0"))];
			int index = 0;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.trim().length() != 0) {
					String[] split = line.trim().split("\\s+");
					Point point = new Point(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
					points[index++] = point;
				}
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
				StdDraw.show();
			}
			StdDraw.show();
		}
	}

}