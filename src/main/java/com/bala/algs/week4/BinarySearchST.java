package com.bala.algs.week4;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.bala.algs.week2.LinkedListQueue;
import com.bala.algs.week2.Queue;
import com.bala.algs.week2.ResizingArrayQueue;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

@SuppressWarnings("unchecked")
public class BinarySearchST<Key extends Comparable<Key>, Value> implements ST<Key, Value>, Iterable<Key> {

	private Key[] keys;

	private Value[] values;

	private int size;

	public BinarySearchST(int size) {
		this.keys = (Key[]) new Comparable[size];
		this.values = (Value[]) new Object[size];
		this.size = 0;
	}

	private void resizeArray(int newSize) {
		Key[] keys = (Key[]) new Comparable[newSize];
		Value[] values = (Value[]) new Object[newSize];
		System.arraycopy(this.keys, 0, keys, 0, size);
		System.arraycopy(this.values, 0, values, 0, size);
		this.keys = keys;
		this.values = values;

	}

	@Override
	public void put(Key key, Value val) {
		if (key == null) {
			throw new IllegalArgumentException("Argument Key is null");
		}
		if (val == null) {
			delete(key);
		}

		int rank = rank(key);

		if (rank < size && keys[rank].compareTo(key) == 0) {
			values[rank] = val;
			return;
		}

		if (size == keys.length / 2) {
			resizeArray(2 * keys.length);
		}

		for (int i = rank; i < size; i++) {
			keys[i + 1] = keys[i];
			values[i + 1] = values[i];
		}
		keys[rank] = key;
		values[rank] = val;
		size++;
	}

	@Override
	public Value get(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument Key is null");
		}
		int rank = rank(key);

		Value val = null;

		if (rank < size && keys[rank].compareTo(key) == 0) {
			val = values[rank];
		}
		return val;
	}

	@Override
	public void delete(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument Key is null");
		}

		int rank = rank(key);
		if (rank < size && keys[rank].compareTo(key) == 0) {
			for (int i = rank; i < size; i++) {
				keys[i] = keys[i + 1];
				values[i] = values[i + 1];
			}
			size--;
		}
	}

	@Override
	public boolean contains(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument Key is null");
		}
		boolean result = false;
		int rank = rank(key);
		if (rank < size && keys[rank].compareTo(key) == 0) {
			result = true;
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
		if (isEmpty()) {
			throw new NoSuchElementException("Symbol Table is Empty");
		}
		return keys[0];
	}

	@Override
	public Key max() {
		if (isEmpty()) {
			throw new NoSuchElementException("Symbol Table is Empty");
		}
		return keys[size - 1];
	}

	@Override
	public Key floor(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument Key is null");
		}
		Key temp = null;
		int rank = rank(key);
		if (rank < size && keys[rank].compareTo(key) == 0) {
			temp = keys[rank];
		} else if (rank == 0 && isEmpty()) {
			temp = null;
		} else if (rank == 0) {
			temp = null;
		} else {
			temp = keys[rank - 1];
		}
		return temp;
	}

	@Override
	public Key ceiling(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument Key is null");
		}
		Key temp = null;
		int rank = rank(key);
		if (rank == size) {
			temp = null;
		} else {
			temp = keys[rank];
		}
		return temp;
	}

	@Override
	public int rank(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		int lo = 0, hi = size;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			int cmp = key.compareTo(keys[mid]);
			if (cmp < 0) {
				hi = mid - 1;
			} else if (cmp > 0) {
				lo = mid + 1;
			} else {
				return mid;
			}
		}
		return lo;
	}

	@Override
	public Key select(int k) {
		if (isEmpty()) {
			throw new NoSuchElementException("Symbol Table is Empty");
		}
		if (k > size) {
			throw new IllegalArgumentException("Index is greater than the symbol table size");
		}
		return keys[k];
	}

	@Override
	public void deleteMin() {
		delete(min());

	}

	@Override
	public void deleteMax() {
		delete(max());
	}

	@Override
	public int size(Key lo, Key hi) {
		if (lo == null)
			throw new IllegalArgumentException("first argument to size() is null");
		if (hi == null)
			throw new IllegalArgumentException("second argument to size() is null");

		int size = 0;
		if (lo.compareTo(hi) > 0) {
			size = 0;
		}
		int rank1 = rank(lo);
		int rank2 = rank(hi);
		if (rank2 == size) {
			size = rank2 - rank1;
		} else {
			size = rank2 - rank1 + 1;
		}
		return size;
	}

	@Override
	public Iterable<Key> keys(Key lo, Key hi) {
		if (lo == null)
			throw new IllegalArgumentException("first argument to size() is null");
		if (hi == null)
			throw new IllegalArgumentException("second argument to size() is null");

		Queue<Key> keys = new LinkedListQueue<>();
		if (lo.compareTo(hi) < 0) {
			int rank1 = rank(lo);
			int rank2 = rank(hi);
			for (int index = rank1; index < rank2; index++) {
				keys.enqueue(this.keys[index]);
			}
			if (rank2 < size) {
				keys.enqueue(this.keys[rank2]);
			}
		}
		return keys;
	}

	@Override
	public Iterable<Key> keys() {
		Queue<Key> keys = new ResizingArrayQueue<>(size);
		for (Key key : this.keys) {
			keys.enqueue(key);
		}
		return keys;
	}

	@Override
	public Iterator<Key> iterator() {
		return new Iterator<Key>() {

			int index = 0;

			@Override
			public boolean hasNext() {
				return index < size;
			}

			@Override
			public Key next() {
				if (hasNext()) {
					return keys[index++];
				} else {
					throw new NoSuchElementException("No new elements");
				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Operation Not supported");
			}
		};
	}
	
	@Override
	public Iterable<Key> levelOrder() {
		throw new UnsupportedOperationException("Operation Not Supported");
	}

	public static void main(String[] args) {
		BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>(10);
		for (int i = 0; !StdIn.isEmpty(); i++) {
			String key = StdIn.readString();
			st.put(key, i);
		}
		for (String s : st.keys())
			StdOut.println(s + " " + st.get(s));
	}

}