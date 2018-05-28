package com.bala.algs.week4;

import java.io.BufferedReader;
import java.io.FileReader;

import edu.princeton.cs.algs4.Queue;

public class Board {

	private final int[][] blocks;

	private final int dimension;

	public Board(int[][] blocks) {
		// construct a board from an n-by-n array of blocks
		// (where blocks[i][j] = block in row i, column j)
		if (blocks == null) {
			throw new IllegalArgumentException("Arguement is null");
		}
		this.blocks = new int[blocks.length][];
		for (int i = 0; i < blocks.length; i++) {
			this.blocks[i] = new int[blocks[i].length];
			for (int j = 0; j < blocks[i].length; j++) {
				this.blocks[i][j] = blocks[i][j];
			}
		}
		this.dimension = blocks.length;
	}

//	private int calculateDistances(int num, int i, int j) {
//		boolean breakLoop = false;
//		int distance = 0;
//		for (int i1 = 0; i1 < dimension; i1++) {
//			for (int j1 = 0; j1 < dimension; j1++) {
//				if ((i1 * dimension + j1 + 1) == num) {
//					distance = Math.abs(i1 - i) + Math.abs(j1 - j);
//					breakLoop = true;
//					break;
//				}
//			}
//			if (breakLoop)
//				break;
//		}
//		return distance;
//	}

	private Board swap(int i, int j, int i1, int j1) {
		int temp = this.blocks[i][j];
		this.blocks[i][j] = this.blocks[i1][j1];
		this.blocks[i1][j1] = temp;
		return this;
	}

	public int dimension() {
		// board dimension n
		return dimension;
	}

	public int hamming() {
		// number of blocks out of place
		int sum = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (this.blocks[i][j] != (i * dimension + j + 1) && !(i == dimension - 1 && j == dimension - 1)) {
					sum += 1;
				}
			}
		}
		return sum;
	}

	public int manhattan() {
		// sum of Manhattan distances between blocks and goal
		int sum = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (this.blocks[i][j] != 0 && this.blocks[i][j] != (i * dimension + j + 1)) {
					int value = this.blocks[i][j];
					int initialX = (value - 1) / dimension;
					int initialY = value - 1 - initialX * dimension;
					sum += Math.abs(initialX - i) + Math.abs(initialY - j);
				}
			}
		}
		return sum;
	}

	public boolean isGoal() {
		// is this board the goal board?
		boolean result = false;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension ; j++) {
				if (!(i == dimension - 1 && j == dimension - 1) && this.blocks[i][j] == (i * dimension + j + 1)) {
					result = true;
				} else if (!(i == dimension - 1 && j == dimension - 1) && this.blocks[i][j] != (i * dimension + j + 1)) {
					result = false;
					break;
				}
			}
			if (!result)
				break;
		}
		return result && (this.blocks[dimension - 1][dimension - 1] == 0);
	}

	public Board twin() {
		// a board that is obtained by exchanging any pair of blocks
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension - 1; j++) {
				if (blocks[i][j] != 0 && blocks[i][j + 1] != 0) {
					return new Board(blocks).swap(i, j, i, j + 1);
				}
			}
		}
		return new Board(this.blocks);
	}

	public boolean equals(Object y) {
		// does this board equal y?
		boolean result = true;
		if (y != null && y instanceof Board) {
			Board that = (Board) y;
			if (this.dimension == that.dimension) {
				for (int i = 0; i < dimension; i++) {
					for (int j = 0; j < dimension; j++) {
						if (this.blocks[i][j] == that.blocks[i][j]) {
							continue;
						} else {
							result = false;
							break;
						}
					}
					if (!result)
						break;
				}
			} else {
				result = false;
			}
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public int hashCode() {
		int hashCode = 31 * this.dimension;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				hashCode = 31 * hashCode + blocks[i][j];
			}
		}
		return hashCode;
	}

	public Iterable<Board> neighbors() {
		// all neighboring boards
		Queue<Board> boards = new Queue<>();
		int row = 0, col = 0;
		boolean foundZeroBlock = false;
		for (; row < dimension; row++) {
			col = 0;
			for (; col < dimension; col++) {
				if (blocks[row][col] == 0) {
					foundZeroBlock = true;
					break;
				}
			}
			if (foundZeroBlock)
				break;
		}

		if (row + 1 < this.dimension()) {
			Board board = new Board(this.blocks).swap(row, col, row + 1, col);
			boards.enqueue(board);
		}
		if (row - 1 >= 0) {
			Board board = new Board(this.blocks).swap(row, col, row - 1, col);
			boards.enqueue(board);
		}
		if (col + 1 < this.dimension()) {
			Board board = new Board(this.blocks).swap(row, col, row, col + 1);
			boards.enqueue(board);
		}
		if (col - 1 >= 0) {
			Board board = new Board(this.blocks).swap(row, col, row, col - 1);
			boards.enqueue(board);
		}
		return boards;
	}

	public String toString() {
		// string representation of this board (in the output format specified below)
		StringBuilder sb = new StringBuilder();
		sb.append(dimension).append("\n");
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				sb.append(this.blocks[i][j]).append(" ");
			}
			sb.deleteCharAt(sb.length() - 1).append("\n");
		}
		return sb.substring(0, sb.length() - 1);
	}

	public static void main(String[] args) throws Exception {
		// create initial board from file

		try (BufferedReader bufferedReader = new BufferedReader(
				new FileReader("/Users/bj186016/EclipseWorkspace/Algorithms/src/main/resources/puzzle8/puzzle04.txt"))) {
			String line = bufferedReader.readLine();
			int n = Integer.valueOf(line != null ? line : "0");
			int[][] blocks = new int[n][n];
			for (int i = 0; i < n; i++) {
				line = bufferedReader.readLine();
				if (line != null && line.trim().length() != 0) {
					String[] split = line.trim().split("\\s+");
					for (int j = 0; j < split.length; j++) {
						blocks[i][j] = Integer.valueOf(split[j]);
					}
				}
			}
			Board initial = new Board(blocks);
			Board twin = initial.twin();
			System.out.println(initial.equals(twin));
			// solve the puzzle
			System.out.println(initial.manhattan());
			System.out.println(initial.hamming());
		}
	}

}