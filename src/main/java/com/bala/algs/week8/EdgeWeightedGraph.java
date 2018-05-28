package com.bala.algs.week8;

import com.bala.algs.week2.Bag;
import com.bala.algs.week2.LinkedListBag;

@SuppressWarnings("unchecked")
public class EdgeWeightedGraph implements EdgeGraph {

	private int noOfVertex;

	private Bag<Edge>[] bags;

	private int noOfEdges;

	public EdgeWeightedGraph(int vertex) {
		bags = (Bag<Edge>[]) new Bag[vertex];
		for (int i = 0; i < vertex; i++) {
			bags[i] = new LinkedListBag<Edge>();
		}
		this.noOfVertex = vertex;
	}

	private void validateVertex(int vertex) {
		if (vertex < 0 || vertex >= noOfVertex) {
			throw new IllegalArgumentException("Edge is not in range");
		}
	}

	@Override
	public int noOfVertices() {
		return noOfVertex;
	}

	@Override
	public int noOfEdges() {
		// TODO Auto-generated method stub
		return noOfEdges;
	}

	@Override
	public int degree(int v) {
		validateVertex(v);
		return bags[v].size();
	}

	@Override
	public int inDegree(int v) {
		throw new UnsupportedOperationException("Operation not supported");
	}

	@Override
	public int outDegree(int v) {
		throw new UnsupportedOperationException("Operation not supported");
	}

	@Override
	public EdgeGraph reverse() {
		throw new UnsupportedOperationException("Operation not supported");
	}

	@Override
	public void addEdge(Edge edge) {
		if (edge == null) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		int index = edge.either();
		int otherIndex = edge.other(index);
		validateVertex(index);
		validateVertex(otherIndex);
		bags[index].add(edge);
		bags[otherIndex].add(edge);
		noOfEdges++;
	}

	@Override
	public Iterable<Edge> adj(int v) {
		validateVertex(v);
		return bags[v];
	}

	@Override
	public Iterable<Edge> edges() {
		Bag<Edge> edges = new LinkedListBag<>();
		for (int vertex = 0; vertex < noOfVertex; vertex++) {
			for (Edge edge : bags[vertex]) {
				boolean added = false;
				if (edge.other(vertex) > vertex) {
					edges.add(edge);
				}
				if (edge.other(vertex) == vertex) {
					if (!added) {
						edges.add(edge);
						added = Boolean.TRUE;
					}
				}
			}
		}
		return edges;
	}

}