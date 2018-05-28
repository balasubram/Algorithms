package com.bala.algs.week7;

import com.bala.algs.week2.LinkedListQueue;
import com.bala.algs.week2.LinkedListStack;
import com.bala.algs.week2.Queue;
import com.bala.algs.week2.Stack;

public class BreadthFirstDirectedPaths implements Paths {

	private boolean[] marked;

	private int[] distanceTo;

	private int[] edgeTo;

	private final int source;

	public BreadthFirstDirectedPaths(Graph graph, int vertex) {
		this.marked = new boolean[graph.noOfVertices()];
		this.distanceTo = new int[graph.noOfVertices()];
		this.edgeTo = new int[graph.noOfVertices()];
		validateVertex(vertex);
		this.source = vertex;
		bfs(graph, vertex);
	}

	// public BreadthFirstDirectedPaths(Graph graph, Iterable<Integer> vertices) {
	// if (vertices == null) {
	// throw new IllegalArgumentException("Argument is null");
	// }
	// this.marked = new boolean[graph.noOfVertices()];
	// this.distanceTo = new int[graph.noOfVertices()];
	// this.edgeTo = new int[graph.noOfVertices()];
	// for (Integer vertex : vertices) {
	// validateVertex(vertex);
	// }
	// bfs(graph, vertices);
	// }

	private void validateVertex(int vertex) {
		if (vertex < 0 || vertex >= marked.length) {
			throw new IllegalArgumentException("Vertex is out of range");
		}
	}

	private void bfs(Graph graph, int vertex) {
		edgeTo[vertex] = vertex;
		distanceTo[vertex] = 0;
		Queue<Integer> queue = new LinkedListQueue<Integer>();
		queue.enqueue(vertex);
		while (!queue.isEmpty()) {
			int newVertex = queue.dequeue();
			for (int adj : graph.adj(newVertex)) {
				if (!marked[adj]) {
					marked[adj] = Boolean.TRUE;
					distanceTo[adj] = distanceTo[newVertex] + 1;
					edgeTo[adj] = newVertex;
					queue.enqueue(adj);
				}
			}
		}
	}

	// private void bfs(Graph graph, Iterable<Integer> vertices) {
	// Queue<Integer> queue = new LinkedListQueue<Integer>();
	// for (Integer vertex : vertices) {
	// edgeTo[vertex] = vertex;
	// distanceTo[vertex] = 0;
	// queue.enqueue(vertex);
	// }
	// while (!queue.isEmpty()) {
	// int newVertex = queue.dequeue();
	// for (int adj : graph.adj(newVertex)) {
	// if (!marked[adj]) {
	// marked[adj] = Boolean.TRUE;
	// distanceTo[adj] = distanceTo[newVertex] + 1;
	// edgeTo[adj] = newVertex;
	// queue.enqueue(adj);
	// }
	// }
	// }
	// }

	@Override
	public boolean hasPathTo(int v) {
		validateVertex(v);
		return marked[v];
	}

	public int distTo(int v) {
		validateVertex(v);
		return distanceTo[v];
	}

	@Override
	public Iterable<Integer> pathTo(int v) {
		validateVertex(v);
		if (!hasPathTo(v))
			return null;
		Stack<Integer> paths = new LinkedListStack<Integer>();
		for (int x = v; x != source; v = edgeTo[x]) {
			paths.push(x);
		}
		return paths;
	}

}