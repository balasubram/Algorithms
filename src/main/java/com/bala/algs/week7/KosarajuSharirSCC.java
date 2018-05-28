package com.bala.algs.week7;

public class KosarajuSharirSCC {

	private boolean[] marked;

	private int[] connectedIndexer;

	private int components;

	KosarajuSharirSCC(DirectedGraph graph) {
		this.marked = new boolean[graph.noOfVertices()];
		this.connectedIndexer = new int[graph.noOfVertices()];
		this.components = 0;
		DepthFirstOrder order = new DepthFirstOrder(graph.reverse());
		connectedComponents(order, graph);
	}

	private void connectedComponents(DepthFirstOrder order, DirectedGraph graph) {
		for (int vertex : order.reversePost()) {
			if (!marked[vertex]) {
				dfs(graph, vertex);
				components++;
			}
		}
	}

	private void dfs(DirectedGraph graph, int vertex) {
		marked[vertex] = Boolean.TRUE;
		for (int adj : graph.adj(vertex)) {
			if (!marked[adj]) {
				connectedIndexer[adj] = components;
				dfs(graph, adj);
			}
		}

	}

	private void validateVertex(int vertex) {
		if (vertex < 0 || vertex >= marked.length) {
			throw new IllegalArgumentException("Vertex is out of range");
		}
	}

	public boolean stronglyConnected(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		return connectedIndexer[v] == connectedIndexer[w];
	}

	public int count() {
		return components;
	}

	public int id(int v) {
		validateVertex(v);
		return connectedIndexer[v];
	}
}