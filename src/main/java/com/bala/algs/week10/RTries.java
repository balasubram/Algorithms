package com.bala.algs.week10;

import com.bala.algs.week2.LinkedListQueue;
import com.bala.algs.week2.Queue;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieST;

public class RTries<Value> implements TriesST<Value> {

	private static class Node {
		Object value;
		Node[] next = new Node[ARRAY_INDICES_COUNT];

		Node(Object val) {
			this.value = val;
		}

	}

	private static final int ARRAY_INDICES_COUNT = 256;

	private Node root;

	private int size;

	public RTries() {
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
		if (node == null) {
			node = new Node(null);
		}
		if (index == key.length()) {
			if (node.value == null) {
				size++;
			}
			node.value = val;
		} else {
			int charIndex = key.charAt(index);
			node.next[charIndex] = put(node.next[charIndex], key, val, index + 1);
		}
		return node;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Value get(String key) {
		if (key == null)
			throw new IllegalArgumentException("Argument is null");

		Node node = get(root, (String) key, 0);
		if (node == null)
			return null;
		return (Value) node.value;
	}

	private Node get(Node node, String key, int index) {
		if (node == null)
			return null;
		if (index == key.length())
			return node;
		int charIndex = key.charAt(index);
		return get(node.next[charIndex], key, index + 1);
	}

	@Override
	public void delete(String key) {
		if (key == null)
			throw new IllegalArgumentException("Argument is null");
		root = delete(root, (String) key, 0);
	}

	private Node delete(Node node, String key, int index) {
		if (node == null) {
			return null;
		}
		if (key.length() == index) {
			node.value = null;
			size--;
		} else {
			int charIndex = key.charAt(index);
			node.next[charIndex] = delete(node.next[charIndex], key, index++);
		}

		// remove subtrie rooted at x if it is completely empty
		if (node.value != null)
			return node;
		for (int c = 0; c < ARRAY_INDICES_COUNT; c++) {
			if (node.next[c] != null)
				return node;
		}
		return null;
	}

	@Override
	public boolean contains(String key) {
		if (key == null)
			throw new IllegalArgumentException("Argument is null");

		return get(root, (String) key, 0) != null;
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
		return keys(root);
	}

	private Iterable<String> keys(Node root) {
		Queue<String> keys = new LinkedListQueue<>();
		if (!isEmpty()) {
			StringBuilder sb = new StringBuilder();
			collect(root, sb, keys);
		}
		return keys;
	}

	private void collect(Node node, StringBuilder sb, Queue<String> keys) {
		if (node != null) {
			if (node.value != null) {
				keys.enqueue(sb.toString());
			} else {
				for (int index = 0; index < ARRAY_INDICES_COUNT; index++) {
					if (node.next[index] != null) {
						sb.append((char) index);
						collect(node.next[index], sb, keys);
						sb.deleteCharAt(sb.length() - 1);
					}
				}
			}
		}
	}

	@Override
	public Iterable<String> keysWithPrefix(String prefix) {
		Queue<String> keys = new LinkedListQueue<>();
		if (!isEmpty()) {
			Node temp = get(root, prefix, 0);
			StringBuilder sb = new StringBuilder(prefix);
			collect(temp, sb, keys);
		}
		return keys;
	}

	@Override
	public String longestPrefixOf(String query) {
		if (!isEmpty()) {
			int length = longestPrefixOf(root, query, 0, -1);
			if (length != -1) {
				return query.substring(0, length);
			}
		}
		return null;
	}

	private int longestPrefixOf(Node node, String query, int index, int length) {
		if (node == null)
			return length;
		if (node.value != null)
			length = index;
		if (index == query.length())
			return length;
		return longestPrefixOf(node.next[query.charAt(index)], query, index + 1, length);
	}

	@Override
	public Iterable<String> keysThatMatch(String pattern) {
		Queue<String> keys = new LinkedListQueue<>();
		if (!isEmpty()) {
			collect(root, new StringBuilder(), pattern, keys);
		}

		return keys;
	}

	private void collect(Node node, StringBuilder sb, String pattern, Queue<String> keys) {
		if (node == null)
			return;
		int index = sb.length();
		if (index == pattern.length() && node.value != null) {
			keys.enqueue(sb.toString());
		}
		if (index == pattern.length())
			return;
		int next = pattern.charAt(index);
		sb.append((char) next);
		collect(node.next[next], sb, pattern, keys);
		sb.deleteCharAt(sb.length() - 1);
	}

	public static void main(String[] args) {
		TrieST<Integer> st = new TrieST<Integer>();
		String[] keys = { "another", "test", "double", "treble", "times", "happy", "grateful", "super", "excellent", "wonderful" };
		for (int i = 0; i < keys.length; i++) {
			st.put(keys[i], i);
		}

		for (String key : st.keysThatMatch("t")) {
			StdOut.println(key + " " + st.get(key));
		}

		System.out.println(st.longestPrefixOf("test"));
	}

}