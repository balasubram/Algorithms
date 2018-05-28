package com.bala.algs.week4;

import java.util.NoSuchElementException;

import com.bala.algs.week2.LinkedListQueue;
import com.bala.algs.week2.Queue;

public class BinarySearchTreeST<Key extends Comparable<Key>, Value> implements ST<Key, Value> {

	private final class Node {
		Key key;
		Value val;
		Node left;
		Node right;
		int size;

		Node(Key key, Value val) {
			this.key = key;
			this.val = val;
			this.size = 1;
		}

	}

	private Node root;

	private int size;

	public BinarySearchTreeST() {
		this.size = 0;
		this.root = null;
	}

	private int size(Node node) {
		if (node != null)
			return node.size;
		return 0;
	}

	private Node put(Node node, Key key, Value val) {
		if (node == null) {
			return new Node(key, val);
		} else {
			int cmp = node.key.compareTo(key);
			if (cmp < 0) {
				node.right = put(node.right, key, val);
			} else if (cmp > 0) {
				node.left = put(node.left, key, val);
			} else {
				node.val = val;
			}
		}
		node.size = size(node.left) + size(node.right);
		return node;
	}

	private Value get(Node node, Key key) {
		Value val = null;
		if (node != null) {
			int cmp = node.key.compareTo(key);
			if (cmp < 0) {
				return get(node.right, key);
			} else if (cmp > 0) {
				return get(node.left, key);
			} else {
				val = node.val;
			}
		}
		return val;
	}

	private Node delete(Node node, Key key) {
		if (node == null)
			return null;
		int cmp = node.key.compareTo(key);
		if (cmp < 0)
			node.left = delete(node.left, key);
		if (cmp > 0)
			node.right = delete(node.right, key);
		if (cmp == 0) {
			if (node.right == null)
				return node.left;
			if (node.left == null)
				return node.right;
			else {
				Node temp = node;
				node = min(node.right);
				node.right = deleteMin(temp.right);
				node.left = temp.left;
			}
		}
		node.size = size(node.left) + size(node.right) + 1;
		return node;
	}

	private Node deleteMin(Node node) {
		if (node.left == null)
			return node.right;
		node.left = deleteMin(node.left);
		node.size = size(node.left) + size(node.right) + 1;
		return node;
	}

	private Node deleteMax(Node node) {
		if (node.right == null)
			return node.left;
		node.left = deleteMax(node.right);
		node.size = size(node.left) + size(node.right) + 1;
		return node;
	}

	private boolean contains(Node node, Key key) {
		boolean result = false;
		if (node != null) {
			int cmp = node.key.compareTo(key);
			if (cmp < 0) {
				return contains(node.right, key);
			} else if (cmp > 0) {
				return contains(node.left, key);
			} else {
				result = true;
			}
		}
		return result;
	}

	private Node min(Node node) {
		if (node.left != null)
			return node;
		else
			return min(node.left);
	}

	private Node max(Node node) {
		if (node.right != null)
			return node;
		else
			return max(node.right);
	}

	private Node floor(Node node, Key key) {
		Node temp = null;
		if (node == null) {
			temp = null;
		} else {
			int cmp = node.key.compareTo(key);
			if (cmp == 0) {
				temp = node;
			} else if (cmp > 0) {
				temp = floor(node.left, key);
			} else if (cmp < 0) {
				temp = floor(node.right, key);
			}
		}
		if (temp == null)
			temp = node;
		return temp;
	}

	private Node ceiling(Node node, Key key) {
		Node temp = null;
		if (node == null) {
			temp = null;
		} else {
			int cmp = node.key.compareTo(key);
			if (cmp == 0) {
				temp = node;
			} else if (cmp > 0) {
				temp = ceiling(node.left, key);
			} else if (cmp < 0) {
				temp = ceiling(node.right, key);
			}
		}
		if (temp == null)
			temp = node;
		return temp;
	}

	private int rank(Node node, Key key) {
		if (node == null)
			return 0;
		else {
			int cmp = node.key.compareTo(key);
			if (cmp == 0)
				return size(node.left);
			if (cmp > 0)
				return rank(node.left, key);
			if (cmp < 0)
				return 1 + size(node.left) + rank(node.right, key);
		}
		return 0;
	}

	private boolean isBST() {
		return isBST(root, null, null);
	}

	private boolean isBST(Node node, Key min, Key max) {
		if (node == null)
			return true;
		if (min != null && node.key.compareTo(min) <= 0)
			return false;
		if (max != null && node.key.compareTo(max) >= 0)
			return false;
		return isBST(node.left, min, node.key) && isBST(node.right, node.key, max);
	}

	private boolean isSizeConsistent() {
		return isSizeConsistent(root);
	}

	private boolean isSizeConsistent(Node x) {
		if (x == null)
			return true;
		if (x.size == size(x.left) + size(x.right) + 1)
			return true;
		return isSizeConsistent(x.left) && isSizeConsistent(x.right);
	}

