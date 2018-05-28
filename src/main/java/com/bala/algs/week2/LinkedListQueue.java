package com.bala.algs.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class LinkedListQueue<Item> implements Queue<Item> {

	int size;
	
	Node<Item> first;
	
	Node<Item> last;

	static class Node<Item> {
		Item item;
		Node<Item> next = null;

		Node(Item item) {
			this.item = item;
		}
	}

	public LinkedListQueue() {
		this.size = 0;
	}

	@Override
	public void enqueue(Item item) {
		Node<Item> node = new Node<Item>(item);
		if(isEmpty()) {
			first = node;
			last = node;
		} else {
			last.next = node;
			last = node;
		}
		size++;
	}

	@Override
	public Item dequeue() {
		if(isEmpty()) {
			throw new NoSuchElementException("Queue is Empty");
		}
		Item item = first.item;
		first = first.next;
		if(first == null) last = null;
		size--;
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
			throw new NoSuchElementException("Queue is Empty");
		}
		return first.item;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new Iterator<Item>() {
			
			Node<Item> temp = first;

			@Override
			public boolean hasNext() {
				return (temp != null);
			}

			@Override
			public Item next() {
				if(hasNext()) {
					Item item = temp.item;
					temp = temp.next;
					return item;	
				}
				throw new NoSuchElementException("No new elements in the queue");
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException("Operation Not Suppoprted");
			}
			
		};
	}
	
    public static void main(String[] args) {
    		ResizingArrayQueue<String> queue = new ResizingArrayQueue<String>(5);
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