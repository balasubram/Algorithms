package com.bala.algs.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

@SuppressWarnings("unchecked")
public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] items;

	private int size;

	public RandomizedQueue() {
		this.size = 0;
		items = (Item[]) new Object[10];
	}

	private void resizeArray(int newSize) {
		Item[] newItems = (Item[]) new Object[newSize];
		System.arraycopy(items, 0, newItems, 0, size);
		items = newItems;
	}

	public boolean isEmpty() {
		// is the randomized queue empty?
		return size == 0;
	}

	public int size() {
		// return the number of items on the randomized queue
		return size;
	}

	public void enqueue(Item item) {
		// add the item
		if (item == null) {
			throw new IllegalArgumentException("Arguement cannot be null");
		}
		if (size == (items.length / 2)) {
			resizeArray(2 * items.length);
		}
		items[size++] = item;
	}

	public Item dequeue() {
		// remove and return a random item
		if (isEmpty()) {
			throw new NoSuchElementException("Queue is Empty");
		}
		int randIndex = StdRandom.uniform(0,size);
		Item item = items[randIndex];
		items[randIndex] = items[size-1];
		items[size-1] = null;
		size--;
		if (size == items.length / 4) {
			resizeArray(items.length / 2);
		}
		return item;
	}

	public Item sample() {
		// return a random item (but do not remove it)
		if (isEmpty()) {
			throw new NoSuchElementException("Queue is Empty");
		}

		int randIndex = StdRandom.uniform(0, size);
		return items[randIndex];
	}

	public Iterator<Item> iterator() {
		// return an independent iterator over items in random order
		return new RandomIterator();
	}

	private class RandomIterator implements Iterator<Item> {

		int index = 0;

		int[] indices = new int[size];

		RandomIterator() {
			for (int i = 0; i < size; i++) {
				indices[i] = i;
			}
			StdRandom.shuffle(indices);
		}

		@Override
		public boolean hasNext() {
			return (index < size);
		}

		@Override
		public Item next() {
			if (hasNext()) {
				Item item = items[indices[index++]];
				return item;
			}
			throw new NoSuchElementException("No new elements");
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Operation not supported");
		}

	}

	public static void main(String[] args) {
		// unit testing (optional)
		RandomizedQueue<String> queue = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-"))
				queue.enqueue(item);
			else if (!queue.isEmpty())
				StdOut.print(queue.dequeue() + " ");
		}
		StdOut.println("(" + queue.size() + " left on queue)");
	}

}