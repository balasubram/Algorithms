package com.bala.algs.week2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Required Arguments are not present");
		}

		int noOfElements = Integer.parseInt(args[0]);
		RandomizedQueue<String> queue = new RandomizedQueue<>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			queue.enqueue(item);
		}
		for (int i = 0; i < noOfElements; i++) {
			StdOut.println(queue.dequeue());
		}
	}

}