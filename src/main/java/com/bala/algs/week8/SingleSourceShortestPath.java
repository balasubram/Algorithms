package com.bala.algs.week8;

public interface SingleSourceShortestPath {

	double distTo(int v);

	boolean hasPathTo(int v);

	Iterable<Edge> pathTo(int v);
	
}