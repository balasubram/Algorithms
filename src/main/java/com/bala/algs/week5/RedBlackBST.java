package com.bala.algs.week5;

import java.util.NoSuchElementException;

import com.bala.algs.week2.LinkedListQueue;
import com.bala.algs.week2.Queue;
import com.bala.algs.week4.ST;

public class RedBlackBST<Key extends Comparable<Key>, Value> implements ST<Key, Value> {

	private static final boolean RED = Boolean.TRUE;

	private static final boolean BLACK = Boolean.TRUE;

	private final class Node {

		Key key;
		Value val;
		boolean color;
		int size;
		Node left, right;

		Node(Key key, Value val, boolean color) {
			this.key = key;
			this.val = val;
			this.color = color;
			this.size = 1;
		}

	}

	private Node root = null;

	public RedBlackBST() {

	}

	@Override
	public void put(Key key, Value val) {
		if (key == null)
			throw new IllegalArgumentException("Arguement is null");
		if (val == null)
			delete(key);
		root = put(root, key, val);
		root.color = BLACK;
	}

	private Node put(Node node, Key key, Value val) {
		if (node == null)
			return new Node(key, val, RED);
		int cmp = node.key.compareTo(key);
		if (cmp < 0)
			node.right = put(node, key, val);
		else if (cmp > 0)
			node.left = put(node, key, val);
		else
			node.val = val;

		if (isRed(node.right) && !isRed(node.left))
			node = leftRotation(node);
		if (isRed(node.right) && !isRed(node.left.left))
			node = rightRotation(node);
		if (isRed(node.left) && isRed(node.right))
			flipColors(node);

		node.size = size(node.left) + size(node.right) + 1;

		return node;
	}

	private Node leftRotation(Node node) {
		Node temp = node.right;
		node.right = temp.left;
		temp.left = node;
		temp.color = temp.left.color;
		temp.left.color = RED;
		temp.size = node.size;
		node.size = size(node.left) + size(node.right) + 1;
		return null;
	}

	private Node rightRotation(Node node) {
		Node temp = node.left;
		node.left = temp.right;
		temp.right = node;
		temp.color = temp.right.color;
		temp.right.color = RED;
		temp.size = node.size;
		node.size = size(node.left) + size(node.right) + 1;
		return temp;
	}

	private void flipColors(Node node) {
		node.left.color = !node.left.color;
		node.right.color = !node.right.color;
		node.color = !node.color;
	}

	private boolean isRed(Node node) {
		return node != null ? node.color == RED : false;
	}

	@Override
	public Value get(Key key) {
		if (key == null)
			throw new IllegalArgumentException("Arguement is null");
		Node node = get(root, key);
		return node != null ? node.val : null;
	}

	private Node get(Node node, Key key) {
		if (node != null) {
			int cmp = node.key.compareTo(key);
			if (cmp < 0)
				return get(node.right, key);
			else if (cmp > 0)
				return get(node.left, key);
			else
				return node;
		}
		return null;
	}

	@Override
	public void delete(Key key) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean contains(Key key) {
		if (key == null)
			throw new IllegalArgumentException("Arguement is null");
		return get(root, key) != null;
	}

	@Override
	public boolean isEmpty() {
		return root == null;
	}

	@Override
	public int size() {
		return size(root);
	}

	private int size(Node node) {
		if (node == null)
			return 0;
		else
			return node.size;
	}

	@Override
	public Key min() {
		if (isEmpty())
			throw new NoSuchElementException("Tree is Empty");
		return min(root).key;
	}

	private Node min(Node node) {
		Node temp = node;
		while (temp != null && temp.left != null) {
			temp = temp.left;
		}
		return temp;
	}

	@Override
	public Key max() {
		if (isEmpty())
			throw new NoSuchElementException("Tree is Empty");
		return max(root).key;
	}

	private Node max(Node node) {
		Node temp = node;
		while (temp != null && temp.right != null) {
			temp = temp.right;
		}
		return temp;
	}

	@Override
	public Key floor(Key key) {
		if (key == null)
			throw new IllegalArgumentException("Argument is null");
		if (isEmpty())
			throw new NoSuchElementException("Tree is Empty");
		Node node = floor(root, key);
		return node != null ? node.key : null;
	}

