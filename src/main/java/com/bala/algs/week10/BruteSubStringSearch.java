package com.bala.algs.week10;

public class BruteSubStringSearch {

	public static int methodOne(String pattern, String matcher) {
		int m = pattern.length();
		int n = matcher.length();
		for (int i = 0; i < m - n; i++) {
			int j = 0;
			for (; j < n; j++) {
				if (pattern.charAt(i + j) != matcher.charAt(j)) {
					break;
				}
			}

			if (j == n) {
				return i;
			}

		}
		return -1;
	}

	public static int methodTwo(String pattern, String matcher) {
		int m = pattern.length();
		int n = matcher.length();
		for (int i = 0, j = 0; i < m - n; i++) {
			if (pattern.charAt(i) == matcher.charAt(j)) {
				j++;
			} else {
				i += j;
			}

			if (j == n) {
				return i;
			}

		}
		return -1;
	}

}