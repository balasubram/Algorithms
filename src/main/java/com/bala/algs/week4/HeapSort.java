package com.bala.algs.week4;

import com.bala.algs.week2.SortingApiImpl;

import edu.princeton.cs.algs4.Heap;

public class HeapSort<T> extends  SortingApiImpl<T> {

	
	private void sink(Comparable<T>[] a, int k, int length) {
		while( 2 * k <= length) {
			int i = 2 * k;
			if(i < length && less(a[i-1], a[i+1-1])) i++;
			if(!less(a[k-1], a[i-1])) break;
			exch(a, k-1, i-1);
			k = i;
		}
	}
	
	@Override
	public void sort(Comparable<T>[] a) {
		int size = a.length;
		for(int k = size/2; k >= 1; k--) {
			sink(a, k, size);
		}
		
		while(size > 1) {
			exch(a, 0, size-- -1);
			sink(a, 1, size);
		}
		
	}


	public static void main(String[] args) {
		String[] a = { "red", "bet", "or", "not", "to", "be", "this", "is", "a", "sample", "string", "sort", "example",
				"for", "shell", "sort" };
		HeapSort<String> heap = new HeapSort<>();
		System.out.println(heap.isSorted(a));
		heap.sort(a);
		heap.show(a);
		System.out.println(heap.isSorted(a));
		Heap.sort(a);
	}

}