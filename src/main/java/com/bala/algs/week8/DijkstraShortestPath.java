package com.bala.algs.week8;

import com.bala.algs.week2.LinkedListStack;
import com.bala.algs.week2.Stack;

import edu.princeton.cs.algs4.IndexMinPQ;

public class DijkstraShortestPath implements SingleSourceShortestPath {

	private double[] distanceTo;

	private Edge[] edgeTo;

	public DijkstraShortestPath(EdgeWeightedDirectedGraph graph, int vertex) {
		for (Edge edge : graph.edges()) {
			if (edge.getWeight() < 0) {
				throw new IllegalArgumentException("Edge weights are negative");
			}
		}

		validateVertex(vertex);

		distanceTo = new double[graph.noOfVertices()];
		edgeTo = new Edge[graph.noOfVertices()];

		for (int v = 0; v < graph.noOfVertices(); v++) {
			distanceTo[v] = Double.POSITIVE_INFINITY;
		}

		distanceTo[vertex] = 0.0;

		IndexMinPQ<Double> indexMinPQ = new IndexMinPQ<>(graph.noOfVertices());
		indexMinPQ.insert(vertex, distanceTo[vertex]);
		while (!indexMinPQ.isEmpty()) {
			int s = indexMinPQ.delMin();
			relax(graph, indexMinPQ, s);
		}
	}

	private void validateVertex(int v) {
		if (v < 0 || v >= distanceTo.length) {
			throw new IllegalArgumentException("Vertex is out of range");
		}
	}

	private void relax(EdgeWeightedDirectedGraph graph, IndexMinPQ<Double> indexMinPQ, int v) {
		for (Edge edge : graph.adj(v)) {
			int w = edge.other(v);
			if (distanceTo[w] > distTo(v) + edge.getWeight()) {
				distanceTo[w] = distTo(v) + edge.getWeight();
				edgeTo[w] = edge;
				if (indexMinPQ.contains(w))
					indexMinPQ.changeKey(w, distanceTo[w]);
				else
					indexMinPQ.insert(w, distanceTo[w]);
			}
		}
	}

	@Override
	public double distTo(int v) {
		validateVertex(v);
		return distanceTo[v];
	}

	@Override
	public boolean hasPathTo(int v) {
		validateVertex(v);
		return edgeTo[v] != null;
	}

	@Override
	public Iterable<Edge> pathTo(int v) {
		validateVertex(v);
		Stack<Edge> edges = new LinkedListStack<>();
		if (hasPathTo(v))
			return edges;
		for (Edge edge = edgeTo[v]; edge != null; edge = edgeTo[edge.either()]) {
			edges.push(edge);
		}
		return null;
	}

}