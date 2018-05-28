package com.bala.algs.week4;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.bala.algs.week2.Queue;
import com.bala.algs.week2.ResizingArrayQueue;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SequentialSearchST<Key extends Comparable<Key>, Value> implements ST<Key, Value>, Iterable<Key> {

	final class Node {
		Key key;
		Value value;
		Node next;

		public Node(Key key, Value value) {
			this.key = key;
			this.value = value;
		}
	}

	int size;

	Node symbolTable;

	public SequentialSearchST() {
		this.size = 0;
		this.symbolTable = null;
	}

	@Override
	public void put(Key key, Value val) {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null");
		}
		if (val == null) {
			delete(key);
		}

		Node temp = symbolTable;
		while (temp != null) {
			if (temp.key.equals(key)) {
				temp.value = val;
				return;
			}
			temp = temp.next;
		}

		Node newNode = new Node(key, val);
		newNode.next = symbolTable;
		symbolTable = newNode;
		size++;
	}

	@Override
	public Value get(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null");
		}
		Value val = null;

		Node temp = symbolTable;
		while (temp != null) {
			if (temp.key.equals(key)) {
				val = temp.value;
				break;
			}
			temp = temp.next;
		}
		return val;
	}

	@Override
	public void delete(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null");
		}
		Node temp = symbolTable;
		while (temp != null) {
			if (temp.key.equals(key)) {
				temp.next = temp.next.next;
				size--;
				break;
			}
			temp = temp.next;
		}
	}

	@Override
	public boolean contains(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null");
		}
		boolean result = false;
		Node temp = symbolTable;
		while (temp != null) {
			if (temp.key.equals(key)) {
				result = true;
				break;
			}
			temp = temp.next;
		}
		return result;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Key min() {
		throw new UnsupportedOperationException("Operation no supported");
	}

	@Override
	public Key max() {
		throw new UnsupportedOperationException("Operation no supported");
	}

	@Override
	public Key floor(Key key) {
		throw new UnsupportedOperationException("Operation no supported");
	}

	@Override
	public Key ceiling(Key key) {
		throw new UnsupportedOperationException("Operation no supported");
	}

	@Override
	public int rank(Key key) {
		throw new UnsupportedOperationException("Operation no supported");
	}

	@Override
	public Key select(int k) {
		throw new UnsupportedOperationException("Operation no supported");
	}

	@Override
	public void deleteMin() {
		throw new UnsupportedOperationException("Operation no supported");
	}

	@Override
	public void deleteMax() {
		throw new UnsupportedOperationException("Operation no supported");
	}

	@Override
	public int size(Key lo, Key hi) {
		throw new UnsupportedOperationException("Operation no supported");
	}

	@Override
	public Iterable<Key> keys(Key lo, Key hi) {
		throw new UnsupportedOperationException("Operation no supported");
	}

	@Override
	public Iterable<Key> keys() {
		Queue<Key> keys = new ResizingArrayQueue<>(size);
		Node temp = symbolTable;
		while (temp != null) {
			keys.enqueue(temp.key);
			temp = temp.next;
		}
		return keys;
	}

	@Override
	public Iterator<Key> iterator() {
		return new Iterator<Key>() {

			Node temp = symbolTable;

			@Override
			public boolean hasNext() {
				return temp != null;
			}

			@Override
			public Key next() {
				if (hasNext()) {
					Key key = temp.key;
					temp = temp.next;
					return key;
				} else {
					throw new NoSuchElementException("No new elements");
				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Operation Not Supported");
			}

		};
	}

	@Override
	public Iterable<Key> levelOrder() {
		throw new UnsupportedOperationException("Operation Not Supported");
	}

	public static void main(String[] args) {
		SequentialSearchST<String, Integer> st = new SequentialSearchST<String, Integer>();
		for (int i = 0; !StdIn.isEmpty(); i++) {
			String key = StdIn.readString();
			st.put(key, i);
		}
		for (String s : st.keys())
			StdOut.println(s + " " + st.get(s));
	}

}