	@Override
	public void put(Key key, Value val) {
		if (key == null) {
			throw new IllegalArgumentException("Argument Key is null");
		}
		if (val == null) {
			delete(key);
		}
		root = put(root, key, val);
		assert (isSizeConsistent() == true);
		assert (isBST() == true);
		size++;
	}

	@Override
	public Value get(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument Key is null");
		}
		if (isEmpty()) {
			throw new IllegalArgumentException("The tree is empty");
		}
		return get(root, key);
	}

	@Override
	public void delete(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument Key is null");
		}
		if (isEmpty()) {
			throw new IllegalArgumentException("The tree is empty");
		}
		root = delete(root, key);
		assert (isSizeConsistent() == true);
		assert (isBST() == true);
	}

	@Override
	public boolean contains(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument Key is null");
		}
		if (isEmpty()) {
			throw new IllegalArgumentException("The tree is empty");
		}
		return contains(root, key);
	}

	private Node select(Node node, int k) {
		if (node != null) {
			int size = size(node.left);
			if (size > k) {
				return select(node.left, k);
			} else if (size > k) {
				return select(node.right, k - size - 1);
			} else {
				return node;
			}
		}
		return null;
	}

	private Iterable<Key> keys(Queue<Key> keys, Node node, Key lo, Key hi) {
		if (node == null)
			return keys;
		else {
			int cmpLo = node.key.compareTo(lo);
			int cmpHi = node.key.compareTo(hi);
			if (cmpLo < 0)
				return keys(keys, node.left, lo, hi);
			if (cmpLo >= 0 && cmpHi <= 0)
				keys.enqueue(node.key);
			if (cmpHi > 0)
				return keys(keys, node.right, lo, hi);
		}
		return keys;
	}

	@Override
	public boolean isEmpty() {
		return size() != 0;
	}

	@Override
	public int size() {
		return size(root);
	}

	@Override
	public Key min() {
		if (isEmpty()) {
			throw new IllegalArgumentException("The tree is empty");
		}
		return min(root).key;
	}

	@Override
	public Key max() {
		if (isEmpty()) {
			throw new IllegalArgumentException("The tree is empty");
		}
		return max(root).key;
	}

	@Override
	public Key floor(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument Key is null");
		}
		if (isEmpty()) {
			throw new NoSuchElementException("The tree is empty");
		}
		return floor(root, key).key;
	}

	@Override
	public Key ceiling(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument Key is null");
		}
		if (isEmpty()) {
			throw new NoSuchElementException("The tree is empty");
		}
		return ceiling(root, key).key;
	}

	@Override
	public int rank(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Argument Key is null");
		}
		if (isEmpty()) {
			throw new NoSuchElementException("The tree is empty");
		}
		return rank(root, key);
	}

	@Override
	public Key select(int k) {
		if (k > size() || k < 0) {
			throw new IllegalArgumentException("Illegal Index k");
		}
		if (isEmpty()) {
			throw new NoSuchElementException("The tree is empty");
		}
		return select(root, k).key;
	}

	@Override
	public void deleteMin() {
		if (isEmpty()) {
			throw new NoSuchElementException("The tree is empty");
		}
		root = deleteMin(root);

	}

	@Override
	public void deleteMax() {
		if (isEmpty()) {
			throw new NoSuchElementException("The tree is empty");
		}
		root = deleteMax(root);
	}

	@Override
	public int size(Key lo, Key hi) {
		if (lo == null || hi == null) {
			throw new IllegalArgumentException("Argument Key is null");
		}
		if (isEmpty()) {
			throw new NoSuchElementException("The tree is empty");
		}
		int rankLo = rank(lo);
		int rankHi = rank(hi);
		if (rankLo > rankHi) {
			return 0;
		} else {
			if (rankHi <= size())
				rankHi += 1;
			return rankHi - rankLo;
		}
	}

	@Override
	public Iterable<Key> keys(Key lo, Key hi) {
		if (lo == null || hi == null) {
			throw new IllegalArgumentException("Argument Key is null");
		}
		if (isEmpty()) {
			new LinkedListQueue<>();
		}
		Queue<Key> keys = new LinkedListQueue<>();
		return keys(keys, root, lo, hi);
	}

	@Override
	public Iterable<Key> keys() {
		if (isEmpty())
			return new LinkedListQueue<>();
		return keys(min(), max());
	}

	@Override
	public Iterable<Key> levelOrder() {
		Queue<Key> keys = new LinkedListQueue<>();
		Queue<Node> queue = new LinkedListQueue<>();
		queue.enqueue(root);
		while (!queue.isEmpty()) {
			Node temp = queue.dequeue();
			if (temp != null) {
				keys.enqueue(temp.key);
				queue.enqueue(temp.left);
				queue.enqueue(temp.left);
			}
		}
		return keys;
	}

}