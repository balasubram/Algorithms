package com.bala.algs.week11;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;

public class MoveToFront {
	private static final int NO_OF_CHARS = 256;

	private static final char[] CHARACTERS = new char[NO_OF_CHARS];

	private static void constructCharacters() {
		for (int i = 0; i < NO_OF_CHARS; i++) {
			CHARACTERS[i] = (char) i;
		}
	}

	// apply move-to-front encoding, reading from standard input and writing to standard output
	public static void encode() {
		constructCharacters();
		while (!BinaryStdIn.isEmpty()) {
			char ch = BinaryStdIn.readChar();
			int count = 0;
			for (count = 0; CHARACTERS[count] != ch; count++) {
				;
			}

			for (int i = count; i > 0; i--) {
				CHARACTERS[i] = CHARACTERS[i - 1];
			}
			CHARACTERS[0] = ch;
			BinaryStdOut.write(count);
			StdOut.printf("%02x", count & 0xff);
		}

	}

	// apply move-to-front decoding, reading from standard input and writing to standard output
	public static void decode() {
		constructCharacters();
		while (!BinaryStdIn.isEmpty()) {
			int count = BinaryStdIn.readInt(8);
			char ch = CHARACTERS[count];
			for (int i = count; i > 0; i--) {
				CHARACTERS[i] = CHARACTERS[i - 1];
			}
			CHARACTERS[0] = ch;
			BinaryStdOut.write(ch);
		}

	}

	//
	// // if args[0] is '-', apply move-to-front encoding
	// // if args[0] is '+', apply move-to-front decoding
	public static void main(String[] args) {
		if (args.length == 0) {
			throw new IllegalArgumentException("No arguments are passed");
		} else {
			if (args[0].equals("-")) {
				encode();
			} else if (args[0].equals("+")) {
				decode();
			} else {
				throw new IllegalArgumentException("No Valid value");
			}
		}
	}

}