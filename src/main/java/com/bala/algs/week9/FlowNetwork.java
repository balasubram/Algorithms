package com.bala.algs.week9;

import com.bala.algs.week2.Bag;
import com.bala.algs.week2.LinkedListBag;

public class FlowNetwork {

	private int noOfVertices;

	private int noOfEdges;

	private Bag<FlowEdge>[] edges;

	@SuppressWarnings("unchecked")
	public FlowNetwork(int noOfVertices) {
		this.noOfVertices = noOfVertices;
		this.edges = (Bag<FlowEdge>[]) new Bag[noOfVertices];
		for (int i = 0; i < noOfVertices; i++) {
			edges[i] = new LinkedListBag<>();
		}
	}

	private void validateVertex(int vertex) {
		if (vertex < 0 || vertex >= noOfVertices) {
			throw new IllegalArgumentException("Vertex is out of range");
		}
	}

	public int getNoOfVertices() {
		return this.noOfVertices;
	}

	public int getNoOfEdges() {
		return this.noOfEdges;
	}

	public void addEdge(FlowEdge edge) {
		int from = edge.from();
		int to = edge.to();
		validateVertex(from);
		validateVertex(to);
		edges[from].add(edge);
		noOfEdges++;
	}

	public Iterable<FlowEdge> adj(int vertex) {
		validateVertex(vertex);
		return edges[vertex];
	}

	public Iterable<FlowEdge> edges() {
		Bag<FlowEdge> edges = new LinkedListBag<>();
		for (int vertex = 0; vertex < noOfVertices; vertex++) {
			for (FlowEdge edge : this.edges[vertex]) {
				if (edge.to() != vertex) {
					edges.add(edge);
				}
			}
		}
		return edges;
	}

}