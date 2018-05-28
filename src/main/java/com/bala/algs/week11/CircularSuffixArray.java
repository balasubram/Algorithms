package com.bala.algs.week11;

import java.util.Arrays;
import java.lang.IllegalArgumentException;

public class CircularSuffixArray {

	private final String string;

	private final Integer[] indexes;

	public CircularSuffixArray(String string) {
		// circular suffix array of s
		if (string == null || string.equals(""))
			throw new IllegalArgumentException("Can't get suffix array for empty string!");
		this.string = string;

		this.indexes = new Integer[string.length()];
		for (int index = 0; index < string.length(); index++) {
			this.indexes[index] = index;
		}
		Arrays.sort(indexes, (first, second) -> {
			int firstIndex = first;
			int secondIndex = second;

			for (int i = 0; i < string.length(); i++) {
				if (firstIndex > string.length() - 1)
					firstIndex = 0;
				if (secondIndex > string.length() - 1)
					secondIndex = 0;
				if (string.charAt(firstIndex) < string.charAt(secondIndex))
					return -1;
				else if (string.charAt(firstIndex) > string.charAt(secondIndex))
					return 1;
				firstIndex++;
				secondIndex++;
			}
			return 0;
		});
	}

	public int length() {
		// length of s
		return string.length();
	}

	public int index(int i) {
		// returns index of ith sorted suffix
		if (i < 0 || i > length()) {
			throw new IllegalArgumentException("Argument is out of range");
		}
		return indexes[i];
	}

	public static void main(String[] args) {
		// unit testing (required)
	}

}