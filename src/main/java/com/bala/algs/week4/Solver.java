package com.bala.algs.week4;

import java.io.BufferedReader;
import java.io.FileReader;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

	private class Mover implements Comparable<Mover> {

		private final Board board;

		private final Mover previous;

		private final int moves;

		private final int priority;

		public Mover(Board board) {
			this.board = board;
			this.previous = null;
			this.moves = 0;
			this.priority = this.moves + this.board.manhattan();
		}

		public Mover(Board board, Mover previous, int moves) {
			this.board = board;
			this.previous = previous;
			this.moves = moves;
			this.priority = this.moves + this.board.manhattan();
		}

		@Override
		public int compareTo(Mover that) {
			return this.priority - that.priority;
		}

	}

	private boolean isSolvable;

	private Stack<Board> result = new Stack<>();;

	private int moves;

	public Solver(Board initial) {
		// find a solution to the initial board (using the A* algorithm)
		if (initial == null) {
			throw new IllegalArgumentException("Arguement is null");
		}
		MinPQ<Mover> moves = new MinPQ<>();
		moves.insert(new Mover(initial));
		MinPQ<Mover> twinMoves = new MinPQ<>();
		twinMoves.insert(new Mover(initial.twin()));
		int noOfMoves = 0;
		while (noOfMoves < 50) {
			Mover board = moves.delMin();
			Mover twinBoard = twinMoves.delMin();
			noOfMoves = board.moves > twinBoard.moves ? board.moves : twinBoard.moves;
			if (twinBoard.board.isGoal()) {
				isSolvable = false;
				this.moves = -1;
				break;
			}
			if (!board.board.isGoal()) {
				Iterable<Board> neighbors = board.board.neighbors();
				for (Board neightbor : neighbors) {
					if (board.previous != null && neightbor.equals(board.previous.board)) {
						continue;
					}
					moves.insert(new Mover(neightbor, board, board.moves + 1));
				}
			} else {
				System.out.println(noOfMoves);
				isSolvable = true;
				board.board.isGoal();
				this.moves = board.moves;
				Mover temp = board;
				while (temp != null) {
					result.push(temp.board);
					temp = temp.previous;
				}
				break;
			}

			if (!twinBoard.board.isGoal()) {
				Iterable<Board> neighbors = twinBoard.board.neighbors();
				for (Board neightbor : neighbors) {
					if (twinBoard.previous != null && neightbor.equals(twinBoard.previous.board)) {
						continue;
					}
					twinMoves.insert(new Mover(neightbor, board, board.moves + 1));
				}
			}
		}
	}

	public boolean isSolvable() {
		// is the initial board solvable?
		return isSolvable;
	}

	public int moves() {
		// min number of moves to solve initial board; -1 if unsolvable
		return this.moves;
	}

	public Iterable<Board> solution() {
		// sequence of boards in a shortest solution; null if unsolvable
		return result.size() == 0 ? null : result;
	}

	public static void main(String[] args) throws Exception {
		// create initial board from file

		try (BufferedReader bufferedReader = new BufferedReader(
				new FileReader("/Users/bj186016/EclipseWorkspace/Algorithms/src/main/resources/puzzle8/puzzle50.txt"))) {
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
			// solve the puzzle
			Solver solver = new Solver(initial);

			// print solution to standard output
			if (!solver.isSolvable()) {
				StdOut.println("No solution possible");
				StdOut.println(solver.solution());
			} else {
				StdOut.println("Minimum number of moves = " + solver.moves());
				for (Board board : solver.solution())
					StdOut.println(board);
			}
		}
	}

}