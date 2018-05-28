package com.bala.algs.week7;

public interface Paths {

	boolean hasPathTo(int v);

	int distTo(int v);

	Iterable<Integer> pathTo(int v);

}