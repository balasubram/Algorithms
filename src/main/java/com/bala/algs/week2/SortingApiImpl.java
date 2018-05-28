package com.bala.algs.week2;

import edu.princeton.cs.algs4.StdOut;

public abstract class SortingApiImpl<T> implements SortingApi<T> {

	@SuppressWarnings("unchecked")
	protected boolean less(Comparable<T> v, Comparable<T> w) {
		return v.compareTo((T) w) < 0;
	}
	
	@SuppressWarnings("unchecked")
	protected boolean compare(Comparable<T> v, Comparable<T> w) {
		return v.compareTo((T) w) <= 0;
	}


	protected void exch(Comparable<T>[] a, int i, int j) {
		Comparable<T> t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	protected void show(Comparable<T>[] a) {
		// Print the array, on a single line.
		for (int i = 0; i < a.length; i++)
			StdOut.print(a[i] + " ");
		StdOut.println();
	}

	@Override
	public boolean isSorted(Comparable<T>[] a) {
		boolean result = true;
		for (int i = 0; i < a.length - 1; i++) {
			if (!compare(a[i], a[i + 1])) {
				result = false;
				break;
			}
		}
		return result;
	}

}