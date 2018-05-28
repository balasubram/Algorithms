package com.bala.algs.week9;

public class MSD {

	private static int R = 256;

	public MSD() {

	}

	public String[] sort(String[] strings, int length) {
		String[] aux = new String[strings.length];
		sort(strings, length, 0, strings.length - 1, 0, aux);
		return strings;

	}

	private void sort(String[] strings, int length, int lo, int hi, int index, String[] aux) {

		if (lo >= hi)
			return;

		if (index >= length)
			return;

		int[] count = new int[R + 1];

		for (int i = lo; i <= hi; i++) {
			count[strings[i].charAt(index) + 1]++;
		}

		for (int i = 0; i < R; i++) {
			count[i + 1] += count[i];
		}

		for (int i = lo; i <= hi; i++) {
			aux[count[strings[i].charAt(index)]++] = strings[i];
		}

		for (int i = lo; i < hi; i++) {
			strings[i] = aux[i];
		}

		for (int i = 0; i < R; i++) {
			sort(strings, length, lo + count[i], lo + count[i + 1], index + 1, aux);
		}

	}

	public static void main(String[] args) {
		String abc = "this test does with sort";
		String splits[] = abc.split("\\s+");
		MSD msd = new MSD();
		String[] sorted = msd.sort(splits, 4);
		for (String sortedString : sorted) {
			System.out.println(sortedString);
		}

	}

}