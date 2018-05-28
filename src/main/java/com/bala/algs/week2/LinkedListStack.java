package com.bala.algs.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class LinkedListStack<Item> implements Stack<Item> {

	Node<Item> items = null;

	int size;

	static class Node<Item> {
		Item item;
		Node<Item> next = null;

		Node(Item item) {
			this.item = item;
		}
	}

	public LinkedListStack() {
		this.size = 0;
	}

	@Override
	public void push(Item item) {
		Node<Item> node = new Node<Item>(item);
		node.next = items;
		items = node;
		size++;

	}

	@Override
	public Item pop() {
		if(isEmpty()) {
			throw new NoSuchElementException("Stack is Empty");
		}
		size--;
		Item item = items.item;
		items = items.next;
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
		if(isEmpty()) {
			throw new NoSuchElementException("Stack is Empty");
		}
		return items.item;
	}

	@Override
	public Iterator<Item> iterator() {
		return new Iterator<Item>() {
			
			Node<Item> temp = items;

			@Override
			public boolean hasNext() {
				return (temp != null) ;
			}

			@Override
			public Item next() {
				if(hasNext()) {
					Item item = temp.item;
					temp = temp.next;
					return item;
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
		LinkedListStack<String> stack = new LinkedListStack<String>();
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