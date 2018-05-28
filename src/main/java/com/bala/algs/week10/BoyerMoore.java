package com.bala.algs.week10;

public class BoyerMoore {

	private static final int NO_OF_CHARACTERS = 256;

	private final int[] skipCharacters;

	private String pattern;

	public BoyerMoore(String pattern) {
		this.pattern = pattern;
		this.skipCharacters = new int[NO_OF_CHARACTERS];
		for (int i = 0; i < NO_OF_CHARACTERS; i++) {
			skipCharacters[i] = -1;
		}
		for (int i = 0; i < pattern.length(); i++) {
			skipCharacters[pattern.charAt(i)] = i;
		}

	}

	public int search(String text) {
		int n = text.length();
		int m = pattern.length();
		int skipChars = 0;
		for (int i = 0; i < n; i += skipChars) {
			skipChars = 0;
			for (int j = m - 1; j >= 0; j--) {
				if (pattern.charAt(j) != text.charAt(i + j)) {
					skipChars = Math.max(1, j - skipCharacters[text.charAt(i + j)]);
					break;
				}
			}
			if (skipChars == 0) {
				return i;
			}
		}
		return -1;
	}

}