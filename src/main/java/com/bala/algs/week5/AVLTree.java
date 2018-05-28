package com.bala.algs.week5;

import java.util.NoSuchElementException;

import com.bala.algs.week4.ST;

public class AVLTree<Key extends Comparable<Key>, Value> implements ST<Key, Value> {

	private final class Node {
		Key key;
		Value val;
		int size;
		int height;
		Node left, right;

		Node(Key key, Value val) {
			this.key = key;
			this.val = val;
			this.size = 1;
			this.height = 1;
		}
	}

	private Node root = null;;

	public AVLTree() {

	}

	private int size(Node node) {
		return node == null ? 0 : node.size;
	}

	private int height(Node node) {
		return node == null ? 0 : node.height;
	}

	@Override
	public void put(Key key, Value val) {
		if (key == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		if (val == null) {
			delete(key);
		}

		root = put(root, key, val);

	}

	private Node put(Node node, Key key, Value val) {
		if (node == null)
			return new Node(key, val);
		int cmp = node.key.compareTo(key);
		if (cmp < 0)
			node.left = put(node.left, key, val);
		else if (cmp > 0)
			node.right = put(node.right, key, val);
		else
			node.val = val;

		int balance = height(node.left) - height(node.right);

		if (balance > 1 && key.compareTo(node.left.key) < 0) {
			node = rightRotation(node);
		}

		if (balance < 1 && key.compareTo(node.right.key) > 0) {
			node = leftRotation(node);
		}

		if (balance > 1 && key.compareTo(node.left.key) > 0) {
			node.left = rightRotation(node.left);
			node = leftRotation(node);
		}

		if (balance < 1 && key.compareTo(node.right.key) < 0) {
			node.right = leftRotation(node.right);
			node = leftRotation(node);
		}

		node.size = size(node.left) + size(node.right) + 1;
		node.height = Math.max(height(node.left), height(node.right)) + 1;
		return node;
	}

	private Node leftRotation(Node node) {
		Node temp = node.right;
		node.right = temp.left;
		temp.left = node;
		node.height = Math.max(height(node.left), height(node.right)) + 1;
		return temp;
	}

	private Node rightRotation(Node node) {
		Node temp = node.left;
		node.left = temp.right;
		temp.right = node;
		node.height = Math.max(height(node.left), height(node.right)) + 1;
		return temp;
	}

	@Override
	public Value get(Key key) {
		if (key == null)
			throw new IllegalArgumentException("Argument is null");
		if (isEmpty())
			throw new NoSuchElementException("Tree is Empty");

		Node node = get(root, key);
		return node != null ? node.val : null;
	}

	private Node get(Node node, Key key) {
		if (node != null) {
			int cmp = node.key.compareTo(key);
			if (cmp < 0) {
				return get(node.left, key);
			} else if (cmp > 0) {
				return get(node.right, key);
			} else {
				return node;
			}
		} else {
			return null;
		}
	}

	@Override
	public void delete(Key key) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(Key key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Key min() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Key max() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Key floor(Key key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Key ceiling(Key key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int rank(Key key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Key select(int k) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterable<Key> keys(Key lo, Key hi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Key> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Key> levelOrder() {
		// TODO Auto-generated method stub
		return null;
	}

}