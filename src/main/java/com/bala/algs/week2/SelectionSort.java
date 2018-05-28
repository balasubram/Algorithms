package com.bala.algs.week2;

public class SelectionSort<T> extends SortingApiImpl<T> {

	@Override
	public void sort(Comparable<T>[] a) {
		for (int i = 0; i < a.length; i++) {
			int min = i;
			for (int j = i + 1; j < a.length; j++) {
				if (less(a[j], a[min])) {
					min = j;
				}
				exch(a, i, min);
			}
		}

	}

	public static void main(String[] args) {
		String[] a = { "to", "be", "or", "not", "to", "be" };
		SelectionSort<String> selection = new SelectionSort<>();
		System.out.println(selection.isSorted(a));
		selection.sort(a);
		selection.show(a);
		System.out.println(selection.isSorted(a));
	}

}