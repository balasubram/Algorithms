package com.bala.algs.week4;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

@SuppressWarnings("unchecked")
public class MaxPQ<Key> implements Iterable<Key> {

	Key[] keys;

	int size;

	Comparator<Key> comparator;

	public MaxPQ() {
		this.size = 0;
		keys = (Key[]) new Object[10 + 1];
	}

	public MaxPQ(int max) {
		this.size = 0;
		keys = (Key[]) new Object[max + 1];
	}

	public MaxPQ(int max, Comparator<Key> comparator) {
		this.size = 0;
		this.comparator = comparator;
		this.keys = (Key[]) new Object[max + 1];
	}

	private boolean less(int i, int j) {
		if (comparator == null) {
			return ((Comparable<Key>) keys[i]).compareTo(keys[j]) < 0;
		} else {
			return comparator.compare(keys[i], keys[j]) < 0;
		}
	}

	private void exch(int i, int j) {
		Key temp = keys[i];
		keys[i] = keys[j];
		keys[j] = temp;
	}

	private void swim(int index) {
		while (index > 1 && less(index / 2, index)) {
			exch(index / 2, index);
			index = index / 2;
		}
	}

	private void sink(int index) {
		while (2 * index <= size) {
			int i = 2 * index;
			if (i < size && less(i, i + 1))
				i++;
			if (less(index, i)) {
				exch(i, index);
			}
			index = i;
		}
	}

	private void resizeArray(int newSize) {
		Key[] keys = (Key[]) new Object[newSize];
		System.arraycopy(this.keys, 0, keys, 0, size+1);
		this.keys = keys;
	}

	public void insert(Key v) {
		if (size + 1 == keys.length) {
			resizeArray(2 * keys.length);
		}
		keys[++size] = v;
		swim(size);
	}

	public Key max() {
		if (!isEmpty()) {
			return keys[1];
		}
		return null;

	}

	public Key delMax() {
		if (isEmpty())
			return null;
		Key key = keys[1];
		exch(1, size);
		keys[size--] = null;
		sink(1);
		if (size > 0 && size == keys.length / 4) {
			resizeArray(keys.length / 2);
		}
		return key;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	@Override
	public Iterator<Key> iterator() {
		return new MaxPQIterator();
	}

	class MaxPQIterator implements Iterator<Key> {

		MaxPQ<Key> temp;

		public MaxPQIterator() {
			this.temp = new MaxPQ<Key>(size(), comparator);
			for (int i = 0; i < size; i++) {
				temp.insert(keys[i + 1]);
			}
		}

		@Override
		public boolean hasNext() {
			return temp.isEmpty();
		}

		@Override
		public Key next() {
			if (hasNext()) {
				return temp.delMax();
			} else {
				throw new NoSuchElementException("No new elements");
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Opeation Not Supported");
		}

	}

	public static void main(String[] args) {
		MaxPQ<String> pq = new MaxPQ<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-"))
				pq.insert(item);
			else if (!pq.isEmpty())
				StdOut.print(pq.delMax() + " ");
		}
		StdOut.println("(" + pq.size() + " left on pq)");
	}

}