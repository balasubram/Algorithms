package com.bala.algs.week3;

public class QuickSortSelection<T> {

	private void exchange(Comparable<T>[] a, int i, int j) {
		Comparable<T> tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

	@SuppressWarnings("unchecked")
	private int compare(Comparable<T> temp, Comparable<T> indexer) {
		return temp.compareTo((T) indexer);
	}

	private int partition(Comparable<T>[] a, int lo, int hi) {

		int k = lo;
		int i = lo;
		int j = hi + 1;

		while (true) {
			while (compare(a[++i], a[k]) < 0) {
				if (i == hi)
					break;
			}
			while (compare(a[k], a[--j]) < 0) {
				if (j == lo)
					break;
			}

			if (i >= j)
				break;

			exchange(a, i, j);
		}

		exchange(a, k, j);
		return j;
	}

	private int getIndex(Comparable<T>[] a, Comparable<T> indexer) {
		
		int lo = 0, hi = a.length - 1;
		int k = -1;
		while(lo <= hi) {
			k = partition(a, lo, hi);
			int cmp = compare(indexer, a[k]);
			if (cmp < 0) {
				hi = k - 1;
			} else if (cmp > 0) {
				lo = k + 1;
			} else {
				break;
			}
		}
		return k;
	}

	public static void main(String[] args) {
		String[] a = { "red", "bet", "or", "not", "to", "be", "this", "is", "a", "sample", "string", "sort", "example",
				"for", "shell", "sort" };
		QuickSortSelection<String> quick = new QuickSortSelection<>();
		System.out.println(quick.getIndex(a, "not"));
	}

}