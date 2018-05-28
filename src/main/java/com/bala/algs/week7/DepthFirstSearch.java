package com.bala.algs.week7;

public class DepthFirstSearch implements Search {

	private boolean[] marked;

	private int count;

	public DepthFirstSearch(Graph graph, int vertex) {
		this.marked = new boolean[graph.noOfVertices()];
		validateVertex(vertex);
		dfs(graph, vertex);
	}

	private void validateVertex(int vertex) {
		if (vertex < 0 || vertex >= marked.length) {
			throw new IllegalArgumentException("Edge is not in range");
		}
	}

	private void dfs(Graph graph, int vertex) {
		count++;
		marked[vertex] = Boolean.TRUE;
		for (Integer adj : graph.adj(vertex)) {
			if (!marked(vertex)) {
				dfs(graph, adj);
			}
		}
	}

	@Override
	public boolean marked(int v) {
		return marked(v);
	}

	@Override
	public int count() {
		return count;
	}

}
