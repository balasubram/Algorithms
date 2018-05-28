package com.bala.algs.week7;

public interface Graph {

	int noOfVertices();

	int noOfEdges();

	void addEdge(int v, int w);

	int degree(int v);

	Iterable<Integer> adj(int v);

	int inDegree(int v);

	int outDegree(int v);

	Graph reverse();

}