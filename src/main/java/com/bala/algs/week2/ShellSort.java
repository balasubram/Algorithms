package com.bala.algs.week2;

public class ShellSort<T> extends SortingApiImpl<T> {

	@Override
	public void sort(Comparable<T>[] a) {
		int h = 0;
		while (h < a.length)
			h = 3 * h + 1;
		while (h >= 1) {
			for (int i = h; i < a.length; i++) {
				for (int j = i; j >= h; j -= h) {
					if (less(a[j], a[j - h])) {
						exch(a, j, j - h);
					}
				}
			}
			h = h / 3;
		}

	}

	public static void main(String[] args) {
		String[] a = { "to", "be", "or", "not", "to", "be", "this", "is", "a", "sample", "string", "sort", "example",
				"for", "shell", "sort" };
		ShellSort<String> shell = new ShellSort<>();
		System.out.println(shell.isSorted(a));
		shell.sort(a);
		shell.show(a);
		System.out.println(shell.isSorted(a));
	}

}