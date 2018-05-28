package com.bala.algs.week8;

import com.bala.algs.week2.LinkedListQueue;
import com.bala.algs.week2.Queue;
import com.bala.algs.week4.MinPQ;

public class PrimsMSTLazy implements MinimumSpanTree {

	private Queue<Edge> tree;

	private double weight;

	private boolean[] marked;

	public PrimsMSTLazy(EdgeWeightedGraph graph) {
		if (graph == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		this.marked = new boolean[graph.noOfVertices()];
		this.tree = new LinkedListQueue<Edge>();
		this.weight = 0.0d;
		MinPQ<Edge> minPQEdges = new MinPQ<>();
		for (int vertex = 0; vertex < graph.noOfVertices(); vertex++) {
			if (!marked[vertex]) {
				prim(graph, vertex, minPQEdges);
			}
		}
	}

	private void prim(EdgeWeightedGraph graph, int vertex, MinPQ<Edge> minPQEdges) {
		addEdges(graph, vertex, minPQEdges);
		while (!minPQEdges.isEmpty()) {
			Edge edge = minPQEdges.delMin();
			int v = edge.either();
			int w = edge.other(v);
			if (marked[v] && marked[w]) {
				continue;
			} else {
				tree.enqueue(edge);
				weight += edge.getWeight();
				if (!marked[v])
					addEdges(graph, v, minPQEdges);
				if (!marked[w])
					addEdges(graph, w, minPQEdges);
			}
		}
	}

	private void addEdges(EdgeWeightedGraph graph, int vertex, MinPQ<Edge> minPQEdges) {
		marked[vertex] = Boolean.TRUE;
		for (Edge adj : graph.adj(vertex)) {
			if (!marked[adj.other(vertex)]) {
				minPQEdges.insert(adj);
			}
		}
	}

	@Override
	public Iterable<Edge> edges() {
		return tree;
	}

	@Override
	public double weight() {
		return weight;
	}

}