package com.bala.algs.week2;

import java.util.Iterator;

@SuppressWarnings("unchecked")
public class FixedCapacityStack<Item> implements Stack<Item> {

	Item[] items;

	int size;

	public FixedCapacityStack(int capacity) {
		this.size = 0;
		items = (Item[]) new Object[capacity];
	}

	@Override
	public void push(Item item) {
		if (size >= items.length) {
			throw new UnsupportedOperationException("The stack has reached its capacity");
		}
		items[size++] = item;
	}

	@Override
	public Item pop() {
		if (size == 0) {
			throw new UnsupportedOperationException("Stack Underflow");
		}
		Item item = items[--size];
		items[size] = null;
		return item;
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
	public Item peek() {
		if (size == 0) {
			throw new UnsupportedOperationException("Stack Underflow");
		}
		Item item = items[size - 1];
		return item;
	}

	@Override
	public Iterator<Item> iterator() {
		return new Iterator<Item>() {

			int index = 0;
			@Override
			public boolean hasNext() {
				return index < size;
			}

			@Override
			public Item next() {
				if(hasNext()) {
					Item item = items[index++];
					return item;
				} 
				throw new UnsupportedOperationException("No more elements");
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException("Operation is not supported");
			}
		};
	}

}