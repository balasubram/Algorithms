package com.bala.algs.week8;

import com.bala.algs.week2.LinkedListStack;
import com.bala.algs.week2.Stack;

public class AcyclicShortestPath implements SingleSourceShortestPath {

	private double[] distanceTo;

	private Edge[] edgeTo;

	public AcyclicShortestPath(EdgeWeightedDirectedGraph graph, int vertex) {
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

		TopologicalSort topologicalSort = new TopologicalSort(graph);
		if (!topologicalSort.hasOrder()) {
			throw new IllegalArgumentException("Graph is not acyclic");
		}

		for (int v : topologicalSort.order()) {
			relax(graph, v);
		}
	}

	private void validateVertex(int v) {
		if (v < 0 || v >= distanceTo.length) {
			throw new IllegalArgumentException("Vertex is out of range");
		}
	}

	private void relax(EdgeWeightedDirectedGraph graph, int v) {
		for (Edge edge : graph.adj(v)) {
			int w = edge.other(v);
			if (distanceTo[w] > distTo(v) + edge.getWeight()) {
				distanceTo[w] = distTo(v) + edge.getWeight();
				edgeTo[w] = edge;
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
		if (!hasPathTo(v))
			return edges;
		for (Edge edge = edgeTo[v]; edge != null; edge = edgeTo[edge.either()]) {
			edges.push(edge);
		}
		return edges;
	}

}