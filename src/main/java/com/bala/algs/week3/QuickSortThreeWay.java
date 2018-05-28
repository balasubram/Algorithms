package com.bala.algs.week3;

import com.bala.algs.week2.SortingApiImpl;

public class QuickSortThreeWay<T> extends SortingApiImpl<T> {
	
	@SuppressWarnings("unchecked")
	private int compararer(Comparable<T> u, Comparable<T> v) {
		return u.compareTo((T)v);
	}

	private void sort(Comparable<T>[] a, int lo, int hi) {
		if(lo < hi) {
			int i = lo;
			int lt = lo, gt = hi;
			while(i <= gt) {
				int cmp = compararer(a[i], a[lt]);
				if(cmp < 0) {
					exch(a, i, lt);
					i++;
					lt++;
				} else if (cmp > 0) {
					exch(a, i, gt);
					gt--;
				} else {
					i++;
				}
			}
			sort(a, lo, lt - 1);
			sort(a, gt + 1, hi);	
		}
	}
	
	@Override
	public void sort(Comparable<T>[] a) {
		sort(a, 0, a.length - 1);
	}
	
	public static void main(String[] args) {
		String[] a = { "red", "bet", "or", "not", "to", "be", "this", "is", "a", "sample", "string", "sort", "example",
				"for", "shell", "sort" };
		QuickSortThreeWay<String> quick = new QuickSortThreeWay<>();
		System.out.println(quick.isSorted(a));
		quick.sort(a);
		quick.show(a);
		System.out.println(quick.isSorted(a));
	}

}