package com.bala.algs.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class LinkedListBag<Item> implements Bag<Item> {

	Node<Item> items = null;

	int size;

	static class Node<Item> {
		Item item;
		Node<Item> next = null;

		Node(Item item) {
			this.item = item;
		}
	}

	public LinkedListBag() {
		this.size = 0;
	}

	@Override
	public void add(Item item) {
		Node<Item> node = new Node<Item>(item);
		node.next = items;
		items = node;
		size++;
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
	public Iterator<Item> iterator() {
		return new Iterator<Item>() {

			Node<Item> temp = items;

			@Override
			public boolean hasNext() {
				return temp != null;
			}

			@Override
			public Item next() {
				if(hasNext()) {
					Item item = temp.item;
					temp = temp.next;
					return item;
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