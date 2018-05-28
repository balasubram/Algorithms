package com.bala.algs.week10;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

public class BoggleBoard {

	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private final int m;

	private final int n;

	private final char[][] board;

	// Initializes a random 4-by-4 Boggle board.
	// (by rolling the Hasbro dice)
	public BoggleBoard() {
		this.m = 4;
		this.n = 4;
		board = new char[this.m][this.n];
		for (int i = 0; i < this.m; i++) {
			for (int j = 0; j < this.n; j++) {
				this.board[i][j] = ALPHABET.charAt(StdRandom.uniform(26));
			}
		}
	}

	// Initializes a random m-by-n Boggle board.
	// (using the frequency of letters in the English language)
	public BoggleBoard(int m, int n) {
		this.m = m;
		this.n = n;
		board = new char[this.m][this.n];
		for (int i = 0; i < this.m; i++) {
			for (int j = 0; j < this.n; j++) {
				this.board[i][j] = ALPHABET.charAt(StdRandom.uniform(26));
			}
		}
	}

	// Initializes a Boggle board from the specified filename.
	public BoggleBoard(String filename) {
		In in = new In(filename);
		this.m = in.readInt();
		this.n = in.readInt();
		board = new char[this.m][this.n];
		for (int i = 0; i < this.m; i++) {
			for (int j = 0; j < this.n; j++) {
				this.board[i][j] = in.readString().toUpperCase().charAt(0);
			}
		}
	}

	// Initializes a Boggle board from the 2d char array.
	// (with 'Q' representing the two-letter sequence "Qu")
	public BoggleBoard(char[][] a) {
		this.m = a.length;
		this.n = a[0].length;
		board = new char[this.m][this.n];
		for (int i = 0; i < this.m; i++) {
			for (int j = 0; j < this.n; j++) {
				this.board[i][j] = a[i][j];
			}
		}
	}

	// Returns the number of rows.
	public int rows() {
		return m;
	}

	// Returns the number of columns.
	public int cols() {
		return n;
	}

	// Returns the letter in row i and column j.
	// (with 'Q' representing the two-letter sequence "Qu")
	public char getLetter(int i, int j) {
		return this.board[i][j];
	}

	// Returns a string representation of the board.
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.m).append("x").append(this.n).append("\n");
		for (int i = 0; i < this.m; i++) {
			for (int j = 0; j < this.n; j++) {
				sb.append(this.board[i][j]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