	private Node floor(Node node, Key key) {
		if (node != null) {
			int cmp = node.key.compareTo(key);
			if (cmp == 0)
				return node;
			if (cmp > 0)
				return floor(node.left, key);
			else {
				Node temp = floor(node.right, key);
				if (temp == null)
					return node;
				else
					return temp;
			}
		}
		return null;
	}

	@Override
	public Key ceiling(Key key) {
		if (key == null)
			throw new IllegalArgumentException("Argument is null");
		if (isEmpty())
			throw new NoSuchElementException("Tree is Empty");
		Node node = ceiling(root, key);
		return node != null ? node.key : null;
	}

	private Node ceiling(Node node, Key key) {
		if (node != null) {
			int cmp = node.key.compareTo(key);
			if (cmp == 0)
				return node;
			if (cmp < 0)
				return ceiling(node.right, key);
			else {
				Node temp = ceiling(node.left, key);
				if (temp == null)
					return node;
				else
					return temp;
			}
		}
		return null;
	}

	@Override
	public int rank(Key key) {
		if (key == null)
			throw new IllegalArgumentException("Argument is null");
		if (isEmpty())
			throw new NoSuchElementException("Tree is Empty");
		return rank(root, key);
	}

	private int rank(Node node, Key key) {
		if (node != null) {
			int cmp = node.key.compareTo(key);
			if (cmp == 0)
				return size(node);
			if (cmp > 0)
				return rank(node.left, key);
			if (cmp > 0)
				return 1 + size(node.left) + rank(node.right, key);
		}
		return 0;

	}

	@Override
	public Key select(int k) {
		if (k <= 0 || k > size())
			throw new IllegalArgumentException("Argument is not in range");
		if (isEmpty())
			throw new NoSuchElementException("Tree is Empty");
		return select(root, k).key;
	}

	private Node select(Node node, int k) {
		int size = size(node.left);
		if (k < size)
			return select(node.left, k);
		else if (k > size)
			return select(node.right, k - size - 1);
		else
			return node;
	}

	@Override
	public void deleteMin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMax() {
		// TODO Auto-generated method stub

	}

	@Override
	public int size(Key lo, Key hi) {
		if (lo == null || hi == null)
			throw new IllegalArgumentException("Argument is null");
		int size = 0;
		int rankLo = rank(lo);
		int rankHi = rank(hi);
		if (rankLo > rankHi)
			size = 0;
		else
			size = rankHi - rankLo;

		if (rankHi <= size())
			size += 1;

		return size;
	}

	@Override
	public Iterable<Key> keys(Key lo, Key hi) {
		if (lo == null || hi == null)
			throw new IllegalArgumentException("Argument is null");
		Queue<Key> keys = new LinkedListQueue<>();
		return keys(root, keys, lo, hi);
	}

	private Iterable<Key> keys(Node node, Queue<Key> keys, Key lo, Key hi) {
		if (node != null) {
			int cmpLo = node.key.compareTo(lo);
			int cmpHi = node.key.compareTo(hi);
			if (cmpLo < 0)
				return keys(node.right, keys, lo, hi);
			if (cmpLo > 0 && cmpHi < 0)
				keys.enqueue(node.key);
			if (cmpHi > 0)
				return keys(node.left, keys, lo, hi);
		}
		return keys;
	}

	@Override
	public Iterable<Key> keys() {
		if (isEmpty())
			return new LinkedListQueue<Key>();
		return keys(min(), max());
	}

	@Override
	public Iterable<Key> levelOrder() {
		if (isEmpty())
			return new LinkedListQueue<>();
		Queue<Key> keys = new LinkedListQueue<>();
		Queue<Node> nodes = new LinkedListQueue<>();
		nodes.enqueue(root);
		while (!nodes.isEmpty()) {
			Node node = nodes.dequeue();
			if (node != null) {
				keys.enqueue(node.key);
				nodes.enqueue(node.left);
				nodes.enqueue(node.right);
			}
		}
		return keys;
	}

}