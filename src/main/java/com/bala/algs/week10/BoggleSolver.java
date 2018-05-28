package com.bala.algs.week10;

import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.In;

public class BoggleSolver {

	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private class Node {

		boolean isWord = Boolean.FALSE;
		Node[] next = new Node[ALPHABET.length()];

	}

	private Node root;

	private void constructTries(String word) {
		root = constructTries(root, word, Boolean.TRUE, 0);
	}

	private Node constructTries(Node node, String word, boolean isWord, int index) {
		if (node == null) {
			node = new Node();
		}
		if (index == word.length()) {
			node.isWord = Boolean.TRUE;
		} else {
			int charIndex = ALPHABET.indexOf(word.charAt(index));
			node.next[charIndex] = constructTries(node.next[charIndex], word, isWord, index + 1);
		}
		return node;
	}

	private boolean isWord(String word) {
		if (word.length() < 3) {
			return Boolean.FALSE;
		} else {
			return isWord(root, word, 0);
		}
	}

	private boolean isWord(Node node, String word, int index) {
		if (node == null)
			return false;
		if (index == word.length())
			return node.isWord;
		int charIndex = ALPHABET.indexOf(word.charAt(index));
		return isWord(node.next[charIndex], word, index + 1);
	}

	private boolean longestPrefixOf(String query) {
		int length = longestPrefixOf(root, query, 0, -1);
		if (length == -1 || length != query.length()) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	private int longestPrefixOf(Node node, String word, int index, int length) {
		if (node == null)
			return length;
		if (index == word.length())
			return word.length();
		int charIndex = ALPHABET.indexOf(word.charAt(index));
		return longestPrefixOf(node.next[charIndex], word, index + 1, length);
	}

	// Initializes the data structure using the given array of strings as the dictionary.
	// (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
	public BoggleSolver(String[] dictionary) {
		if (dictionary == null) {
			throw new IllegalArgumentException("Argument is null");
		}

		for (String word : dictionary) {
			if (word == null) {
				throw new IllegalArgumentException("A dictionary word is null");
			}
			constructTries(word);
		}

	}

	// Returns the set of all valid words in the given Boggle board, as an Iterable.
	public Iterable<String> getAllValidWords(BoggleBoard board) {
		Set<String> words = new HashSet<>();
		for (int row = 0; row < board.rows(); row++) {
			for (int col = 0; col < board.cols(); col++) {
				boolean[][] visited = new boolean[board.rows()][board.cols()];
				char c = board.getLetter(row, col);
				StringBuilder sb = new StringBuilder();
				if (c == 'Q') {
					sb.append("QU");
				} else {
					sb.append(c);
				}
				retrieveValidWords(board, sb.toString(), row, col, words, visited);
			}
		}
		return words;
	}

	private void retrieveValidWords(BoggleBoard board, String word, int row, int col, Set<String> words, boolean[][] visited) {

		if (visited[row][col])
			return;

		if (isWord(word)) {
			words.add(word);
		}

		visited[row][col] = Boolean.TRUE;

		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = col - 1; j <= col + 1; j++) {
				if (isValid(i, j, board) && !visited[i][j]) {
					StringBuilder sb = new StringBuilder(word);
					char c = board.getLetter(i, j);
					if (c == 'Q') {
						sb.append("QU");
					} else {
						sb.append(c);
					}
					if (longestPrefixOf(sb.toString())) {
						retrieveValidWords(board, sb.toString(), i, j, words, visited);
					}
				}
			}
		}

		visited[row][col] = Boolean.FALSE;

	}

	private boolean isValid(int rowIndex, int colIndex, BoggleBoard board) {
		return rowIndex >= 0 && rowIndex < board.rows() && colIndex >= 0 && colIndex < board.cols();
	}

	// Returns the score of the given word if it is in the dictionary, zero otherwise.
	// (You can assume the word contains only the uppercase letters A through Z.)
	public int scoreOf(String word) {
		if (word == null) {
			throw new IllegalArgumentException("Argument is null");
		}

		if (isWord(word)) {
			switch (word.length()) {
			case 0:
			case 1:
			case 2:
				return 0;
			case 3:
			case 4:
				return 1;
			case 5:
				return 2;
			case 6:
				return 3;
			case 7:
				return 5;
			case 8:
				return 11;
			default:
				return 11;
			}
		}
		return 0;
	}

	public static void main(String[] args) {
//		char[][] chars = new char[][] { { 'A', 'T', 'E', 'E' }, { 'A', 'P', 'Y', 'O' }, { 'T', 'I', 'N', 'U' },
//				{ 'E', 'D', 'S', 'E' } };
		BoggleBoard board = new BoggleBoard("/Users/bj186016/EclipseWorkspace/Algorithms/src/main/resources/boggle/board-q.txt");
		In in = new In("/Users/bj186016/EclipseWorkspace/Algorithms/src/main/resources/boggle/dictionary-algs4.txt");
		BoggleSolver solver = new BoggleSolver(in.readAllStrings());
		Iterable<String> words = solver.getAllValidWords(board);
		for (String word : words) {
			System.out.println(word);
		}
	}

}