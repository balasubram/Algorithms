package com.bala.algs.week8;

import com.bala.algs.week2.Bag;
import com.bala.algs.week2.LinkedListBag;

@SuppressWarnings("unchecked")
public class EdgeWeightedDirectedGraph implements EdgeGraph {

	private int noOfVertex;

	private Bag<Edge>[] bags;

	private int noOfEdges;

	private int[] inDegree;

	public EdgeWeightedDirectedGraph(int vertex) {
		bags = (Bag<Edge>[]) new Bag[vertex];
		inDegree = new int[vertex];
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
		throw new UnsupportedOperationException("Operation not supported");
	}

	@Override
	public int inDegree(int v) {
		validateVertex(v);
		return inDegree(v);

	}

	@Override
	public int outDegree(int v) {
		validateVertex(v);
		return bags[v].size();
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
		inDegree[index]++;
		bags[index].add(edge);
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
				edges.add(edge);
			}
		}
		return edges;
	}

}