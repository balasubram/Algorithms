package com.bala.algs.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

@SuppressWarnings("unchecked")
public class ResizingArrayStack<Item> implements Stack<Item> {
	
	Item[] items;
	
	int size;
	
	public ResizingArrayStack(int size) {
		items = (Item[]) new Object[size];
		this.size = 0;
	}
	
	private void resizeArray(int newSize) {
		Item[] newItems = (Item[]) new Object[newSize];
		System.arraycopy(items, 0, newItems, 0, size);
		items = newItems;
	}

	@Override
	public void push(Item item) {
		if(size == (items.length/2 + 1)) {
			resizeArray( items.length * 2);
		} 
		items[size++] = item;
	}


	@Override
	public Item pop() {
		if(isEmpty()) {
			throw new NoSuchElementException("Stack is empty");
		}
		Item item = items[--size];
		if(size == (items.length /4 -1)) {
			resizeArray(items.length/2);
		}
		return item;
	}
	
	@Override
	public Item peek() {
		if(isEmpty()) {
			throw new NoSuchElementException("Stack is empty");
		}
		return items[size-1];
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
			
			int index = size;

			@Override
			public boolean hasNext() {
				return index >=0;
			}

			@Override
			public Item next() {
				if(hasNext()) {
					return items[--index];
				}
				throw new NoSuchElementException("No new elements");
			}
			
			@Override
			public void remove() {
	            throw new UnsupportedOperationException();
	        }
		};
	}

	public static void main(String[] args) {
		ResizingArrayStack<String> stack = new ResizingArrayStack<String>(5);
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-"))
				stack.push(item);
			else if (!stack.isEmpty())
				StdOut.print(stack.pop() + " ");
		}
		StdOut.println("(" + stack.size() + " left on stack)");
	}

}