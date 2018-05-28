package com.bala.algs.week10;

import com.bala.algs.week2.LinkedListQueue;
import com.bala.algs.week2.Queue;

import edu.princeton.cs.algs4.StdOut;

public class TernarySearchTries<Value> implements TriesST<Value> {

	private class Node {
		char character;
		Value value;
		Node left, mid, right;
	}

	private Node root;

	private int size;

	public TernarySearchTries() {
		this.size = 0;
	}

	@Override
	public void put(String key, Value val) {
		if (key == null) {
			throw new IllegalArgumentException("Argument is null");
		}

		if (val == null)
			delete(key);

		root = put(root, key, val, 0);
	}

	private Node put(Node node, String key, Value val, int index) {
		char character = key.charAt(index);
		if (node == null) {
			node = new Node();
			node.character = character;
		}

		if (index == key.length() - 1) {
			if (node.value == null) {
				size++;
			}
			node.value = val;
		} else {
			if (character < node.character)
				node.left = put(node.left, key, val, index);
			else if (character > node.character)
				node.right = put(node.right, key, val, index);
			else
				node.mid = put(node.mid, key, val, index + 1);
		}

		return node;
	}

	@Override
	public Value get(String key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument is null");
		}

		if (key.length() < 1)
			throw new IllegalArgumentException("key must have length >= 1");

		Node node = get(root, key, 0);
		if (node != null)
			return node.value;
		else
			return null;
	}

	private Node get(Node node, String key, int index) {
		if (node == null) {
			return null;
		}
		char character = key.charAt(index);
		if (character < node.character)
			return get(node.left, key, index);
		else if (character > node.character)
			return get(node.right, key, index);
		else {
			if (index == key.length() - 1)
				return node;
			else
				return get(node.mid, key, index + 1);
		}

	}

	@Override
	public void delete(String key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		root = delete(root, key, 0);
	}

	private Node delete(Node node, String key, int index) {
		if (node == null) {
			return null;
		}

		if (index == key.length() - 1) {
			node.value = null;
		} else {
			char character = key.charAt(index);

			if (character < node.character)
				node.left = delete(node.left, key, index);
			else if (character > node.character)
				node.right = delete(node.right, key, index);
			else
				node.mid = delete(node.mid, key, index + 1);
		}
		if (root.value != null) {
			return node;
		} else {
			if (root.left != null || root.right != null || root.mid != null) {
				return root;
			}
		}
		return null;
	}

	@Override
	public boolean contains(String key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		return get(key) != null;
	}

	@Override
	public boolean isEmpty() {
		return root == null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterable<String> keys() {
		Queue<String> queue = new LinkedListQueue<String>();
		if (!isEmpty()) {
			collect(root, new StringBuilder(), queue);
		}
		return queue;
	}

	private void collect(Node node, StringBuilder sb, Queue<String> queue) {
		if (node == null)
			return;
		if (node.value != null) {
			queue.enqueue(sb.toString() + node.character);
		}
		collect(node.left, sb, queue);
		collect(node.mid, sb.append(node.character), queue);
		sb.deleteCharAt(sb.length() - 1);
		collect(node.right, sb, queue);

	}

	@Override
	public Iterable<String> keysWithPrefix(String prefix) {
		Queue<String> queue = new LinkedListQueue<String>();
		if (!isEmpty()) {
			Node temp = get(root, prefix, 0);
			collect(temp.mid, new StringBuilder(prefix), queue);
		}
		return queue;
	}

	@Override
	public String longestPrefixOf(String query) {
		if (!isEmpty()) {
			Node temp = root;
			int index = 0;
			int length = -1;

			while (temp != null && index < query.length()) {
				char character = query.charAt(index);
				if (character < temp.character) {
					temp = temp.left;
				} else if (character > temp.character) {
					temp = temp.right;
				} else {
					index++;
					if (temp.value != null) {
						length = index;
						break;
					}
					temp = temp.mid;
				}
			}
			if (length != -1) {
				return query.substring(0, length);
			}
		}
		return null;
	}

	@Override
	public Iterable<String> keysThatMatch(String pattern) {
		Queue<String> keys = new LinkedListQueue<String>();
		if (!isEmpty()) {
			collect(root, new StringBuilder(), pattern, keys);
		}
		return keys;
	}

	private void collect(Node node, StringBuilder sb, String pattern, Queue<String> keys) {
		if (node == null)
			return;
		int index = sb.length();
		if (index == (pattern.length() - 1) && node.value != null) {
			keys.enqueue(sb.toString() + node.character);
		}
		if (index == pattern.length())
			return;
		char character = pattern.charAt(index);
		if (character < node.character)
			collect(node.left, sb, pattern, keys);
		else if (character > node.character)
			collect(node.right, sb, pattern, keys);
		else
			collect(node.mid, sb.append(node.character), pattern, keys);
	}

	public static void main(String[] args) {
		TernarySearchTries<Integer> st = new TernarySearchTries<Integer>();
		String[] keys = { "another", "test", "double", "treble", "times", "happy", "grateful", "super", "excellent", "wonderful" };
		for (int i = 0; i < keys.length; i++) {
			st.put(keys[i], i);
		}

		System.out.println(st.get("another"));
		System.out.println(st.get("test"));
		System.out.println(st.get("double"));
		System.out.println(st.get("times"));
		System.out.println(st.get("happy"));
		System.out.println(st.get("wonderful"));

		for (String key : st.keysThatMatch("test")) {
			StdOut.println(key + " " + st.get(key));
		}

		System.out.println(st.longestPrefixOf("excellent"));
	}

}