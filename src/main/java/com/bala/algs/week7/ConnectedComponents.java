package com.bala.algs.week7;

public class ConnectedComponents {

	private boolean[] marked;

	private int[] components;

	private int componentsCount;

	private int[] componentSize;

	public ConnectedComponents(Graph graph) {
		this.marked = new boolean[graph.noOfVertices()];
		this.components = new int[graph.noOfVertices()];
		this.componentSize = new int[graph.noOfVertices()];
		this.componentsCount = 0;
		connectedComponents(graph);

	}

	private void validateVertex(int vertex) {
		if (vertex < 0 || vertex >= marked.length) {
			throw new IllegalArgumentException("Vertex is out of range");
		}
	}

	private void connectedComponents(Graph graph) {
		for (int vertex = 0; vertex < graph.noOfVertices(); vertex++) {
			if (!marked[vertex]) {
				dfs(graph, vertex);
				componentsCount++;
			}
		}
	}

	private void dfs(Graph graph, int vertex) {
		for (int adj : graph.adj(vertex)) {
			if (!marked[adj]) {
				marked[adj] = Boolean.TRUE;
				components[adj] = componentsCount;
				componentSize[componentsCount]++;
				dfs(graph, adj);
			}
		}
	}

	public boolean connected(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		return components[v] == components[w];
	}

	public int count() {
		return componentsCount;
	}

	public int id(int v) {
		validateVertex(v);
		return components[v];
	}

	public int size(int v) {
		validateVertex(v);
		return componentSize[components[v]];
	}

}