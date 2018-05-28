package com.bala.algs.week2;

public interface SortingApi<T> {

	void sort(Comparable<T>[] a);

	boolean isSorted(Comparable<T>[] a);

}