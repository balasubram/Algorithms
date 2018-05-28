package com.bala.algs.week3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	
	class LocalSegment {
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
	
	public BruteCollinearPoints(Point[] points) {
		// finds all line segments containing 4 points
		if (points == null || hasNullValues(points) || hasDuplicates(points)) {
			throw new IllegalArgumentException("Argument is null");
		}
		for (int i = 0; i < points.length; i++) {
			for (int j = i + 1; j < points.length; j++) {
				double slope1 = points[i].slopeTo(points[j]);
				for (int k = j + 1; k < points.length; k++) {
					double slope2 = points[i].slopeTo(points[k]);
					if (Double.compare(slope1, slope2) == 0) {
						for (int l = k + 1; l < points.length; l++) {
							double slope3 = points[i].slopeTo(points[l]);
							if (Double.compare(slope1, slope3) == 0) {
								Point[] p = new Point[] { points[i], points[j], points[k], points[l] };
								Arrays.sort(p);
								boolean canBeAdded = true;
								for (LocalSegment localSegment : segments) {
									if (Double.compare(slope1, localSegment.getSlope()) == 0 && (Double.compare(slope1, points[i].slopeTo(localSegment.a)) == 0
											|| Double.compare(slope1, points[i].slopeTo(localSegment.b)) == 0)) {
										p[1] = localSegment.a;
										p[2] = localSegment.b;
										Arrays.sort(p);
										localSegment.a = p[0];
										localSegment.b = p[3];
										canBeAdded = false;
										break;
									}
								}
								if (canBeAdded) {
									segments.add(new LocalSegment(p[0], p[3]));
								}
							}
						}
					}
				}
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
	
	public int numberOfSegments() {
		return segments.size();
	}

	public LineSegment[] segments() {
		// the number of line segments
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
			BruteCollinearPoints collinear = new BruteCollinearPoints(points);
			for (LineSegment segment : collinear.segments()) {
				StdOut.println(segment);
				segment.draw();
				StdDraw.show();
			}
			StdDraw.show();
		}
	}

}