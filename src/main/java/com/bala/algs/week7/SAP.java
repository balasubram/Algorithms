package com.bala.algs.week7;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;;

public class SAP {

	private final Digraph graph;

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		if (G == null) {
			throw new IllegalArgumentException("Argument is null");
		}
		this.graph = new Digraph(G);
	}

	private void validateVertex(int vertex) {
		if (vertex < 0 || vertex >= graph.V()) {
			throw new IllegalArgumentException("Vertex is out of range");
		}
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(graph, v);
		BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(graph, w);
		Queue<Integer> ancestors = new Queue<>();
		for (int vertex = 0; vertex < graph.V(); vertex++) {
			if (pathV.hasPathTo(vertex) && pathW.hasPathTo(vertex)) {
				ancestors.enqueue(vertex);
			}
		}
		int shortestLength = Integer.MAX_VALUE;
		for (int vertex : ancestors) {
			int length = pathV.distTo(vertex) + pathW.distTo(vertex);
			if (length < shortestLength) {
				shortestLength = length;
			}
		}
		return ancestors.isEmpty() ? -1 : shortestLength;
	}

	// a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	public int ancestor(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(graph, v);
		BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(graph, w);
		Queue<Integer> ancestors = new Queue<>();
		for (int vertex = 0; vertex < graph.V(); vertex++) {
			if (pathV.hasPathTo(vertex) && pathW.hasPathTo(vertex)) {
				ancestors.enqueue(vertex);
			}
		}
		int ancestor = -1;
		int shortestLength = Integer.MAX_VALUE;
		for (int vertex : ancestors) {
			int length = pathV.distTo(vertex) + pathW.distTo(vertex);
			if (length < shortestLength) {
				shortestLength = length;
				ancestor = vertex;
			}
		}
		return ancestor;
	}

	// length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null) {
			throw new IllegalArgumentException("Arguments is null");
		}
		for (Integer vertex : v) {
			validateVertex(vertex);
		}
		for (Integer vertex : w) {
			validateVertex(vertex);
		}

		BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(graph, v);
		BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(graph, w);
		int shortestLength = Integer.MAX_VALUE;
		Queue<Integer> ancestors = new Queue<>();
		for (int vertex = 0; vertex < graph.V(); vertex++) {
			if (pathV.hasPathTo(vertex) && pathW.hasPathTo(vertex)) {
				ancestors.enqueue(vertex);
			}
		}
		for (int vertex : ancestors) {
			int length = pathV.distTo(vertex) + pathW.distTo(vertex);
			if (length < shortestLength) {
				shortestLength = length;
			}
		}
		return ancestors.isEmpty() ? -1 : shortestLength;
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null) {
			throw new IllegalArgumentException("Arguments is null");
		}
		for (Integer vertex : v) {
			validateVertex(vertex);
		}
		for (Integer vertex : w) {
			validateVertex(vertex);
		}
		BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(graph, v);
		BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(graph, w);
		int shortestLength = Integer.MAX_VALUE;
		Queue<Integer> ancestors = new Queue<>();
		for (int vertex = 0; vertex < graph.V(); vertex++) {
			if (pathV.hasPathTo(vertex) && pathW.hasPathTo(vertex)) {
				ancestors.enqueue(vertex);
			}
		}
		int ancestor = -1;
		for (int vertex : ancestors) {
			int length = pathV.distTo(vertex) + pathW.distTo(vertex);
			if (length < shortestLength) {
				shortestLength = length;
				ancestor = vertex;
			}
		}

		return ancestor;
	}

	// do unit testing of this class
	public static void main(String[] args) {

		In in = new In("/Users/bj186016/EclipseWorkspace/Algorithms/bin/wordnet/digraph1.txt");
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			int length = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}

	}

}