package com.bala.algs.week1;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BinarySearch {

	public BinarySearch() {

	}

	public static int indexOf(int[] a, int key) {
		int lo = 0;
		int hi = a.length;
		int mid = (lo + hi)/2;
		while(lo <= hi) {
			if(key > a[mid]) lo = mid + 1;
			else if(key < a[mid]) hi = mid - 1;
			else return mid;
		}
		return -1;

	}

	public static void main(String[] args) {

		// read the integers from a file
		In in = new In(args[0]);
		int[] whitelist = in.readAllInts();

		// sort the array
		Arrays.sort(whitelist);

		// read integer key from standard input; print if not in whitelist
		while (!StdIn.isEmpty()) {
			int key = StdIn.readInt();
			if (BinarySearch.indexOf(whitelist, key) == -1)
				StdOut.println(key);
		}
	}

}