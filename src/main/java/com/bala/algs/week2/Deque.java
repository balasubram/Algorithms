package com.bala.algs.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

	private Node<Item> first;

	private Node<Item> last;

	private int size;

	private final class Node<item> {
		Item item;
		Node<Item> next;
		Node<Item> prev;

		Node(Item item) {
			this.item = item;
		}
	}

	public Deque() {
		this.size = 0;
		this.first = null;
		this.last = null;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		// return the number of items on the deque
		return size;
	}

	public void addFirst(Item item) {
		// add the item to the front
		if (item == null) {
			throw new IllegalArgumentException("Arguement cannot be null");
		}

		Node<Item> node = new Node<Item>(item);
		node.next = first;		
		if (first != null) {
			first.prev = node;
			first = node;
		} else {
			last = node;
			first = node;
		}
		size++;
	}

	public void addLast(Item item) {
		// add the item to the end
		if (item == null) {
			throw new IllegalArgumentException("Arguement cannot be null");
		}
		Node<Item> node = new Node<Item>(item);
		node.prev = last;
		if (last == null) {
			last = node;
			first = node;
		} else {
			last.next = node;
			last = node;
		}
		size++;
	}

	public Item removeFirst() {
		// remove and return the item from the front
		if (isEmpty()) {
			throw new NoSuchElementException("Queue is Empty");
		}

		Item item = first.item;
		first = first.next;
		if(first == null) {
			last = null;
		} else {
			first.prev = null;
		}
		size--;
		return item;
	}

	public Item removeLast() {
		// remove and return the item from the end
		if (isEmpty()) {
			throw new NoSuchElementException("Queue is Empty");
		}
		Item item = last.item;
		last = last.prev;
		if(last == null) {
			first = null;
		} else {
			last.next = null;	
		}
		size--;
		return item;
	}

	public Iterator<Item> iterator() {
		// return an iterator over items in order from front to end
		return new Iterator<Item>() {

			Node<Item> temp = first;

			@Override
			public boolean hasNext() {
				return (temp != null);
			}

			@Override
			public Item next() {
				if (hasNext()) {
					Item item = temp.item;
					temp = temp.next;
					return item;
				}
				throw new NoSuchElementException("No new elements");
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Operation not supported");
			}

		};
	}

	public static void main(String[] args) {
		Deque<String> queue = new Deque<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-"))
				queue.addLast(item);
			else if (!queue.isEmpty())
				StdOut.print(queue.removeFirst() + " ");
		}
		StdOut.println("(" + queue.size() + " left on queue)");
	}

}