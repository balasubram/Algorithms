package com.bala.algs.week11;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * Project: Burrows–Wheeler Author: CtheSky Create Date: 2017/2/14 Description: All rights reserved.
 */
public class BurrowsWheeler {
	private static final int R = 256;

	// apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
	public static void encode() {
		String s = BinaryStdIn.readString();
		CircularSuffixArray circular = new CircularSuffixArray(s);

		int len = s.length();
		for (int i = 0; i < len; i++)
			if (circular.index(i) == 0)
				BinaryStdOut.write(i);
		for (int i = 0; i < len; i++)
			BinaryStdOut.write(s.charAt((circular.index(i) + len - 1) % len), 8);

		BinaryStdOut.close();
	}

	// apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
	public static void decode() {
		int first = BinaryStdIn.readInt();
		String s = BinaryStdIn.readString();

		int len = s.length();
		char[] t = s.toCharArray();
		char[] sorted = new char[len];
		// build sorted array
		int[] count = new int[R + 1];
		for (int i = 0; i < len; i++)
			count[t[i] + 1]++;
		for (int r = 0; r < R; r++)
			count[r + 1] += count[r];
		for (int i = 0; i < len; i++)
			sorted[count[t[i]]++] = t[i];

		// get count array of sorted
		count = new int[R + 1];
		for (int i = 0; i < len; i++)
			count[sorted[i] + 1]++;
		for (int r = 0; r < R; r++)
			count[r + 1] += count[r];

		// build next array
		int[] next = new int[len];
		for (int j = 0; j < len; j++) {
			int i = count[t[j]]++;
			next[i] = j;
		}

		// reconstruct msg
		for (int i = 0, j = first; i < len; i++) {
			BinaryStdOut.write(sorted[j]);
			j = next[j];
		}
		BinaryStdOut.close();
	}

	// if args[0] is '-', apply Burrows-Wheeler encoding
	// if args[0] is '+', apply Burrows-Wheeler decoding
	public static void main(String[] args) {
		if (args.length == 0)
			return;
		if ("-".equals(args[0]))
			encode();
		else if ("+".equals(args[0]))
			decode();
	}
	
}