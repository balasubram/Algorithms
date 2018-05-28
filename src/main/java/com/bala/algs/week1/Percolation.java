package com.bala.algs.week1;

import java.io.BufferedReader;
import java.io.FileReader;

public class Percolation {

	private final WeightedQuickUnion quickUnionUF;

	private final WeightedQuickUnion quickUnionFullUF;

	private final int gridSize;

	private final int top;

	private final int bottom;

	private int noOfOpenSites;

	private boolean[][] gridStatus;

	public Percolation(int gridSize) {
		
		if( gridSize <= 0) {
			throw new IllegalArgumentException("Illegal Value");
		}
		
		// create n-by-n grid, with all sites blocked
		this.gridSize = gridSize;
		quickUnionUF = new WeightedQuickUnion((gridSize * gridSize) + 2);
		quickUnionFullUF = new WeightedQuickUnion((gridSize * gridSize) + 1);
		this.top = 0;
		this.bottom = (gridSize * gridSize) + 1;
		this.noOfOpenSites = 0;
		this.gridStatus = new boolean[gridSize][gridSize];
		for (int i = 0; i < gridStatus.length; i++) {
			for (int j = 0; j < gridStatus.length; j++) {
				gridStatus[i][j] = false;
			}
		}
	}

	private void validate(int n) {
		if (n < 0 || n >= this.gridSize) {
			throw new IllegalArgumentException("Index is out of range");
		}
	}

	private int calculateIndex(int row, int col) {
		return ((row * this.gridSize) + col) + 1;
	}

	public void open(int row, int col) {
		// open site (row, col) if it is not open already
		validate(row-1);
		validate(col-1);
		if (isOpen(row, col)) {
			return;
		}
		int index = calculateIndex(row-1, col-1);

		if (row-1 == 0) {
			quickUnionUF.union(top, index);
			quickUnionFullUF.union(top, index);
		} 
		
		if (row-1 == (this.gridSize - 1)) {
			quickUnionUF.union(index, bottom);
		}

		if (row-1 != 0 && isOpen(row - 1, col)) {
			int indexN = calculateIndex(row-1 - 1, col-1);
			quickUnionUF.union(index, indexN);
			quickUnionFullUF.union(index, indexN);
		}

		if (row-1 != (gridSize - 1) && isOpen(row + 1, col)) {
			int indexN = calculateIndex(row-1 + 1, col-1);
			quickUnionUF.union(index, indexN);
			quickUnionFullUF.union(index, indexN);
		}

		if (col-1 != 0 && isOpen(row, col - 1)) {
			int indexN = calculateIndex(row-1, col-1 - 1);
			quickUnionUF.union(index, indexN);
			quickUnionFullUF.union(index, indexN);
		}

		if (col-1 != (gridSize - 1) && isOpen(row, col + 1)) {
			int indexN = calculateIndex(row-1, col-1 + 1);
			quickUnionUF.union(index, indexN);
			quickUnionFullUF.union(index, indexN);
		}
		gridStatus[row-1][col-1] = true;
		noOfOpenSites++;
	}

	public boolean isOpen(int row, int col) {
		// is site (row, col) open?
		validate(row-1);
		validate(col-1);
		return gridStatus[row-1][col-1];
	}

	public boolean isFull(int row, int col) {
		// is site (row, col) full?
		validate(row-1);
		validate(col-1);
		int index = calculateIndex(row-1, col-1);
		return quickUnionFullUF.connected(top, index);
	}

	public int numberOfOpenSites() {
		// number of open sites
		return this.noOfOpenSites;
	}

	public boolean percolates() {
		// does the system percolate?
		return quickUnionUF.connected(top, bottom);
	}

	public static void main(String[] args) throws Exception {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(
				"/Users/bj186016/EclipseWorkspace/Algorithms/src/main/resources/percolation/input5.txt"))) {
			String line = bufferedReader.readLine();
			Percolation percolation = new Percolation(Integer.valueOf(line != null ? line: "0"));
			while ((line = bufferedReader.readLine()) != null) {
				if (line.trim().length() != 0) {
					String[] split = line.trim().split("\\s+");
					percolation.open(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
				}
			}
			
			percolation.open(5, -1);
			System.out.println(percolation.percolates());
			System.out.println(percolation.numberOfOpenSites());
//			System.out.println(percolation.isFull(4, 1));
		}
	}

}