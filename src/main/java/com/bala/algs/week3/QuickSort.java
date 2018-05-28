package com.bala.algs.week3;

import com.bala.algs.week2.SortingApiImpl;

public class QuickSort<T> extends SortingApiImpl<T> {

	private int partition(Comparable<T>[] a, int lo, int hi) {
		
		int k = lo;
		int i = lo, j = hi+1;
		while(true) {
			
			while(less(a[++i], a[k])) {
				if(i == hi) break;
			}
			
			while(less(a[k],a[--j])) {
				if(j == lo) break;
			}
			
			if (i >= j) break;
			
			exch(a, i, j);
			
		}
		exch(a,k,j);
		return j;
	}
	
	
	private void sort(Comparable<T>[] a, int lo, int hi) {
		if(lo < hi) {
			int k = partition(a, lo, hi);
			sort(a, lo, k-1);
			sort(a, k+1, hi);	
		}
	}
	
	@Override
	public void sort(Comparable<T>[] a) {
		sort(a, 0, a.length - 1);
	}
	
	public static void main(String[] args) {
		String[] a = { "red", "bet", "or", "not", "to", "be", "this", "is", "a", "sample", "string", "sort", "example",
				"for", "shell", "sort" };
		QuickSort<String> quick = new QuickSort<>();
		System.out.println(quick.isSorted(a));
		quick.sort(a);
		quick.show(a);
		System.out.println(quick.isSorted(a));
	}

}