package com.bala.algs.week8;

interface EdgeGraph {

	int noOfVertices();

	int noOfEdges();

	int degree(int v);

	int inDegree(int v);

	int outDegree(int v);

	EdgeGraph reverse();

	void addEdge(Edge edge);

	Iterable<Edge> adj(int v);

	Iterable<Edge> edges();

}