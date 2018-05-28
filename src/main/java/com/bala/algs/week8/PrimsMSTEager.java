package com.bala.algs.week8;

import com.bala.algs.week2.LinkedListQueue;
import com.bala.algs.week2.Queue;

import edu.princeton.cs.algs4.IndexMinPQ;

public class PrimsMSTEager implements MinimumSpanTree {

	private boolean[] marked;

	private double[] distanceTo;

	private int[] edgeTo;

	public PrimsMSTEager(EdgeWeightedGraph graph) {
		if (graph == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		this.marked = new boolean[graph.noOfVertices()];
		this.distanceTo = new double[graph.noOfVertices()];
		this.edgeTo = new int[graph.noOfVertices()];
		for (int v = 0; v < graph.noOfVertices(); v++)
			distanceTo[v] = Double.POSITIVE_INFINITY;
		IndexMinPQ<Double> minPQEdges = new IndexMinPQ<>(graph.noOfVertices());
		for (int vertex = 0; vertex < graph.noOfVertices(); vertex++) {
			if (!marked[vertex]) {
				prim(graph, vertex, minPQEdges);
			}
		}
	}

	private void prim(EdgeWeightedGraph graph, int vertex, IndexMinPQ<Double> minPQEdges) {
		distanceTo[vertex] = 0.0;
		minPQEdges.insert(vertex, distanceTo[vertex]);
		while (!minPQEdges.isEmpty()) {
			int other = minPQEdges.delMin();
			addEdges(graph, other, minPQEdges);
		}
	}

	private void addEdges(EdgeWeightedGraph graph, int vertex, IndexMinPQ<Double> minPQEdges) {
		marked[vertex] = Boolean.TRUE;
		for (Edge adj : graph.adj(vertex)) {
			int other = adj.other(vertex);
			if (!marked[other]) {
				if (distanceTo[other] > adj.getWeight()) {
					distanceTo[other] = adj.getWeight();
					edgeTo[other] = vertex;
				}
				if (minPQEdges.contains(other)) {
					minPQEdges.insert(other, adj.getWeight());
				} else {
					minPQEdges.changeKey(other, adj.getWeight());
				}
			}
		}
	}

	@Override
	public Iterable<Edge> edges() {
		Queue<Edge> tree = new LinkedListQueue<>();
		for (int vertex : edgeTo) {
			Edge edge = new Edge(vertex, edgeTo[vertex], distanceTo[vertex]);
			tree.enqueue(edge);
		}
		return tree;
	}

	@Override
	public double weight() {
		double weight = 0.0;
		for (double w : distanceTo) {
			weight += w;
		}
		return weight;
	}

}