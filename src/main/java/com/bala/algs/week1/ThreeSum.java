package com.bala.algs.week1;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class ThreeSum {

	private static int count(int[] a) {
		int count = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = i + 1; j < a.length; j++) {
				for (int k = j + 1; k < a.length; k++) {
					if (a[i] + a[j] + a[k] == 0) {
						count++;
					}
				}
			}
		}
		return count;
	}
	
	private static int countFast(int[] a) {
		Arrays.sort(a);
		int count = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = i + 1; j < a.length; j++) {
				int intermediateSum = a[i] + a[j];
				int k = 0 - intermediateSum;
				int index = BinarySearch.indexOf(a, k);
				if( index != -1 && index > j) {
					count ++;
				}
			}
		}
		return count;
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		int[] a = in.readAllInts();

		Stopwatch timer = new Stopwatch();
		int count = count(a);
		StdOut.println("elapsed time = " + timer.elapsedTime());
		StdOut.println(count);
		int anotherCount = countFast(a);
		StdOut.println("elapsed time = " + timer.elapsedTime());
		StdOut.println(anotherCount);
		
	}

}