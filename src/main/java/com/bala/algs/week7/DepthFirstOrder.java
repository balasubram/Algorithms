package com.bala.algs.week7;

import com.bala.algs.week2.LinkedListQueue;
import com.bala.algs.week2.Queue;
import com.bala.algs.week2.ResizingArrayStack;
import com.bala.algs.week2.Stack;

public class DepthFirstOrder {

	private boolean[] marked;

	private Queue<Integer> preOrder;

	private Queue<Integer> postOrder;

	private int[] preOrderIndex;

	private int[] postOrderIndex;

	private int preOrderCounter;

	private int postOrderCounter;

	public DepthFirstOrder(DirectedGraph graph) {
		this.marked = new boolean[graph.noOfVertices()];
		this.preOrderIndex = new int[graph.noOfVertices()];
		this.postOrderIndex = new int[graph.noOfVertices()];
		this.postOrder = new LinkedListQueue<>();
		this.preOrder = new LinkedListQueue<>();
		this.preOrderCounter = 0;
		this.preOrderCounter = 0;
		for (int vertex = 0; vertex < graph.noOfVertices(); vertex++) {
			if (!marked[vertex]) {
				dfs(graph, vertex);
			}
		}

		assert check();
	}

	private void dfs(DirectedGraph graph, int vertex) {
		marked[vertex] = Boolean.TRUE;
		preOrder.enqueue(vertex);
		preOrderIndex[vertex] = preOrderCounter++;
		for (int adj : graph.adj(vertex)) {
			if (!marked[adj]) {
				dfs(graph, adj);
			}
		}
		postOrder.enqueue(vertex);
		postOrderIndex[vertex] = postOrderCounter++;
	}

	private void validateVertex(int vertex) {
		if (vertex < 0 || vertex >= marked.length) {
			throw new IllegalArgumentException("Vertex is out of range");
		}
	}

	private boolean check() {

		int r = 0;
		for (int w : preOrder) {
			if (preOrderIndex[w] != r) {
				return false;
			}
			r++;
		}

		r = 0;
		for (int w : postOrder) {
			if (postOrderIndex[w] != r) {
				return false;
			}
			r++;
		}

		return true;
	}

	public int pre(int v) {
		validateVertex(v);
		return preOrderIndex[v];
	}

	public int post(int v) {
		validateVertex(v);
		return postOrderIndex[v];
	}

	public Iterable<Integer> pre() {
		return preOrder;
	}

	public Iterable<Integer> post() {
		return postOrder;
	}

	public Iterable<Integer> reversePost() {
		Stack<Integer> reverse = new ResizingArrayStack<>(postOrder.size());
		for (int order : postOrder) {
			reverse.push(order);
		}
		return reverse;
	}

}