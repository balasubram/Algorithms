package com.bala.algs.week2;

public class InsertionSort<T> extends SortingApiImpl<T> {

	@Override
	public void sort(Comparable<T>[] a) {
		for (int i = 1; i < a.length; i++) {
			for (int j = 0; j <= i; j++) {
				if (less(a[i], a[j])) {
					exch(a, i, j);
				}
			}
		}
	}

	public static void main(String[] args) {
		String[] a = { "to", "be", "or", "not", "to", "be" };
		InsertionSort<String> insertion = new InsertionSort<>();
		System.out.println(insertion.isSorted(a));
		insertion.sort(a);
		insertion.show(a);
		System.out.println(insertion.isSorted(a));
	}

}