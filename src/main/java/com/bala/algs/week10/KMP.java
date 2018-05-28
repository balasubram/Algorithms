package com.bala.algs.week10;

public class KMP {

	private static final int NO_OF_CHARACTERS = 256;

	private final int[][] dfa;

	private String pattern;

	public KMP(String pattern) {
		this.pattern = pattern;
		this.dfa = new int[NO_OF_CHARACTERS][pattern.length()];
		for (int i = 0, x = 0; i < pattern.length(); i++) {
			for (int j = 0; j < NO_OF_CHARACTERS; j++) {
				dfa[j][i] = x;
			}
			dfa[pattern.charAt(i)][i] = i + 1;
			x = dfa[pattern.charAt(i)][x];
		}

	}

	public int search(String text) {

		int n = pattern.length();
		int m = text.length();
		int i, j;
		for (i = 0, j = 0; i < m && j < n; i++) {
			j = dfa[pattern.charAt(i)][j];
		}

		if (j == m) {
			return i - m;
		} else {
			return -1;
		}
	}

}