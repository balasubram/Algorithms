package com.bala.algs.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

@SuppressWarnings("unchecked")
public class ResizingArrayBag<Item> implements Bag<Item> {

	Item[] items;

	int size;

	public ResizingArrayBag(int size) {
		items = (Item[]) new Object[size];
		this.size = 0;
	}

	private void resizeArray(int newSize) {
		Item[] newItems = (Item[]) new Object[newSize];
		System.arraycopy(items, 0, newItems, 0, size);
		items = newItems;
	}

	public void add(Item item) {
		if (size == (items.length / 2 + 1)) {
			resizeArray(2 * items.length);
		}
		items[size++] = item;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	@Override
	public Iterator<Item> iterator() {
		return new Iterator<Item>() {

			int index = 0;

			@Override
			public boolean hasNext() {
				return index <= size - 1;
			}

			@Override
			public Item next() {
				if (hasNext()) {
					return items[index++];
				} else {
					throw new NoSuchElementException("No new elements");
				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Remove operation is not supported");
			}
		};
	}

	public static void main(String[] args) {
		ResizingArrayBag<String> bag = new ResizingArrayBag<String>(2);
		bag.add("Hello");
		bag.add("World");
		bag.add("how");
		bag.add("are");
		bag.add("you");

		for (String s : bag)
			StdOut.println(s);
	}

}