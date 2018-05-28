package com.bala.algs.week3;

import com.bala.algs.week2.SortingApiImpl;

@SuppressWarnings("unchecked")
public class BottomUpMergeSort<T> extends SortingApiImpl<T> {

	private void merge(Comparable<T>[] a, Comparable<T>[] aux, int lo, int mid, int hi) {

		System.arraycopy(a, lo, aux, lo, (hi-lo)+1);

		int i = lo, j = mid+1;
		for (int k = lo; k <= hi; k++) {
			if (i > mid)
				a[k] = aux[j++];
			else if (j > hi)
				a[k] = aux[i++];
			else if (less(aux[i], aux[j]))
				a[k] = aux[i++];
			else
				a[k] = aux[j++];
		}

	}

	private void sort(Comparable<T>[] a, Comparable<T>[] aux, int lo, int hi) {

		for(int len = 1; len < hi; len += len) {
			for(int i = lo; i < hi - len; i += len + len ) {
				int mid = i + len - 1;
				int j = Math.min(i + len + len - 1, hi);
				merge(a, aux, i, mid, j);
			}
		}

	}

	@Override
	public void sort(Comparable<T>[] a) {
		Comparable<T>[] aux = new Comparable[a.length];
		sort(a, aux, 0, a.length - 1);
	}

	public static void main(String[] args) {
		String[] a = { "to", "be", "or", "not", "to", "be", "this", "is", "a", "sample", "string", "sort", "example",
				"for", "shell", "sort" };
		BottomUpMergeSort<String> merge = new BottomUpMergeSort<>();
		System.out.println(merge.isSorted(a));
		merge.sort(a);
		merge.show(a);
		System.out.println(merge.isSorted(a));
	}

}