package com.bala.algs.week8;

import java.awt.Color;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

	private Picture picture;

	private double[][] picsEnergy;

	public SeamCarver(Picture picture) {
		// create a seam carver object based on the given picture
		if (picture == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		this.picture = picture;
		this.picsEnergy = new double[picture.width()][picture.height()];
		for (int col = 0; col < width(); col++) {
			for (int row = 0; row < height(); row++) {
				picsEnergy[col][row] = -1;
				picsEnergy[col][row] = energy(col, row);
			}
		}
	}

	private double computeGradY(int col, int row) {
		Color color1 = picture.get(col, row + 1);
		Color color2 = picture.get(col, row - 1);
		double redDiff = color1.getRed() - color2.getRed();
		double greenDiff = color1.getGreen() - color2.getGreen();
		double blueDiff = color1.getBlue() - color2.getBlue();
		return Math.pow(redDiff, 2) + Math.pow(greenDiff, 2) + Math.pow(blueDiff, 2);
	}

	private double computeGradX(int col, int row) {
		Color color1 = picture.get(col - 1, row);
		Color color2 = picture.get(col + 1, row);
		double redDiff = color1.getRed() - color2.getRed();
		double greenDiff = color1.getGreen() - color2.getGreen();
		double blueDiff = color1.getBlue() - color2.getBlue();
		return Math.pow(redDiff, 2) + Math.pow(greenDiff, 2) + Math.pow(blueDiff, 2);
	}

	private int computeArrayValue(int col, int row) {
		return row * width() + col;
	}

	private int getRowFromArrayValue(int value) {
		return value / width();
	}

	private int getColumnFromArrayValue(int value) {
		return value % width();
	}

	private void relaxForHorizontal(int col, int row, int anotherCol, int anotherRow, int[] edgeTo, double[] weights) {
		int index = computeArrayValue(col, row);
		int nextIndex = computeArrayValue(anotherCol, anotherRow);
		if (weights[nextIndex] > weights[index] + energy(anotherCol, anotherRow)) {
			weights[nextIndex] = weights[index] + energy(anotherCol, anotherRow);
			edgeTo[nextIndex] = index;
		}
	}

	public Picture picture() {
		// current picture
		return picture;
	}

	public int width() {
		// width of current picture
		return picture.width();
	}

	public int height() {
		// height of current picture
		return picture.height();
	}

	public double energy(int x, int y) {
		// energy of pixel at column x and row y
		if (x < 0 || x >= width() || y < 0 || y >= height()) {
			throw new IllegalArgumentException("Arguments is not in range");
		}

		if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
			return 1000;
		}

		if (picsEnergy[x][y] == -1) {
			return Math.sqrt(computeGradX(x, y) + computeGradY(x, y));
		}
		return picsEnergy[x][y];
	}

	public int[] findHorizontalSeam() {
		// sequence of indices for horizontal seam
		int[] edgeTo = new int[width() * height()];
		double[] weights = new double[width() * height()];

		for (int col = 0; col < width(); col++) {
			for (int row = 0; row < height(); row++) {
				int index = computeArrayValue(col, row);
				edgeTo[index] = -1;
				weights[index] = Double.POSITIVE_INFINITY;
				if (col == 0)
					weights[index] = 0;
			}
		}

		for (int col = 0; col < width() - 1; col++) {
			for (int row = 0; row < height(); row++) {
				if (row - 1 >= 0)
					relaxForHorizontal(col, row, col + 1, row - 1, edgeTo, weights);
				relaxForHorizontal(col, row, col + 1, row, edgeTo, weights);
				if (row + 1 < height())
					relaxForHorizontal(col, row, col + 1, row + 1, edgeTo, weights);
			}
		}

		int minIndex = -1;
		double minWeight = Double.POSITIVE_INFINITY;

		for (int row = 0; row < height(); row++) {
			int index = computeArrayValue(width() - 1, row);
			if (minWeight > weights[index]) {
				minWeight = weights[index];
				minIndex = index;
			}
		}

		int[] seam = new int[width()];
		int v = minIndex, index = seam.length;
		for (; index > 0 && edgeTo[v] != -1;) {
			seam[--index] = getRowFromArrayValue(v);
			v = edgeTo[v];
		}
		seam[--index] = getRowFromArrayValue(v);
		return seam;
	}

	public int[] findVerticalSeam() {
		// sequence of indices for vertical seam
		int[] edgeTo = new int[width() * height()];
		double[] weights = new double[width() * height()];

		for (int row = 0; row < height(); row++) {
			for (int col = 0; col < width(); col++) {
				int index = computeArrayValue(col, row);
				weights[index] = Double.POSITIVE_INFINITY;
				edgeTo[index] = -1;
				if (row == 0)
					weights[index] = 0;
			}
		}

		for (int row = 0; row < height() - 1; row++) {
			for (int col = 0; col < width(); col++) {
				if (col - 1 >= 0)
					relaxForHorizontal(col, row, col - 1, row + 1, edgeTo, weights);
				relaxForHorizontal(col, row, col, row + 1, edgeTo, weights);
				if (col + 1 < width())
					relaxForHorizontal(col, row, col + 1, row + 1, edgeTo, weights);
			}
		}

		int minIndex = -1;
		double minWeight = Double.POSITIVE_INFINITY;

		for (int col = 0; col < width(); col++) {
			int index = computeArrayValue(col, height() - 1);
			if (minWeight > weights[index]) {
				minWeight = weights[index];
				minIndex = index;
			}
		}

		int[] seam = new int[height()];
		int v = minIndex, index = seam.length;
		for (; index > 0 && edgeTo[v] != -1;) {
			seam[--index] = getColumnFromArrayValue(v);
			v = edgeTo[v];
		}
		seam[--index] = getColumnFromArrayValue(v);
		return seam;
	}

	public void removeHorizontalSeam(int[] seam) {
		// remove horizontal seam from current picture
		if (seam == null) {
			throw new IllegalArgumentException("Argument is null");
		}

		if (height() <= 1) {
			throw new IllegalArgumentException("Width is less than or equal to 1");
		}

		if (seam.length != width()) {
			throw new IllegalArgumentException("Passed array length does not match the width of the picture");
		}

		for (int index : seam) {
			if (index < 0 || index >= height()) {
				throw new IllegalArgumentException("Arguments is not in range");
			}
		}

		Picture newPicture = new Picture(width(), height() - 1);
		double[][] newPicsEnery = new double[width()][height() - 1];
		for (int col = 0; col < width(); col++) {
			for (int row = 0; row < height(); row++) {
				if (row < seam[col]) {
					newPicture.set(col, row, this.picture.get(col, row));
					newPicsEnery[col][row] = this.picsEnergy[col][row];
				} else if (row > seam[col]) {
					newPicture.set(col, row - 1, this.picture.get(col, row));
					newPicsEnery[col][row - 1] = this.picsEnergy[col][row];
				}
			}
		}
		this.picsEnergy = newPicsEnery;
		this.picture = newPicture;

	}

	public void removeVerticalSeam(int[] seam) {
		// remove vertical seam from current picture
		if (seam == null) {
			throw new IllegalArgumentException("Argument is null");
		}

		if (width() <= 1) {
			throw new IllegalArgumentException("Height is less than or equal to 1");
		}

		if (seam.length != height()) {
			throw new IllegalArgumentException("Passed array length does not match the height of the picture");
		}

		for (int index : seam) {
			if (index < 0 || index >= width()) {
				throw new IllegalArgumentException("Arguments is not in range");
			}
		}

		Picture newPicture = new Picture(width() - 1, height());
		double[][] newPicsEnery = new double[width() - 1][height()];
		for (int col = 0; col < width(); col++) {
			for (int row = 0; row < height(); row++) {
				if (col < seam[row]) {
					newPicture.set(col, row, picture.get(col, row));
					newPicsEnery[col][row] = picsEnergy[col][row];
				} else if (col > seam[row]) {
					newPicture.set(col - 1, row, picture.get(col, row));
					newPicsEnery[col - 1][row] = picsEnergy[col][row];
				}
			}
		}
		this.picsEnergy = newPicsEnery;
		this.picture = newPicture;
	}

}