package com.bala.algs.week9;

public class LSD {

	private static final int R = 256;

	public LSD() {

	}

	public String[] sort(String[] strings, int length) {

		String[] aux = new String[strings.length];

		for (int index = length - 1; index >= 0; index--) {
			int[] count = new int[R + 1];
			for (int i = 0; i < strings.length; i++) {
				count[strings[i].charAt(index) + 1]++;
			}

			for (int r = 0; r < R; r++) {
				count[r + 1] += count[r];
			}
			// move data
			for (int i = 0; i < strings.length; i++) {
				aux[count[strings[i].charAt(index)]++] = strings[i];
			}

			for (int i = 0; i < strings.length; i++) {
				strings[i] = aux[i];
			}
		}

		return strings;
	}
	
	public static void main(String[] args) {
		String abc = "this test does with sort";
		String splits[] = abc.split("\\s+");
		LSD lsd = new LSD();
		lsd.sort(splits, 4);

	}

}