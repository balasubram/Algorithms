package com.bala.algs.week8;

public class TopologicalSort {

	private Iterable<Integer> order;

	private int[] rank;

	public TopologicalSort(EdgeWeightedDirectedGraph graph) {
		this.rank = new int[graph.noOfVertices()];
		DepthFirstOrder dfsOrder = new DepthFirstOrder(graph);
		this.order = dfsOrder.reversePost();
		int i = 0;
		for (int v : order)
			rank[v] = i++;
	}

	private void validateVertex(int v) {
		if (v < 0 || v >= rank.length)
			throw new IllegalArgumentException("vertex is out of range");
	}

	public boolean hasOrder() {
		return order != null;
	}

	public Iterable<Integer> order() {
		return order;
	}

	public int rank(int v) {
		validateVertex(v);
		if (hasOrder())
			return rank[v];
		else
			return -1;
	}

}