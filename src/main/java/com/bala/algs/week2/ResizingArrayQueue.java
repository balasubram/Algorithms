package com.bala.algs.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

@SuppressWarnings("unchecked")
public class ResizingArrayQueue<Item> implements Queue<Item> {
	
	Item[] items;
	
	int size;
	
	int first;
	
	int last;
	
	public ResizingArrayQueue(int size) {
		items = (Item[]) new Object[size];
		this.size = 0;
		this.first = 0;
		this.last = 0;
	}
	
	private void resizeArray(int newSize) {
		Item[] newItems = (Item[]) new Object[newSize];
		for(int i = 0, j = first; j < last; i++, j++) {
			newItems[i] = items[j];
		}
		items = newItems;
		last = (last-first);
		first = 0;
	}

	@Override
	public void enqueue(Item item) {
		if(size == items.length/2) {
			resizeArray(items.length * 2);
		}
		items[last++] = item;
		if(last == items.length) {
			last = 0;
		}
		size++;
	}

	@Override
	public Item dequeue() {
		if(isEmpty() && first > last) {
			throw new NoSuchElementException("Queue is Empty");
		}
		Item item = items[first++];
		items[first-1] = null;
		size--;
		if(first == items.length) first = 0;
		if(size == items.length / 4) {
			resizeArray(items.length/2);
		}
		return item;
	}
	
	@Override
	public Item peek() {
		if(isEmpty()) {
			throw new NoSuchElementException("Queue is Empty");
		}
		return items[first];
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
			
			int index = first;
			@Override
			public boolean hasNext() {
				return index < size;
			}

			@Override
			public Item next() {
				if(hasNext()) {
					Item item = items[(index + first) % items.length];
					index++;
					return item;
				}
				throw new NoSuchElementException("All elements are retrieved");
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