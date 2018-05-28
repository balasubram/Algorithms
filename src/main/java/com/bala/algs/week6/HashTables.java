package com.bala.algs.week6;

import com.bala.algs.week2.LinkedListQueue;
import com.bala.algs.week2.Queue;

@SuppressWarnings("unchecked")
public class HashTables<Key, Value> {

	final class Node {
		Key key;
		Value value;
		Node next;

		Node(Key key, Value value) {
			this.key = key;
			this.value = value;
		}

	}

	private int size;

	private Node[] nodes;

	public HashTables(int initialCapacity) {
		this.nodes = (Node[]) new Object[initialCapacity];
		this.size = 0;
	}

	private int computeIndex(Key key) {
		return (key.hashCode() & 0x7fffffff) % nodes.length;
	}

	private void resize(int newSize) {
		HashTables<Key, Value> temp = new HashTables<>(newSize);
		for (Node node : nodes) {
			while (node != null) {
				temp.put(node.key, node.value);
				node = node.next;
			}
		}
		this.nodes = temp.nodes;
		this.size = temp.size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public boolean contains(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument key is null");
		}
		boolean result = false;
		int index = computeIndex(key);
		Node node = nodes[index];
		while (node != null) {
			if (node.key.equals(key)) {
				result = true;
				break;
			}
			node = node.next;
		}
		return result;
	}

	public Value get(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument key is null");
		}
		Value val = null;
		int index = computeIndex(key);
		Node node = nodes[index];
		while (node != null) {
			if (node.key.equals(key)) {
				val = node.value;
				break;
			}
			node = node.next;
		}
		return val;
	}

	public void put(Key key, Value val) {
		if (key == null) {
			throw new IllegalArgumentException("Argument key is null");
		}
		if (val == null) {
			delete(key);
			return;
		}
		if (nodes.length * 10 == size) {
			resize(2 * nodes.length);
		}
		int index = computeIndex(key);
		Node node = nodes[index];
		Node temp = new Node(key, val);
		if (node == null) {
			nodes[index] = temp;
		} else {
			while (node.next != null) {
				node = node.next;
			}
			node.next = temp;
		}
		size++;
	}

	public void delete(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument key is null");
		}
		if (nodes.length * 10 == size / 4) {
			resize(nodes.length / 2);
		}

		int index = computeIndex(key);
		Node node = nodes[index];
		nodes[index] = delete(node, key);
	}

	private Node delete(Node node, Key key) {
		if (node != null) {
			if (node.key.equals(key)) {
				size--;
				return node.next;
			}
			node.next = delete(node, key);
		}
		return node;
	}

	public Iterable<Key> keys() {
		Queue<Key> keys = new LinkedListQueue<Key>();
		for (Node node : nodes) {
			while (node != null) {
				keys.enqueue(node.key);
				node = node.next;
			}
		}
		return keys;
	}

}