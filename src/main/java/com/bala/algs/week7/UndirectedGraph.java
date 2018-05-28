package com.bala.algs.week7;

import com.bala.algs.week2.Bag;
import com.bala.algs.week2.LinkedListBag;
import com.bala.algs.week2.LinkedListStack;
import com.bala.algs.week2.Stack;

@SuppressWarnings("unchecked")
public class UndirectedGraph implements Graph {

	private int noOfVertex;

	private Bag<Integer>[] bags;

	private int noOfEdges;

	public UndirectedGraph(int noOfVertex) {
		this.noOfVertex = noOfVertex;
		this.bags = (Bag<Integer>[]) new Bag[noOfVertex];
		for (int i = 0; i < noOfVertex; i++) {
			bags[i] = new LinkedListBag<Integer>();
		}
		this.noOfEdges = 0;
	}

	public UndirectedGraph(UndirectedGraph graph) {
		this(graph.noOfVertex);
		for (int vertex = 0; vertex < noOfVertex; vertex++) {
			for (Integer adj : graph.adj(vertex)) {
				this.addEdge(vertex, adj);
			}
		}
	}

	private void validateVertex(int vertex) {
		if (vertex < 0 || vertex >= noOfVertex) {
			throw new IllegalArgumentException("Edge is not in range");
		}
	}

	public int noOfVertices() {
		return noOfVertex;
	}

	public int noOfEdges() {
		return noOfEdges;
	}

	public void addEdge(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		bags[v].add(w);
		bags[w].add(v);
		noOfEdges++;
	}

	@Override
	public int inDegree(int v) {
		throw new UnsupportedOperationException("Operation Not supported for Directed Graphs. Use degree instead");
	}

	@Override
	public int outDegree(int v) {
		throw new UnsupportedOperationException("Operation Not supported for Directed Graphs. Use degree instead");
	}

	public int degree(int v) {
		validateVertex(v);
		return bags[v].size();
	}

	public Iterable<Integer> adj(int v) {
		validateVertex(v);
		Stack<Integer> adjacentVertex = new LinkedListStack<Integer>();
		for (Integer adj : bags[v]) {
			adjacentVertex.push(adj);
		}
		return adjacentVertex;
	}

	public Graph reverse() {
		throw new UnsupportedOperationException("Operation not supported");
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("No Of Vertex :").append(noOfVertices()).append("; Edges : ").append(noOfEdges());
		sb.append(System.lineSeparator());
		for (int i = 0; i < noOfVertex; i++) {
			for (int adj : bags[i]) {
				sb.append(i).append("->").append(adj);
			}
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

}