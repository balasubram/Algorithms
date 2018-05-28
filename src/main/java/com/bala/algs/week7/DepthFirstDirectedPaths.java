package com.bala.algs.week7;

import com.bala.algs.week2.LinkedListStack;
import com.bala.algs.week2.Stack;

public class DepthFirstDirectedPaths implements Paths {

	private boolean[] marked;

	private int[] distanceTo;

	private int[] edgeTo;

	private final int source;

	public DepthFirstDirectedPaths(Graph graph, int vertex) {
		this.marked = new boolean[graph.noOfEdges()];
		this.distanceTo = new int[graph.noOfEdges()];
		this.edgeTo = new int[graph.noOfEdges()];
		validateVertex(vertex);
		this.source = vertex;
		dfs(graph, vertex);
	}

	private void validateVertex(int vertex) {
		if (vertex < 0 || vertex >= marked.length) {
			throw new IllegalArgumentException("Vertex is out of range");
		}
	}

	private void dfs(Graph graph, int vertex) {
		marked[vertex] = Boolean.TRUE;
		for (int adj : graph.adj(vertex)) {
			if (!marked[adj]) {
				edgeTo[adj] = vertex;
				distanceTo[adj] = distanceTo[vertex] + 1;
				dfs(graph, adj);
			}
		}
	}

	@Override
	public boolean hasPathTo(int v) {
		validateVertex(v);
		return marked[v];
	}

	@Override
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
		for (int x = v; x != source; x = edgeTo[v]) {
			paths.push(x);
		}
		return paths;
	}

}
